package com.lusiwei.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lusiwei.common.ResponseResult;
import com.lusiwei.dao.ShippingMapper;
import com.lusiwei.exception.MyException;
import com.lusiwei.param.ShippingParam;
import com.lusiwei.pojo.Shipping;
import com.lusiwei.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lusiwei
 * @Date: 2018/11/29 21:25
 * @Description:
 */
@Service
public class ShippingServiceImpl implements ShippingService {
    private final ShippingMapper shippingMapper;

    @Autowired
    public ShippingServiceImpl(ShippingMapper shippingMapper) {
        this.shippingMapper = shippingMapper;
    }

    /**
     * 添加或更新，根据id是否为空判断是添加还是删除
     *
     * @param shippingParam
     * @param userId
     * @return
     */
    @Override
    public ResponseResult addOrUpdate(ShippingParam shippingParam, Integer userId) {
        Shipping shipping = Shipping.builder().id(shippingParam.getId()).userId(userId).receiverName(shippingParam.getReceiverName())
                .receiverPhone(shippingParam.getReceiverPhone()).receiverMobile(shippingParam.getReceiverMobile())
                .receiverProvince(shippingParam.getReceiverProvince()).receiverCity(shippingParam.getReceiverCity())
                .receiverDistrict(shippingParam.getReceiverDistrict()).receiverAddress(shippingParam.getReceiverAddress())
                .receiverZip(shippingParam.getReceiverZip()).build();
        int result = 0;
        ///id==null则为增加，有id则为修改
        if (shippingParam.getId() == null) {
            shipping.setCreateTime(new Date());
            shipping.setUpdateTime(new Date());
            result = shippingMapper.insert(shipping);
            if (result <= 0) {
                throw new MyException("添加地址失败");
            }
            Map<String, Integer> shipIdMap = new HashMap<>(1);
            shipIdMap.put("shippingId", shipping.getId());
            return ResponseResult.createSuccessResponse("新建地址成功", shipIdMap);
        }
        shipping.setUpdateTime(new Date());
        result = shippingMapper.updateByPrimaryKey(shipping);
        if (result <= 0) {
            throw new MyException("更新地址失败");
        }
        return ResponseResult.createSuccessResponse("更新地址成功");
    }

    @Override
    public ResponseResult delete(Integer shippingId, Integer userId) {
        //根据shippingId 查找userId 判断是否有权限操作
        Shipping shipping = shippingMapper.selectByPrimaryKey(shippingId);
        if (!shipping.getUserId().equals(userId)) {
            throw new MyException("权限错误，非法操作");
        }
        int result = shippingMapper.deleteByPrimaryKey(shippingId);
        if (result <= 0) {
            throw new MyException("删除失败");
        }
        return ResponseResult.createSuccessResponse("删除地址成功");
    }

    @Override
    public ResponseResult select(Integer shippingId, int userId) {
        //根据shippingId 查找userId 判断是否有权限操作
        Shipping shipping = shippingMapper.selectByPrimaryKey(shippingId);
        if (shipping == null) {
            return ResponseResult.createFailResponse("地址不存在");
        }
        if (!shipping.getUserId().equals(userId)) {
            throw new MyException("权限错误，非法操作");
        }
        return ResponseResult.createSuccessResponse(shipping);
    }

    @Override
    public ResponseResult list(int pageNum, int pageSize, int userId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.queryListByUserId(userId);
        PageInfo<Shipping> shippingPageInfo = new PageInfo<>(shippingList);
        return ResponseResult.createSuccessResponse(shippingPageInfo);
    }
}
