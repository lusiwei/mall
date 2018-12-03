package com.lusiwei.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lusiwei.common.ResponseResult;
import com.lusiwei.dao.ProductMapper;
import com.lusiwei.dto.ProductDetailDto;
import com.lusiwei.dto.ProductDto;
import com.lusiwei.exception.MyException;
import com.lusiwei.pojo.Product;
import com.lusiwei.pojo.ProductWithBLOBs;
import com.lusiwei.service.ProductService;
import com.lusiwei.util.PropertiesUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.lusiwei.dto.ProductDto.getProductDto;

/**
 * @Author: lusiwei
 * @Date: 2018/11/29 09:41
 * @Description: 前台商品service层
 */
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public ResponseResult queryList(String keyword, Integer categoryId, String orderBy, int pageNum, int pageSize) {
        pageNum = pageNum <= 0 ? 1 : pageNum;
        pageSize = (pageSize <= 0 || pageSize>10) ? 5 : pageSize;
        PageHelper.startPage(pageNum, pageSize,true);
        List<Product> productList = productMapper.queryList(keyword, categoryId, orderBy);
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : productList) {
            ProductDto productDto = getProductDto(product);
            productDto.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix")+"product/");
            productDtoList.add(productDto);
        }
        PageInfo pageInfo=new PageInfo(productList);
        pageInfo.setList(productDtoList);
        return ResponseResult.createSuccessResponse(pageInfo);
    }

    @Override
    public ResponseResult queryDetail(Integer productId) {
        if (productId == null) {
            throw new MyException("商品id不能空");
        }
        ProductWithBLOBs product = productMapper.queryDetail(productId);
        if (product == null) {
            return ResponseResult.createFailResponse("该商品以下架或删除");
        }
        ProductDetailDto productDetail = new ProductDetailDto();
        BeanUtils.copyProperties(product, productDetail);
        productDetail.setImageHost("http://www.image.com/upload/product/");
        return ResponseResult.createSuccessResponse(productDetail);
    }
}
