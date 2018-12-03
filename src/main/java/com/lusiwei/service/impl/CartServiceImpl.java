package com.lusiwei.service.impl;

import com.lusiwei.common.Constant;
import com.lusiwei.common.ResponseResult;
import com.lusiwei.dao.CartMapper;
import com.lusiwei.dao.ProductMapper;
import com.lusiwei.exception.MyException;
import com.lusiwei.pojo.Cart;
import com.lusiwei.pojo.Product;
import com.lusiwei.service.CartService;
import com.lusiwei.util.BigDecimalUtil;
import com.lusiwei.vo.CartProductVo;
import com.lusiwei.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: lusiwei
 * @Date: 2018/11/29 13:26
 * @Description: cart 业务层
 */
@Service
public class CartServiceImpl implements CartService {
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;

    @Autowired
    public CartServiceImpl(CartMapper cartMapper, ProductMapper productMapper) {
        this.cartMapper = cartMapper;
        this.productMapper = productMapper;
    }

    @Override
    public ResponseResult<CartVo> getCartList(Integer userId) {
        //根据userId在cart表中查出 product_id,再根据 productId 查product
        List<Cart> cartList = cartMapper.selectByUserId(userId);
        List<CartProductVo> cartProductVoList = new ArrayList<>();
        boolean allChecked = true;
        BigDecimal cartTotalPrice = new BigDecimal("0");
        for (Cart cart : cartList) {
            Product product = productMapper.selectByProductId(cart.getProductId());
            CartProductVo cartProductVo = CartProductVo.getCartProductVo(product, cart);
            cartProductVoList.add(cartProductVo);
            //有一个未选中久将allcheck置为false
            if (cart.getChecked() == 0) {
                allChecked = false;
            }
            //统计价格
            cartTotalPrice = BigDecimalUtil.add(cartProductVo.getProductTotalPrice(), cartTotalPrice);
        }
        CartVo cartVo = new CartVo();
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(allChecked);
        cartVo.setCartTotalPrice(cartTotalPrice);
        return ResponseResult.createSuccessResponse(cartVo);
    }

    @Override
    public ResponseResult add(Integer productId, Integer count, Integer userId, String updateType) {
        //先判断购物车中是否已经有了相同的商品,如果有那么修改数量
        if (cartMapper.selectByProductId(productId) > 0) {
            //修改quantity,商品数量
            cartMapper.updateByProductId(productId, count, updateType,new Date());
        } else {
            Cart cart = Cart.builder().productId(productId).quantity(count).userId(userId).checked(1).createTime(new Date()).updateTime(new Date()).build();
            int i = cartMapper.insertSelective(cart);
            if (i <= 0) {
                return ResponseResult.createFailResponse("添加到购物车失败");
            }
        }
        return getCartList(userId);
    }

    @Override
    public ResponseResult delete(String productIds, Integer userId) {
        String[] split = productIds.split(",");
        int i = cartMapper.deleteByProductIds(split);
        if (i <= 0) {
            return ResponseResult.createSuccessResponse("删除失败");
        }
        return getCartList(userId);
    }

    @Override
    public ResponseResult select(Integer productId, String productSelectedType, Integer userId) {
        if (productId != null) {
            Cart cart=cartMapper.selectCartByProductId(productId);
            if (!cart.getUserId().equals(userId)) {
                throw new MyException("没有权限");
            }
        }
        int code=0;
        if (Constant.Cart.PRODUCT_SELECTED.equals(productSelectedType)) {
            //选中一个
            code=cartMapper.updateCheckByProductId(productId,Constant.Cart.CHECKED,new Date());
        } else if (Constant.Cart.PRODUCT_UNSELECTED.equals(productSelectedType)) {
            code= cartMapper.updateCheckByProductId(productId, Constant.Cart.UNCHECKED, new Date());
        } else if (Constant.Cart.PRODUCT_SELECTEED_ALL.equals(productSelectedType)) {
            code=cartMapper.updateByUserId(userId,Constant.Cart.CHECKED,new Date());
        } else if (Constant.Cart.PRODUCT_UNSELECTEED_ALL.equals(productSelectedType)) {
            code = cartMapper.updateByUserId(userId, Constant.Cart.UNCHECKED, new Date());
        }
        if (code <= 0) {
            throw new MyException("勾选失败");
        }
        return getCartList(userId);
    }

    @Override
    public ResponseResult queryCount(Integer userId) {
        Integer count=cartMapper.queryCountByUserId(userId);
        return ResponseResult.createSuccessResponse(count);
    }


}
