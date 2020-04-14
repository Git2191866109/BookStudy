package com.litian.test;

import javax.servlet.*;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: MyGenericServlet.java
 * @time: 2020/4/14 15:29
 * @desc: |自定义一个Servlet接口的实现类：让开发的任何Servlet都继承该类，以简化开发
 */

public abstract class MyGenericServlet implements Servlet, ServletConfig {
    private ServletConfig servletConfig;

    // 以下方法为Servlet接口的方法
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.servletConfig = servletConfig;
        init();
    }

    public void init() throws ServletException {
    }

    @Override
    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    @Override
    public abstract void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException;

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }

    // 以下方法为ServletConfig接口的方法
    @Override
    public String getServletName() {
        return servletConfig.getServletName();
    }

    @Override
    public ServletContext getServletContext() {
        return servletConfig.getServletContext();
    }

    @Override
    public String getInitParameter(String s) {
        return servletConfig.getInitParameter(s);
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return servletConfig.getInitParameterNames();
    }
}
