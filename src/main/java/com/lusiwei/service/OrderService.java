package com.lusiwei.service;

import com.lusiwei.common.ResponseResult;

/**
 * @Author: lusiwei
 * @Date: 2018/11/30 15:48
 * @Description:
 */
public interface OrderService {
    /**
     * 创建订单
     *
     * @param shippingId
     * @param id
     * @return
     */
    ResponseResult create(Integer shippingId, int id);

    /**
     * 取消订单
     *
     * @param userId
     * @param orderNo
     * @return
     */

    ResponseResult cancel(int userId, Long orderNo);

    ResponseResult getOrderCartProduct(int id);


    ResponseResult detail(int id, Long orderNo);

    ResponseResult list(int id, Integer pageSize, Integer pageNum);

    ResponseResult pay(int id, Long orderNo, String qrcode);
}
