package com.litian.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: ${NAME}.java
 * @time: 2020/4/17 14:31
 * @desc: |
 */

@WebServlet(name = "RedirectServlet", urlPatterns = "/redirectServlet")
public class RedirectServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("RedirectServlet's doGet method! ↓");

        request.setAttribute("name", "RedirectServlet");
        System.out.println(request.getAttribute("name"));

        // 执行请求的重定向，直接调用response的sendRedirect方法
        // 其中path为要重定向的地址
        String location = "testServlet";
        response.sendRedirect(location);
    }
}
