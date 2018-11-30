package com.lusiwei.controller.backend;

import com.lusiwei.common.Constant;
import com.lusiwei.common.ResponseCode;
import com.lusiwei.common.ResponseResult;
import com.lusiwei.pojo.Category;
import com.lusiwei.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author: lusiwei
 * @Date: 2018/11/26 11:11
 * @Description:
 */
@RestController
@RequestMapping("/manage/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping("get_category.do")
    public ResponseResult<List<Category>> getCategory(int categoryId, HttpSession httpSession) {
        Object user = httpSession.getAttribute(Constant.User.CURRENT_USER);
        if (user == null) {
            return ResponseResult.createFailResponse(ResponseCode.UNLOGIN.getCode(), "用户未登录，请登录");
        }
        ResponseResult<List<Category>> responseResult = categoryService.getCategory(categoryId);
        List<Category> data = responseResult.getData();
        if (data.size() == 0) {
            return ResponseResult.createFailResponse("未找到该品类");
        }
        return responseResult;
    }

    /**
     * 增加品类
     *
     * @return
     */
    @RequestMapping("add_category.do")
    public ResponseResult addCategory(int parentId, String categoryName) {
        categoryService.addCategory(parentId,categoryName);
        return ResponseResult.createSuccessResponse("添加品类成功");
    }

    @RequestMapping("set_category_name.do")
    public ResponseResult setCategoryName(int categoryId,String categoryName){
        categoryService.updateCategoryName(categoryId,categoryName);
        return ResponseResult.createSuccessResponse("更新品类名字成功");
    }

    @RequestMapping("get_deep_category.do")
    public ResponseResult getDeepCategory(int categoryId){
        List<Integer> list=categoryService.getDeepCategory(categoryId);
        return ResponseResult.createSuccessResponse(list);
    }

}
