package com.lusiwei.filter;

import com.lusiwei.common.Constant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: lusiwei
 * @Date: 2018/11/25 16:02
 * @Description:
 */
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        Object user = request.getSession().getAttribute(Constant.User.CURRENT_USER);
        if (user != null) {
            filterChain.doFilter(request,response);
        }else {
            response.sendRedirect("user-login.html");
        }

    }

    @Override
    public void destroy() {

    }
}
