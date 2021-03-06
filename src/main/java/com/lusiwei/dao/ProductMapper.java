package com.lusiwei.dao;

import com.lusiwei.pojo.Product;
import com.lusiwei.pojo.ProductWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductWithBLOBs record);

    int insertSelective(ProductWithBLOBs record);

    ProductWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ProductWithBLOBs record);

    int updateByPrimaryKey(Product record);

    List<Product> queryProductList();

    List<Product> searchProductList(@Param("productName") String productName, @Param("productId") Integer productId);

    List<Product> queryList(@Param("keyword") String keyword, @Param("categoryId") Integer categoryId, @Param("orderBy") String orderBy);

    ProductWithBLOBs queryDetail(@Param("productId") Integer productId);

    Product selectByProductId(@Param("productId") Integer productId);
}