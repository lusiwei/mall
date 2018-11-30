package com.lusiwei.controller.portal;

import com.lusiwei.common.ResponseResult;
import com.lusiwei.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lusiwei
 * @Date: 2018/11/29 09:35
 * @Description: 前台产品接口
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("list.do")
    public ResponseResult list(String keyword, Integer categoryId, String orderBy, @RequestParam(defaultValue  = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize){
        return productService.queryList(keyword,categoryId,orderBy,pageNum,pageSize);
    }

    @RequestMapping("detail.do")
    public ResponseResult detail(Integer productId){
        return productService.queryDetail(productId);

    }
}
