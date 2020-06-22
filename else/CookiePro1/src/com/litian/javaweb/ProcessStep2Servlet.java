package com.litian.javaweb;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: ${NAME}.java
 * @time: 2020/6/22 10:18
 * @desc: |
 */

@WebServlet(name = "ProcessStep2Servlet", urlPatterns = "/processStep2")
public class ProcessStep2Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取请求参数：name，address，cardType，card
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String cardType = request.getParameter("cardType");
        String card = request.getParameter("card");

        // 把客户信息封装到customer对象中
        Customer customer = new Customer(name, address, cardType, card);

        // 2. 把请求信息存入到HttpSession中
        HttpSession session = request.getSession();
        session.setAttribute("customer", customer);

        // 3. 重定向页面到confirm.jsp
        response.sendRedirect(request.getContextPath() + "/shoppingcart/confirm.jsp");
    }

}
