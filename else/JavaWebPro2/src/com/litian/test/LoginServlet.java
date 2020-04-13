package com.litian.test;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: LoginServlet.java
 * @time: 2020/4/13 11:45
 * @desc: |
 */

public class LoginServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("请求来了！");
        System.out.println(servletRequest);

        String user = servletRequest.getParameter("user");
        String psd = servletRequest.getParameter("password");
        System.out.println(user + "-->" + psd);

        String interesting = servletRequest.getParameter("interesting");
        System.out.println(interesting);

        String[] interestings = servletRequest.getParameterValues("interesting");
        System.out.println(interestings);
        for(String a: interestings){
            System.out.println(a);
        }

        Enumeration<String> names = servletRequest.getParameterNames();
        while(names.hasMoreElements()){
            String name = names.nextElement();
            String val = servletRequest.getParameter(name);

            System.out.println("3. " + name + ": " + val);
        }

        Map<String, String[]> map = servletRequest.getParameterMap();
        for(Map.Entry<String, String[]> entry: map.entrySet()){
            System.out.println("4. " + entry.getKey() + ": " + Arrays.asList(entry.getValue()));
        }

        HttpServletRequest hsr = (HttpServletRequest) servletRequest;
        String requestURI = hsr.getRequestURI();
        System.out.println(requestURI);

        String method = hsr.getMethod();
        System.out.println(method);

        String qs = hsr.getQueryString();
        System.out.println(qs);

        String sp = hsr.getServletPath();
        System.out.println(sp);

        // 设置响应的内容类型：这里设置成word格式，那么submit之后会让你下载一个文件，文件可以用word打开，内容是write中的内容。
        servletResponse.setContentType("application/msword");

        PrintWriter out = servletResponse.getWriter();
        out.println("What are you talking about?");

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
