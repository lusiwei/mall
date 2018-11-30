package com.lusiwei.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

/**
 * @Author: lusiwei
 * @Date: 2018/11/24 15:08
 * @Description:
 */
public class ResponseResult<T> {
    private int status;
    private String msg;
    private T data;

    ResponseResult() {

    }

    private ResponseResult(int status) {
        this.status = status;
    }

    private ResponseResult(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ResponseResult(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ResponseResult(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
    @JSONField(serialize = false)
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public static <T> ResponseResult<T> createSuccessResponse() {
        return new ResponseResult<>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ResponseResult<T> createSuccessResponse(String msg) {
        return new ResponseResult<>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ResponseResult<T> createSuccessResponse(T data) {
        return new ResponseResult<>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> ResponseResult<T> createSuccessResponse(String msg, T data) {
        return new ResponseResult<>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ResponseResult<T> createFailResponse() {
        return new ResponseResult<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> ResponseResult<T> createFailResponse(String errorMessage) {
        return new ResponseResult<>(ResponseCode.ERROR.getCode(), errorMessage);
    }

    public static <T> ResponseResult<T> createFailResponse(int errorCode, String errorMessage) {
        return new ResponseResult<>(errorCode, errorMessage);
    }


}
