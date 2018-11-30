package com.lusiwei.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lusiwei.common.ResponseResult;
import com.lusiwei.dao.CategoryMapper;
import com.lusiwei.dao.ProductMapper;
import com.lusiwei.dto.ProductManageDetailDto;
import com.lusiwei.dto.ProductManageDto;
import com.lusiwei.exception.MyException;
import com.lusiwei.param.ProductManageParam;
import com.lusiwei.pojo.Product;
import com.lusiwei.pojo.ProductWithBLOBs;
import com.lusiwei.service.ProductManagerService;
import com.lusiwei.util.FtpUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Author: lusiwei
 * @Date: 2018/11/27 16:43
 * @Description:
 */
@Service
public class ProductManagerServiceImpl implements ProductManagerService {
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    @Autowired
    public ProductManagerServiceImpl(ProductMapper productMapper, CategoryMapper categoryMapper) {
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public ResponseResult save(ProductManageParam productManageParam) {
        //通过Builder模式
        //这里只构建了updateTime,增加和删除都有updateTime,增加时createTime
        Product product = Product.builder().categoryId(productManageParam.getCategoryId()).name(productManageParam.getName()
        ).subtitle(productManageParam.getSubtitle()).mainImage(productManageParam.getMainImage())
                .price(productManageParam.getPrice()).stock(productManageParam.getStock())
                .status(productManageParam.getStatus()).updateTime(new Date()).id(productManageParam.getId())
                .build();
        ProductWithBLOBs productWithBLOBs = new ProductWithBLOBs();
        BeanUtils.copyProperties(product, productWithBLOBs);
        productWithBLOBs.setDetail(productManageParam.getDetail());
        productWithBLOBs.setSubImages(productManageParam.getSubImages());
        if (productManageParam.getId() == null) {
            //id没有时表明是增加
            productWithBLOBs.setCreateTime(new Date());
            int insertReturnCode = productMapper.insertSelective(productWithBLOBs);
            if (insertReturnCode > 0) {
                return ResponseResult.createSuccessResponse("新增产品成功");
            } else {
                return ResponseResult.createFailResponse("新增产品失败");
            }
        } else {
            //修改
            int updateReturnCode = productMapper.updateByPrimaryKeyWithBLOBs(productWithBLOBs);
            if (updateReturnCode > 0) {
                return ResponseResult.createSuccessResponse("更新产品成功");
            } else {
                return ResponseResult.createFailResponse("更新产品失败");
            }
        }
    }

    @Override
    public ResponseResult queryProductList(int pageNum, int pageSize) {
        pageNum = pageNum <= 0 ? 1 : pageNum;
        pageSize = pageSize <= 0 ? 10 : pageSize;
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.queryProductList();
        List<ProductManageDto> productManageDtoList = new ArrayList<>();
        for (Product product : productList) {
            ProductManageDto productManageDto = ProductManageDto.getProductManageDto(product);
            productManageDto.setImageHost("http://www.image.com/upload/product/");
            productManageDtoList.add(productManageDto);
        }
        PageInfo<ProductManageDto> productPageInfo = new PageInfo<>(productManageDtoList);
        return ResponseResult.createSuccessResponse(productPageInfo);
    }

    @Override
    public ResponseResult searchProductList(@RequestParam("keyword") String productName, Integer productId, int pageNum, int pageSize) {
        pageNum = pageNum <= 0 ? 1 : pageNum;
        pageSize = pageSize <= 0 ? 10 : pageSize;
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.searchProductList(productName, productId);
        PageInfo<Product> productPageInfo = new PageInfo<>(productList);
        return ResponseResult.createSuccessResponse(productPageInfo);
    }

    @Override
    public ResponseResult upload(MultipartFile multipartFile, String productType) {
        try {
            return ResponseResult.createSuccessResponse(FtpUtil.uploadFile(multipartFile, productType));
        } catch (IOException e) {
            throw new MyException("上传失败");
        }
    }

    @Override
    public ResponseResult queryProductDetail(int productId) {
        Product product=productMapper.selectByPrimaryKey(productId);
        ProductManageDetailDto productManageDetailDto = ProductManageDetailDto.getProductDetailDto(product);
        productManageDetailDto.setImageHost("http://www.image.com/upload/product/");
        //查询parentCategoryId
        int parentId=categoryMapper.queryParentIdByCategoryId(productManageDetailDto.getCategoryId());
        productManageDetailDto.setParentCategoryId(parentId);
        return ResponseResult.createSuccessResponse(productManageDetailDto);
    }
}
