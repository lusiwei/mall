package com.lusiwei.service;

import com.lusiwei.common.ResponseResult;
import com.lusiwei.pojo.Category;

import java.util.List;

/**
 * @Author: lusiwei
 * @Date: 2018/11/26 11:13
 * @Description:
 */
public interface CategoryService {
    ResponseResult<List<Category>> getCategory(int categoryId);

    ResponseResult addCategory(int parentId, String categoryName);

    ResponseResult updateCategoryName(int categoryId, String categoryName);

    List<Integer> getDeepCategory(int categoryId);
}
