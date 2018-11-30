package com.lusiwei.service;

import com.lusiwei.common.ResponseResult;
import com.lusiwei.param.ProductManageParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: lusiwei
 * @Date: 2018/11/27 16:42
 * @Description:
 */
public interface ProductManagerService {
    ResponseResult save(ProductManageParam productManageParam);

    ResponseResult queryProductList(int pageNum, int pageSize);

    ResponseResult searchProductList(String productName, Integer productId, int pageNum, int pageSize);

    ResponseResult upload(MultipartFile multipartFile, String productType);

    ResponseResult queryProductDetail(int productId);
}
