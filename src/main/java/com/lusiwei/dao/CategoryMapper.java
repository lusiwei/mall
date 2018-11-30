package com.lusiwei.dao;

import com.lusiwei.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    /**
     * @param categoryId id
     * @return Category
     */
    List<Category> selectByParentId(@Param("categoryId") int categoryId);

    int updateByCategoryId(@Param("categoryId") int categoryId, @Param("categoryName") String categoryName);

    int queryParentIdByCategoryId(Integer categoryId);
}