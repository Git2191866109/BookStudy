package com.litian.test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: ${NAME}.java
 * @time: 2020/4/17 14:18
 * @desc: |
 */

public class ForwardServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ForwardServlet's doGet! ↓");

        request.setAttribute("name", "ForwardServlet");
        System.out.println(request.getAttribute("name"));

        // 请求的转发
        // 1. 调用HttServletRequest的getRequestDispatcher()方法获取RequestDispatcher对象
        // 调用getRequestDispatcher()需要传入要转发的地址
        String path = "testServlet";
        RequestDispatcher rd = request.getRequestDispatcher("/" + path);

        // 2. 调用HttpServletRequest的forward(request, response)方法进行请求的转发
        rd.forward(request, response);
    }
}
