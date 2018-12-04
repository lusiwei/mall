package com.lusiwei.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lusiwei.common.Constant;
import com.lusiwei.common.ResponseCode;
import com.lusiwei.common.ResponseResult;
import com.lusiwei.dao.*;
import com.lusiwei.exception.MyException;
import com.lusiwei.pojo.*;
import com.lusiwei.service.OrderService;
import com.lusiwei.util.BigDecimalUtil;
import com.lusiwei.util.Date2String;
import com.lusiwei.util.PropertiesUtil;
import com.lusiwei.vo.OrderItemVo;
import com.lusiwei.vo.OrderProductVo;
import com.lusiwei.vo.OrderVo;
import com.lusiwei.vo.ShippingVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author: lusiwei
 * @Date: 2018/11/30 15:48
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ShippingMapper shippingMapper;


    @Autowired
    public OrderServiceImpl(CartMapper cartMapper, ProductMapper productMapper, OrderMapper orderMapper, OrderItemMapper orderItemMapper, ShippingMapper shippingMapper) {
        this.cartMapper = cartMapper;
        this.productMapper = productMapper;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.shippingMapper = shippingMapper;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = MyException.class)
    @Override
    public ResponseResult create(Integer shippingId, int userId) {
        //获取购物车选中的商品
        List<Cart> cartList = cartMapper.selectCheckedCartByUserId(userId);
        //获取所有选中商品的订单详情
        ResponseResult cartOrderItem = getCartOrderItem(userId, cartList);
        if (!cartOrderItem.isSuccess()) {
            return cartOrderItem;
        }
        //计算总价
        List<OrderItem> orderItemList = (List<OrderItem>) cartOrderItem.getData();
        if (CollectionUtils.isEmpty(orderItemList)) {
            return ResponseResult.createFailResponse("购物车未空");
        }
        BigDecimal totalPrice = getTotalPrice(orderItemList);
        //生成订单
        //1, 生成订单号
        long orderNo = generateOrderNo();
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setPayment(totalPrice);
        order.setPaymentType(Constant.PaymentType.ONLINE_PAY.getCode());
        order.setShippingId(shippingId);
        order.setPostage(0);

        int rowCount = orderMapper.insert(order);
        if (rowCount <= 0) {
            return ResponseResult.createFailResponse("订单生成失败");
        }
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
        }
        //批量插入
        int row = orderItemMapper.batchInsert(orderItemList);
        if (row <= 0) {
            throw new MyException("插入订单项失败");
        }
        //生成成功后减少库存
        reduceProductStock(orderItemList);
        //清空购物车
        cleanCart(cartList);
        OrderVo orderVo = getOrderVo(order, orderItemList);
        return ResponseResult.createSuccessResponse(orderVo);
    }

    @Override
    public ResponseResult cancel(int userId, Long orderNo) {
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
        if (order == null) {
            return ResponseResult.createFailResponse("此订单不存在");
        }
        if (order.getStatus().equals(Constant.OrderStatusEnum.NO_PAY.getCode())) {
            return ResponseResult.createSuccessResponse("已付款，无法取消订单");
        }
        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setStatus(Constant.OrderStatusEnum.CANCELED.getCode());
        int rowCount = orderMapper.updateByPrimaryKeySelective(updateOrder);
        if (rowCount <= 0) {
            return ResponseResult.createFailResponse("取消订单失败");
        }
        return ResponseResult.createSuccessResponse("取消订单成功");
    }

    @Override
    public ResponseResult getOrderCartProduct(int userId) {
        //定义要返回到前台的对象
        OrderProductVo orderProductVo = new OrderProductVo();
        //根据userId 获取用户选中在购物车中选中的商品
        List<Cart> carts = cartMapper.selectCheckedCartByUserId(userId);
        // 根据用户选中的商品获取所有订单项
        ResponseResult cartOrderItem = getCartOrderItem(userId, carts);
        if (!cartOrderItem.isSuccess()) {
            return cartOrderItem;
        }
        List<OrderItemVo> orderItemVoList = new ArrayList<>();
        List<OrderItem> orderItem = (List<OrderItem>) cartOrderItem.getData();
        //定义总价
        BigDecimal totalPrice = new BigDecimal("0");
        for (OrderItem item : orderItem) {
            //计算总价
            totalPrice = BigDecimalUtil.add(item.getTotalPrice(), totalPrice);
            orderItemVoList.add(getOrderItemVo(item));
        }
        orderProductVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix") + "product/");
        orderProductVo.setOrderItemVoList(orderItemVoList);
        orderProductVo.setProductTotalPrice(totalPrice);
        return ResponseResult.createSuccessResponse(orderProductVo);
    }

    @Override
    public ResponseResult detail(int userId, Long orderNo) {
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
        if (order == null) {
            return ResponseResult.createFailResponse("没有找到该订单");
        }
        List<OrderItem> orderItemList = orderItemMapper.getByUserIdAndOrderNo(userId, orderNo);
        OrderVo orderVo = getOrderVo(order, orderItemList);
        return ResponseResult.createSuccessResponse(orderVo);
    }

    @Override
    public ResponseResult list(int userId, Integer pageSize, Integer pageNum) {
        List<OrderVo> orderVoList = new ArrayList<>();
        //根据userId获取所有订单
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUserId(userId);
        for (Order order : orderList) {
            List<OrderItem> orderItemList = orderItemMapper.getByUserIdAndOrderNo(userId, order.getOrderNo());
            OrderVo orderVo = getOrderVo(order, orderItemList);
            //根据code获取描述
            orderVo.setPaymentTypeDesc(Constant.PaymentType.getDescByCode(order.getPaymentType()));
            orderVo.setStatusDesc(Constant.OrderStatusEnum.getDescByCode(order.getStatus()));
            orderVoList.add(orderVo);
        }
        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVoList);
        return ResponseResult.createSuccessResponse(pageInfo);
    }

    @Override
    public ResponseResult pay(int id, Long orderNo, String qrcode) {

        return null;
    }

    private OrderVo getOrderVo(Order order, List<OrderItem> orderItemList) {
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPostage(order.getPostage());
        orderVo.setStatus(order.getStatus());
        orderVo.setShippingId(order.getShippingId());
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        if (shipping == null) {
            throw new MyException("收货地址不存在");
        }
        //获取shippingVo
        ShippingVo shippingVo = getShippingVo(shipping);
        orderVo.setShippingVo(shippingVo);
        orderVo.setPaymentTime(Date2String.date2String(order.getPaymentTime()));
        orderVo.setSendTime(Date2String.date2String(order.getSendTime()));
        orderVo.setCloseTime(Date2String.date2String(order.getCloseTime()));
        orderVo.setEndTime(Date2String.date2String(order.getEndTime()));
        orderVo.setCreateTime(Date2String.date2String(order.getCreateTime()));

        //组装OrderItemVo
        List<OrderItemVo> orderItemVoList = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            orderItemVoList.add(getOrderItemVo(orderItem));
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;
    }

    private OrderItemVo getOrderItemVo(OrderItem orderItem) {
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());
        orderItemVo.setCreateTime(Date2String.date2String(orderItem.getCreateTime()));
        return orderItemVo;
    }

    private ShippingVo getShippingVo(Shipping shipping) {
        ShippingVo shippingVo = new ShippingVo();
        shippingVo.setReceiverAddress(shipping.getReceiverAddress());
        shippingVo.setReceiverCity(shipping.getReceiverCity());
        shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVo.setReceiverMobile(shipping.getReceiverMobile());
        shippingVo.setReceiverName(shipping.getReceiverName());
        shippingVo.setReceiverPhone(shipping.getReceiverPhone());
        shippingVo.setReceiverProvince(shipping.getReceiverProvince());
        shippingVo.setReceiverZip(shipping.getReceiverZip());
        return shippingVo;
    }

    /**
     * 清空购物车
     *
     * @param cartList
     */
    private void cleanCart(List<Cart> cartList) {
        for (Cart cart : cartList) {
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
    }

    /**
     * 减少库存
     *
     * @param orderItemList
     */
    private void reduceProductStock(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock() - orderItem.getQuantity());
            productMapper.updateByPrimaryKey(product);
        }
    }

    /**
     * 生成订单号
     *
     * @return
     */
    private long generateOrderNo() {
        long l = System.currentTimeMillis();
        return l + new Random().nextInt(100);
    }

    private BigDecimal getTotalPrice(List<OrderItem> orderItemList) {
        BigDecimal totalPrice = new BigDecimal("0");
        for (OrderItem orderItem : orderItemList) {
            totalPrice = BigDecimalUtil.add(totalPrice, orderItem.getTotalPrice());
        }
        return totalPrice;
    }

    /**
     * 获取所有订单详情
     */
    private ResponseResult getCartOrderItem(int userId, List<Cart> cartList) {
        List<OrderItem> orderItemList = new ArrayList<>();
        if (CollectionUtils.isEmpty(cartList)) {
            return ResponseResult.createFailResponse("购物车为空");
        }
        //校验商品的状态和库存
        for (Cart cart : cartList) {
            Product product = productMapper.selectByPrimaryKey(cart.getProductId());
            //校验商品状态
            if (!product.getStatus().equals(ResponseCode.ON_SALE.getCode())) {
                return ResponseResult.createFailResponse("商品" + product.getName() + "不是在线售卖状态");
            }
            //校验商品库存
            if (cart.getQuantity() > product.getStock()) {
                return ResponseResult.createFailResponse("商品" + product.getName() + "库存不足");
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setUserId(userId);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setTotalPrice(BigDecimalUtil.multiple(orderItem.getCurrentUnitPrice(), orderItem.getQuantity()));
            orderItemList.add(orderItem);
        }
        return ResponseResult.createSuccessResponse(orderItemList);
    }
}
