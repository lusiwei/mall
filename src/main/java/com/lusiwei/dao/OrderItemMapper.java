package com.lusiwei.dao;

import com.lusiwei.pojo.Order;
import com.lusiwei.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    int batchInsert(@Param("orderItemList") List<OrderItem> orderItemList);

    List<OrderItem> getByUserIdAndOrderNo(@Param("userId") Integer userId, @Param("orderNo") Long orderNo);
}