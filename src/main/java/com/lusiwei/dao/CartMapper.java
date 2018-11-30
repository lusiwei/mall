package com.lusiwei.dao;

import com.lusiwei.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    List<Cart> selectByUserId(@Param("userId") Integer userId);

    int selectByProductId(@Param("productId") Integer productId);

    void updateByProductId(@Param("productId") Integer productId, @Param("count") Integer count, @Param("updateType") String updateType, @Param("updateTime") Date updateTime);

    int deleteByProductIds(@Param("productIds") String[] productIds);

    Cart selectCartByProductId(@Param("productId") Integer productId);

    int updateCheckByProductId(@Param("productId") Integer productId, @Param("checked") Integer checked, @Param("updateTime") Date updateTime);

    int updateByUserId(@Param("userId") Integer userId, @Param("checked") Integer checked, @Param("updateTime") Date updaeTime);

    int queryCountByUserId(Integer userId);
}