package com.lusiwei.util;

import com.lusiwei.common.Constant;
import com.lusiwei.exception.MyException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: lusiwei
 * @Date: 2018/12/5 15:04
 * @Description:
 */
public class LoginCookieUtil {
    private static final String DOMAIN = "lusiwei.com";
    private static final String LOGIN_TOKEN_KEY = "login_token";

    /**
     * 保存登录cookie
     */
    public static void addCookie(HttpServletResponse response, String loginValue) {
        Cookie cookie = new Cookie(LOGIN_TOKEN_KEY, loginValue);
        //设置域名
        cookie.setDomain(DOMAIN);
        //设置路径
        cookie.setPath("/");
        //设置过期时间
        cookie.setMaxAge(Constant.Cookie.COOKIE_MAXAGE);
        response.addCookie(cookie);
    }

    /**
     * 获取登录信息
     */
    public static String getLoginInfo(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LOGIN_TOKEN_KEY)) {
                token = cookie.getValue();
                break;
            }
        }
        if (token == null) {
            throw new MyException("未登录");
        }
        //获取到token后从redis中取
        return JedisUtil.getJedis().get(token);
    }

    /**
     * 注销时删除
     */
    public static void removeLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(LOGIN_TOKEN_KEY, null);
        cookie.setDomain(DOMAIN);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
