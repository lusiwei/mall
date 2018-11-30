package com.lusiwei.service.impl;

import com.lusiwei.common.ResponseResult;
import com.lusiwei.dao.CategoryMapper;
import com.lusiwei.exception.MyException;
import com.lusiwei.pojo.Category;
import com.lusiwei.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: lusiwei
 * @Date: 2018/11/26 11:14
 * @Description:
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public ResponseResult<List<Category>> getCategory(int categoryId) {
        List<Category> category = categoryMapper.selectByParentId(categoryId);
        return ResponseResult.createSuccessResponse(category);
    }

    @Override
    public ResponseResult addCategory(int parentId, String categoryName) {
        Category category = Category.builder().parentId(parentId).name(categoryName).build();
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        category.setStatus(1);
        //TODO: 插入失败
        categoryMapper.insertSelective(category);
        return ResponseResult.createSuccessResponse();
    }

    @Override
    public ResponseResult updateCategoryName(int categoryId, String categoryName) {
        int i = categoryMapper.updateByCategoryId(categoryId, categoryName);
        if (i <= 0) {
            throw new MyException("更新品类名字失败");
        }
        return ResponseResult.createSuccessResponse("更新品类名字成功");

    }

    @Override
    public List<Integer> getDeepCategory(int parentId) {
        //定义一个list装所有id
        List<Integer> allCategoryId=new ArrayList<>();
        allCategoryId.add(parentId);
        //根据parentId 查所有子类id
        recursiveQueryByParentId(parentId,allCategoryId);
        return allCategoryId;
    }

    /**
     * 递归查询所有子类id
     * @param parentId
     * @param allCategoryId
     */
    private void recursiveQueryByParentId(int parentId,List<Integer> allCategoryId) {
        List<Category> categoryList = categoryMapper.selectByParentId(parentId);
        List<Integer> collect = categoryList.stream().map(Category::getId).collect(Collectors.toList());
        for (Integer p : collect) {
            allCategoryId.add(p);
            recursiveQueryByParentId(p, allCategoryId);
        }
    }


}
