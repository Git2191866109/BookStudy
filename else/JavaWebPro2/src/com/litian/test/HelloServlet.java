package com.litian.test;

import javax.servlet.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

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
        // 1. ServletConfig
        // 针对Servlet的初始化参数
        System.out.println("初始化中...");
        String user = servletConfig.getInitParameter("user");
        System.out.println("user: " + user);
        Enumeration<String> names = servletConfig.getInitParameterNames();
        while(names.hasMoreElements()){
            String name = names.nextElement();
            String value = servletConfig.getInitParameter(name);
            System.out.println(name + "-->" + value);
        }
        String servletName = servletConfig.getServletName();
        System.out.println(servletName);

        // 2. ServletContext
        // 针对app的初始化参数，相较于上面，该参数适用于所有Servlet，相当于全局的参数
        // 2.1 获取ServletContext对象
        ServletContext sc = servletConfig.getServletContext();
        // 获取WEB应用程序的初始化参数
        String driver = sc.getInitParameter("driver");
        System.out.println("driver: " + driver);
        Enumeration<String> names2 = sc.getInitParameterNames();
        while(names2.hasMoreElements()){
            String name = names2.nextElement();
            String value = sc.getInitParameter(name);
            System.out.println(name + "-->" + value);
        }

        // 2.2 获取当前WEB应用的某一个文件在服务器上的绝对路径：
        String realPath = sc.getRealPath("/index.jsp");
        System.out.println(realPath);

        // 2.3 获取当前WEB应用的名称
        String contextPath = sc.getContextPath();
        System.out.println(contextPath);

        // 2.4 获取当前WEB应用的某一个文件对应的输入流
        // path的 / 响度与当前WEB应用的根目录
        try {
            ClassLoader cl = getClass().getClassLoader();
            InputStream is = cl.getResourceAsStream("jdbc.properties");
            System.out.println("1. " + is);

            InputStream is2 = sc.getResourceAsStream("jdbc.properties");
            System.out.println("2. " + is2);

            InputStream is3 = sc.getResourceAsStream("/WEB-INF/classes/jdbc.properties");
            System.out.println("3. " + is3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2.5 和attribute相关的几个方法

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
