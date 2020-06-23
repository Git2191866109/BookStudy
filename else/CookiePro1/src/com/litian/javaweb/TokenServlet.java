package com.litian.javaweb;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "tokenServlet", urlPatterns = "/tokenServlet")
public class TokenServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
        String token = request.getParameter("token");
        if("litian".equals(token)){
            // 清除标记：没有方法清除固定的请求参数
        }
        */

        HttpSession session = request.getSession();
        // Object token = request.getAttribute("token");
        Object token = session.getAttribute("token");
        String tokenValue = request.getParameter("token");
        System.out.println("token -> " + token);
        System.out.println("tokenValue -> " + tokenValue);

        if (token != null && token.equals(tokenValue)) {
            // request.removeAttribute("token");
            session.removeAttribute("token");
        } else {
            response.sendRedirect(request.getContextPath() + "/token/token.jsp");
            return;
        }

        String name = request.getParameter("name");
        // 访问数据库服务器...
        System.out.println("name: " + name);

        response.sendRedirect(request.getContextPath() + "/token/success.jsp");
    }

}
