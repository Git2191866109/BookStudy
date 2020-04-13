package com.litian.test;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: LoginServlet.java
 * @time: 2020/4/13 14:17
 * @desc: |
 */

public class LoginServlet implements Servlet {
    private ServletConfig servletConfig;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.servletConfig = servletConfig;
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        // 1. 获取请求参数：username和password
        String username = servletRequest.getParameter("username");
        String password = servletRequest.getParameter("password");

        // 2. 获取当前WEB应用的初始化参数：user，password
        // 需要使用ServletContext对象
        ServletContext sc = servletConfig.getServletContext();
        String initUser = sc.getInitParameter("user");
        String initPassword = sc.getInitParameter("password");

        // 3. 比对
        // 设置中文乱码问题
        servletResponse.setCharacterEncoding("utf-8");
        // 设置响应的内容类型
        servletResponse.setContentType("text/html;charset=utf-8");

        PrintWriter out = servletResponse.getWriter();
        if(initUser.equals(username) && initPassword.equals(password)){
            // 4. 打印响应字符
            out.println("Hello: " + username);
        }else{
            out.println("Sorry: " + username + "，你甭想进来！");
        }
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
