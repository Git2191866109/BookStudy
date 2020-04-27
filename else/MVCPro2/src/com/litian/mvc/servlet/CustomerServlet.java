package com.litian.mvc.servlet;

import com.litian.mvc.dao.CriteriaCustomer;
import com.litian.mvc.dao.CustomerDao;
import com.litian.mvc.dao.impl.CustomerDAOJdbcImpl;
import com.litian.mvc.domain.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

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
    private CustomerDao dao = new CustomerDAOJdbcImpl();

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

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("id");
        int id = 0;

        // try catch的作用：防止idStr不能转为int类型
        // 若不能转，则id=0时，无法进行任何的删除操作
        try {
            id = Integer.parseInt(idStr);
            dao.delete(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        response.sendRedirect("query.do");
    }

    private void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取模糊查询的请求参数
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        // 把请求参数封装为一个CriteriaCustomer
        CriteriaCustomer cc = new CriteriaCustomer(name, address, phone);

        // 1. 调用CustomerDao的getAll（划去）->getForListWithCriteriaCustomer得到Customer的集合
        // List<Customer> customers = dao.getAll();
        List<Customer> customers = dao.getForListWithCriteriaCustomer(cc);
        // 2. 把Customer的集合放入request中
        request.setAttribute("customers", customers);
        // 3. 转发页面到index.jsp中（不能使用重定向）
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("add");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
