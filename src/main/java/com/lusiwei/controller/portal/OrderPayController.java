package com.lusiwei.controller.portal;

import com.lusiwei.common.ResponseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Author: lusiwei
 * @Date: 2018/11/30 09:35
 * @Description:
 */
@RestController
@RequestMapping("/order")
public class OrderPayController {
    @RequestMapping("pay.do")
    public ResponseResult pay(Integer orderNo, HttpSession session) {

        return null;
    }

}
