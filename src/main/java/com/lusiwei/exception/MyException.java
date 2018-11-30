package com.lusiwei.exception;

/**
 * @Author: lusiwei
 * @Date: 2018/11/24 16:37
 * @Description:
 */
public class MyException extends RuntimeException {
    private String message="自定义异常";

    public MyException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
