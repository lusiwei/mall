package com.lusiwei.controller.portal;

import com.alibaba.druid.sql.visitor.functions.Concat;
import com.lusiwei.common.Constant;
import com.lusiwei.common.ResponseResult;
import com.lusiwei.dto.UserDto;
import com.lusiwei.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Author: lusiwei
 * @Date: 2018/11/30 15:47
 * @Description:
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping("create.do")
    public ResponseResult create(Integer shippingId, HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute(Constant.User.CURRENT_USER);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户需要登录");
        }
        return orderService.create(shippingId, userDto.getId());
    }

    @RequestMapping("cancel.do")
    public ResponseResult cacel(HttpSession session, Long orderNo) {
        UserDto userDto = (UserDto) session.getAttribute(Constant.User.CURRENT_USER);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户需要登录");
        }
        return orderService.cancel(userDto.getId(), orderNo);
    }

    @RequestMapping("get_order_cart_product.do")
    public ResponseResult getOrderCartProduct(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute(Constant.User.CURRENT_USER);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户需要登录");
        }
        return orderService.getOrderCartProduct(userDto.getId());
    }

    @RequestMapping("detail.do")
    public ResponseResult detail(HttpSession session, Long orderNo) {
        UserDto userDto = (UserDto) session.getAttribute(Constant.User.CURRENT_USER);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户需要登录");
        }
        return orderService.detail(userDto.getId(), orderNo);

    }

    @RequestMapping("list.do")
    public ResponseResult list(HttpSession session, @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum) {
        UserDto userDto = (UserDto) session.getAttribute(Constant.User.CURRENT_USER);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户需要登录");
        }
        return orderService.list(userDto.getId(), pageSize, pageNum);

    }
}
