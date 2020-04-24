package com.litian.mvc.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: ${NAME}.java
 * @time: 2020/4/24 12:34
 * @desc: |
 */

// @WebServlet(name = "CustomerServlet", urlPatterns = "/customerServlet")
@WebServlet(name = "CustomerServlet", urlPatterns = "*.do")
public class CustomerServlet extends HttpServlet {
    /*
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        switch (method) {
            case "add":
                add(request, response);
                break;
            case "query":
                query(request, response);
                break;
            case "delete":
                delete(request, response);
                break;
        }
    }
    */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取ServletPath：/edit.do或/addCustomer.do
        String servletPath = req.getServletPath();
        // 2. 去除/和.do，得到类似于edit或addCustomer这样的字符串
        String methodName = servletPath.substring(1);
        methodName = methodName.substring(0, methodName.length() - 3);
        // System.out.println(methodName);

        try {
            // 3. 利用反射获取methodName对应的方法
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // 4. 利用反射调用对应的方法
            method.invoke(this, req, resp);
        } catch (Exception e) {
            // e.printStackTrace();
            // 对没有的方法可以由一些良好的响应（重定向）
            resp.sendRedirect("error.jsp?code=CannotFindMethod");
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("update");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("delete");
    }

    private void query(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("query");
    }

    private void add(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("add");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
