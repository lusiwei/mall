package com.lusiwei.service;

import com.lusiwei.common.ResponseResult;

/**
 * @Author: lusiwei
 * @Date: 2018/11/29 09:41
 * @Description:
 */
public interface ProductService {
    ResponseResult queryList(String keyword, Integer categoryId, String orderBy, int pageNum, int pageSize);

    ResponseResult queryDetail(Integer productId);
}
