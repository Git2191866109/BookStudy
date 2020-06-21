package com.litian.javaweb;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "s1", urlPatterns = "/processStep1")
public class ProcessStep1Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取选中的图书的信息
        String [] books = request.getParameterValues("book");
        // 2. 把图书信息放入到HttpSession中
        request.getSession().setAttribute("books", books);
        // 3. 重定向页面到shoppingcart/step2.jsp
        System.out.println(request.getContextPath() + "/shoppingcart/step2.jsp");
        response.sendRedirect(request.getContextPath() + "/shoppingcart/step2.jsp");
    }
}
