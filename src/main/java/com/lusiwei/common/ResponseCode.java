package com.lusiwei.common;

/**
 * @Author: lusiwei
 * @Date: 2018/11/24 15:44
 * @Description:
 */
public enum ResponseCode {
    /**
     * 请求成功
     */
    SUCCESS(0, "成功"),
    /**
     * 请求错误
     */
    ERROR(1, "系统错误"),
    NUll_USERNAME_PASSWORD(1,"用户名或密码错误"),
    USERNAME_ERROR(1,"用户名错误"),
    PASSWORD_ERROR(1, "密码错误"),
    UNLOGIN(10,"未登录"),
    ON_SALE(1, "在售"),
    ;
    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
