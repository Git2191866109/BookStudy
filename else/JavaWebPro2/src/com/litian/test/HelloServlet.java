package com.litian.test;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: HelloServlet.java
 * @time: 2020/4/10 12:20
 * @desc: |
 */

public class HelloServlet implements Servlet {

    public HelloServlet() {
        System.out.println("HelloServlet构造器已启动...");
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("初始化中...");
    }

    @Override
    public ServletConfig getServletConfig() {
        System.out.println("获取Servlet配置中...");
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("服务中...");
    }

    @Override
    public String getServletInfo() {
        System.out.println("获取Servlet信息中...");
        return null;
    }

    @Override
    public void destroy() {
        System.out.println("毁灭中...");
    }
}
