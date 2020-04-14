package com.litian.test;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: MyHttpServletRequest.java
 * @time: 2020/4/14 16:30
 * @desc: |针对http协议定义的Servlet基类
 */

public class MyHttpServlet extends MyGenericServlet{
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request;
        HttpServletResponse response;
        try {
            request = (HttpServletRequest)req;
            response = (HttpServletResponse)res;
        } catch (ClassCastException var6) {
            throw new ServletException("http.non_http");
        }

        this.service(request, response);
    }

    public void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        // 1. 获取请求方式
        String method = servletRequest.getMethod();

        // 2. 根据请求方式再调用对应的处理方法
        if("GET".equalsIgnoreCase(method)){
            doGet(servletRequest, servletResponse);
        }

        if("POST".equalsIgnoreCase(method)){
            doPost(servletRequest, servletResponse);
        }
    }

    public void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
    }

    public void doGet(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
    }
}
