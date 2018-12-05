package com.lusiwei.controller.portal;

import com.lusiwei.common.Constant;
import com.lusiwei.common.ResponseResult;
import com.lusiwei.dto.UserDto;
import com.lusiwei.exception.MyException;
import com.lusiwei.param.RegisterParam;
import com.lusiwei.pojo.User;
import com.lusiwei.service.UserService;
import com.lusiwei.util.JedisUtil;
import com.lusiwei.util.JsonUtils;
import com.lusiwei.util.LoginCookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: lusiwei
 * @Date: 2018/11/24 10:15
 * @Description:
 */
//@CrossOrigin(origins = "http://image.lusiwei.com:8088", allowCredentials = "true")
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "test.do")
    public User test() {
        User user = userService.test();
        System.out.println(user);
        return user;
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param httpServletResponse
     * @return
     */
    @PostMapping("login.do")
    public ResponseResult login(String username, String password, HttpServletResponse httpServletResponse, HttpSession session) {
        UserDto userDto = userService.login(username, password);
        if (userDto == null) {
            return ResponseResult.createSuccessResponse("用户没有登录");
        }
//        String token = UUID.randomUUID().toString() + "login";
        String token = session.getId();
        //将生成的token添加到cookie中
        LoginCookieUtil.addCookie(httpServletResponse, token);
        //设置到redis中并设置过期时间
        JedisUtil.getJedis().setex(token, Constant.RedisLoginExpire.EXPIRE, JsonUtils.object2Json(userDto));
        return ResponseResult.createSuccessResponse(userDto);
    }

    @PostMapping("register.do")
    public ResponseResult register(RegisterParam registerParam) {
        return userService.register(registerParam);
    }

    @RequestMapping("get_user_info.do")
    public ResponseResult getUserInfo(HttpServletRequest httpServletRequest) {
        String loginInfo = LoginCookieUtil.getLoginInfo(httpServletRequest);
        UserDto user = JsonUtils.string2Object(loginInfo, UserDto.class);
        if (user == null) {
            throw new MyException("用户未登录，无法获取当前用户信息");
        }
        System.out.println(user);
        return ResponseResult.createSuccessResponse(user);
    }

    @RequestMapping("forget_get_question.do")
    public ResponseResult getQuestion(String username) {
        return userService.queryQuestion(username);
    }

    @RequestMapping("forget_check_answer.do")
    public ResponseResult checkAnswer(String username, String question, String answer) {
        return userService.checkAnswer(username, question, answer);
    }

    @RequestMapping("forget_reset_password.do")//TODO: token
    public ResponseResult forgetPassword(String username, String passwordNew, String forgetToken) {
        return ResponseResult.createSuccessResponse();
    }

    /**
     * 登录状态重置密码
     */
    @RequestMapping("reset_password.do")
    public ResponseResult resetPassword(String passwordOld, String passwordNew, HttpServletRequest httpServletRequest) {
        String loginInfo = LoginCookieUtil.getLoginInfo(httpServletRequest);
        UserDto userDto = JsonUtils.string2Object(loginInfo, UserDto.class);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户未登录");
        }
        String currentUserName = userDto.getUsername();
        return userService.resetPassword(passwordOld, passwordNew, currentUserName);
    }

    /**
     *
     */
    @RequestMapping("update_information.do")
    public ResponseResult updateInformation(String email, String phone, String question, String answer, HttpServletRequest httpServletRequest) {
        String loginInfo = LoginCookieUtil.getLoginInfo(httpServletRequest);
        UserDto userDto = JsonUtils.string2Object(loginInfo, UserDto.class);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户未登录");
        }
        return userService.updateInformation(email, phone, question, answer, userDto);
    }

    @RequestMapping("get_information.do")
    public ResponseResult getInformation(HttpServletRequest httpServletRequest) {
        String loginInfo = LoginCookieUtil.getLoginInfo(httpServletRequest);
        UserDto userDto = JsonUtils.string2Object(loginInfo, UserDto.class);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户未登录");
        }
        User user = userService.getInformation(userDto.getId());
        return ResponseResult.createSuccessResponse(user);
    }

    @RequestMapping("logout.do")
    public ResponseResult logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        LoginCookieUtil.removeLoginToken(httpServletRequest, httpServletResponse);
        return ResponseResult.createSuccessResponse("退出成功");
    }

}
