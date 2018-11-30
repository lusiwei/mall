package com.lusiwei.service;

import com.lusiwei.common.ResponseResult;
import com.lusiwei.param.ShippingParam;

/**
 * @Author: lusiwei
 * @Date: 2018/11/29 21:25
 * @Description:
 */
public interface ShippingService {
    ResponseResult addOrUpdate(ShippingParam shippingParam,Integer userId);

    ResponseResult delete(Integer shippingId, Integer userId);

    ResponseResult select(Integer shippingId, int userId);

    ResponseResult list(int pageNum, int pageNum1, int userId);
}
