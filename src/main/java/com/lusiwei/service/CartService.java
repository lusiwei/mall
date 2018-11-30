package com.lusiwei.service;

import com.lusiwei.common.ResponseResult;
import com.lusiwei.vo.CartVo;

/**
 * @Author: lusiwei
 * @Date: 2018/11/29 13:25
 * @Description:
 */
public interface CartService {
    ResponseResult<CartVo> getCartList(Integer userId);

    ResponseResult add(Integer productId, Integer count, Integer userId, String updateType);

    ResponseResult delete(String productIds, Integer userId);

    ResponseResult select(Integer productId, String productSelected, Integer userId);

    ResponseResult queryCount(Integer userId);
}
