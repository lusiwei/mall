package com.lusiwei.controller.backend;

import com.lusiwei.common.ResponseResult;
import com.lusiwei.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lusiwei
 * @Date: 2018/11/26 17:14
 * @Description:
 */
@RestController
@RequestMapping("manage/user")
public class UserManagerController {
    private final UserService userService;

    @Autowired
    public UserManagerController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("login.do")
    public ResponseResult login(String username,String password){
        return null;
    }
}
