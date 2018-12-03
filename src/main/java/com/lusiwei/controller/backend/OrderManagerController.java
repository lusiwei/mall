package com.lusiwei.controller.backend;

import com.lusiwei.common.ResponseResult;
import com.lusiwei.service.OrderManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lusiwei
 * @Date: 2018/11/30 15:43
 * @Description:
 */
@RestController
@RequestMapping("/manage/order")
public class OrderManagerController {
    @Autowired
    private OrderManagerService orderManagerService;

    @RequestMapping("list")
    public ResponseResult list(){
        return null;
    }

}
