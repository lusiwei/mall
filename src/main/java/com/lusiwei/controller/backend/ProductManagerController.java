package com.lusiwei.controller.backend;

import com.lusiwei.common.Constant;
import com.lusiwei.common.ResponseResult;
import com.lusiwei.dto.UserDto;
import com.lusiwei.param.ProductManageParam;
import com.lusiwei.service.ProductManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * @Author: lusiwei
 * @Date: 2018/11/27 16:40
 * @description: 后台产品接口
 */
@RestController
@RequestMapping("manage/product")
public class ProductManagerController {
    private final ProductManagerService productManagerService;

    @Autowired
    public ProductManagerController(ProductManagerService productManagerService) {
        this.productManagerService = productManagerService;
    }

    /**
     * 产品列表
     * @param pageNum 多少页
     * @param pageSize 一页多少条
     * @param httpSession session
     * @return ResponseResult
     */
    @RequestMapping("list.do")
    public ResponseResult getProductList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, HttpSession httpSession) {
        //判断是否登录
        if (isLogin(httpSession)) {
            return ResponseResult.createFailResponse(10,"用户未登录");
        }
        //判断是否是管理员
        if (isAdmin(httpSession)) {
            return ResponseResult.createFailResponse("没有权限");
        }
        return productManagerService.queryProductList(pageNum,pageSize);
    }

    /**
     *  搜索
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("search.do")
    public ResponseResult search(@RequestParam("keyword") String productName,Integer productId,@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize){
       return productManagerService.searchProductList(productName,productId,pageNum,pageSize);
    }
    /**
     * 图片上传
     */
    @RequestMapping("upload.do")
    public ResponseResult upload( @RequestParam("upload_file") MultipartFile file,@RequestParam("type") String productType){
       return productManagerService.upload(file,productType);
    }

    /**
     * 获取产品详情
     */
    @RequestMapping("detail.do")
    public ResponseResult detail(int productId) {
        return productManagerService.queryProductDetail(productId);
    }
    /**
     * 新增or更新产品
     */
    @RequestMapping("save.do")
    public ResponseResult save(HttpSession httpSession, ProductManageParam productManageParam, BindingResult bindingResult) {
        //判断是否登录
        if (isLogin(httpSession)) {
            return ResponseResult.createFailResponse(10,"用户未登录");
        }
        //判断是否是管理员
        if (isAdmin(httpSession)) {
            return ResponseResult.createFailResponse("没有权限");
        }
        return productManagerService.save(productManageParam);
    }
    /**
     * 判断用户是否登录
     */
    private boolean isLogin(HttpSession session){
        UserDto userDto = (UserDto) session.getAttribute(Constant.User.CURRENT_USER);
        return userDto == null;
    }
    /**
     * 判断用户是否是管理员(登录状态下)
     */
    private boolean isAdmin(HttpSession session){
        UserDto userDto = (UserDto) session.getAttribute(Constant.User.CURRENT_USER);
        return userDto.getRole() == 0;
    }

}
