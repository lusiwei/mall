package com.lusiwei.controller.portal;

import com.lusiwei.common.Constant;
import com.lusiwei.common.ResponseResult;
import com.lusiwei.dto.UserDto;
import com.lusiwei.service.CartService;
import com.lusiwei.util.JsonUtils;
import com.lusiwei.util.LoginCookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: lusiwei
 * @Date: 2018/11/29 13:18
 * @Description: cart
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * 购物车商品列表
     *
     * @param session
     * @return
     */
    @RequestMapping("list.do")
    public ResponseResult cartList(HttpServletRequest httpServletRequest) {
        //判断是否登录
        String loginInfo = LoginCookieUtil.getLoginInfo(httpServletRequest);
        UserDto userDto = JsonUtils.string2Object(loginInfo, UserDto.class);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户未登录");
        }
        Integer userId = userDto.getId();
        return cartService.getCartList(userId);
    }

    /**
     * 添加商品
     */
    @RequestMapping("add.do")
    public ResponseResult add(Integer productId, Integer count, HttpServletRequest httpServletRequest) {
        String loginInfo = LoginCookieUtil.getLoginInfo(httpServletRequest);
        UserDto userDto = JsonUtils.string2Object(loginInfo, UserDto.class);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户未登录");
        }
        Integer userId = userDto.getId();
        return cartService.add(productId, count, userId, Constant.Cart.UPDATE_TYPE_ADD);
    }

    /**
     * 更新购物车某个产品数量
     */
    @RequestMapping("update.do")
    public ResponseResult update(Integer productId, Integer count, HttpServletRequest httpServletRequest) {
        String loginInfo = LoginCookieUtil.getLoginInfo(httpServletRequest);
        UserDto userDto = JsonUtils.string2Object(loginInfo, UserDto.class);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户未登录");
        }
        Integer userId = userDto.getId();
        return cartService.add(productId, count, userId, Constant.Cart.UPDATE_TYPE_UPDATE);
    }

    /**
     * 移除购物车某个产品
     */
    @RequestMapping("delete_product.do")
    public ResponseResult delete(String productIds,HttpServletRequest httpServletRequest) {
        String loginInfo = LoginCookieUtil.getLoginInfo(httpServletRequest);
        UserDto userDto = JsonUtils.string2Object(loginInfo, UserDto.class);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户未登录");
        }
        Integer userId = userDto.getId();
        return cartService.delete(productIds, userId);
    }

    /**
     * 购物车选中某个商品
     */
    @RequestMapping("select.do")
    public ResponseResult select(Integer productId, HttpServletRequest httpServletRequest) {
        String loginInfo = LoginCookieUtil.getLoginInfo(httpServletRequest);
        UserDto userDto = JsonUtils.string2Object(loginInfo, UserDto.class);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户未登录");
        }
        Integer userId = userDto.getId();
        return cartService.select(productId, Constant.Cart.PRODUCT_SELECTED, userId);
    }

    /**
     * 购物车取消选中某个商品
     */
    @RequestMapping("un_select.do")
    public ResponseResult unSelect(Integer productId, HttpServletRequest httpServletRequest) {
        String loginInfo = LoginCookieUtil.getLoginInfo(httpServletRequest);
        UserDto userDto = JsonUtils.string2Object(loginInfo, UserDto.class);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户未登录");
        }
        Integer userId = userDto.getId();
        return cartService.select(productId,Constant.Cart.PRODUCT_UNSELECTED,userId);
    }
    /**
     * 购物车全选
     */
    @RequestMapping("select_all.do")
    public ResponseResult selectAll(HttpServletRequest httpServletRequest){
        String loginInfo = LoginCookieUtil.getLoginInfo(httpServletRequest);
        UserDto userDto = JsonUtils.string2Object(loginInfo, UserDto.class);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户未登录");
        }
        Integer userId = userDto.getId();
        return cartService.select(null,Constant.Cart.PRODUCT_SELECTEED_ALL, userId);
    }
    /**
     * 取消全选
     */
    @RequestMapping("un_select_all.do")
    public ResponseResult unSelectAll(HttpServletRequest httpServletRequest){
        String loginInfo = LoginCookieUtil.getLoginInfo(httpServletRequest);
        UserDto userDto = JsonUtils.string2Object(loginInfo, UserDto.class);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户未登录");
        }
        Integer userId = userDto.getId();
        return cartService.select(null,Constant.Cart.PRODUCT_UNSELECTEED_ALL, userId);
    }
    /**
     * 查询购物车里面产品的数量
     */
    @RequestMapping("get_cart_product_count.do")
    public ResponseResult getCount(HttpServletRequest httpServletRequest){
        String loginInfo = LoginCookieUtil.getLoginInfo(httpServletRequest);
        UserDto userDto = JsonUtils.string2Object(loginInfo, UserDto.class);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户未登录");
        }
        Integer userId = userDto.getId();
        return cartService.queryCount(userId);
    }

}
