package com.lusiwei.common;

import com.lusiwei.exception.MyException;
import com.lusiwei.util.JsonUtil;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: lusiwei
 * @Date: 2018/11/24 16:27
 * @Description: 自定义全局异常处理器
 */
public class GlobalHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ResponseResult responseResult;
        if (e instanceof MyException) {
            responseResult = ResponseResult.createFailResponse(e.getMessage());
        } else {
            responseResult = ResponseResult.createFailResponse();
        }
        write(responseResult, httpServletResponse);
        return null;
    }

    /**
     * 将异常返回结果以json格式 返回 前台
     *
     * @param responseResult
     * @param httpServletResponse
     */
    private static void write(ResponseResult responseResult, HttpServletResponse httpServletResponse) {
        httpServletResponse.setContentType("text/html;charset=utf-8");
        PrintWriter writer;
        try {
            writer = httpServletResponse.getWriter();
            String response=JsonUtil.javaBeanToJson(responseResult);
            writer.write(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
