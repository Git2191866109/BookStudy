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

        // 1. 获取请求参数id

        // 2. 调用CustomerDAO的get方法获取和id对应的Customer对象customer

        // 3. 将customer放入request中

        // 4. 响应updatecustomer.jsp页面：转发


    private void update(HttpServletRequest request, HttpServletResponse response) {
        // 1. 获取表单参数：id，name，address，phone，oldName

        // 2. 比较name和oldName是否相同：若相同说明name可用。
        // 2.1 若不相同，调用CustomerDao的getCountWithName方法获取name在数据库中是否存在

        // 2.2 若返回值大于0，则响应 updatecustomer.jsp页面：
        // 通过转发的方式来响应 updatecustomer.jsp
        if (count > 0) {
            // 2.2.1 要求在 updatecustomer.jsp页面显示一个错误消息：该用户名已被占用，请重新选择！
            // 在request中放入一个属性message：该用户名已被占用，请重新选择！
            // 在页面上通过request.getAttribute("message")的方式来显示
            request.setAttribute("message", "该用户名【" + name + "】已被占用，请重新选择！");

            // 2.2.2 updatecustomer.jsp的表单值可以回显。
            // 其中：address和phone显示提交表单的新的值，而name显示oldName而不是新提交的name
            // 2.2.3 结束方法：return
            request.getRequestDispatcher("/newcustomer.jsp").forward(request, response);
            return;
        }
        // 3. 若验证通过，则把表单参数封装为一个Customer对象customer
        Customer cc = new Customer(name, address, phone);
        // 4. 调用CustomerDao的update方法执行更新操作
        dao.save(cc);
        // 5. 重定向到query.do
        response.sendRedirect("success.jsp");
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

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取表单参数：name，address，phone
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");

        // 2. 检验名字是否被占用
        // 2.1 调用CustomerDao的getCountWithName方法获取name在数据库中是否存在
        long count = dao.getCountWithNames(name);
        // 2.2 若返回值大于0，则响应newcustomer.jsp页面：
        // 通过转发的方式来响应newcustomer.jsp
        if (count > 0) {
            // 2.2.1 要求在newcustomer.jsp页面显示一个错误消息：该用户名已被占用，请重新选择！
            // 在request中放入一个属性message：该用户名已被占用，请重新选择！
            // 在页面上通过request.getAttribute("message")的方式来显示
            request.setAttribute("message", "该用户名【" + name + "】已被占用，请重新选择！");

            // 2.2.2 newcustomer.jsp的表单值可以回显。
            // 方式：<td><input type="text" name="name" value="<%=request.getParameter("name") == null ? "": request.getParameter("name")%>"/></td>
            // 2.2.3 结束方法：return
            request.getRequestDispatcher("/newcustomer.jsp").forward(request, response);
            return;
        }
        // 3. 若验证通过，则把表单参数封装为一个Customer对象customer
        Customer cc = new Customer(name, address, phone);
        // 4. 调用CustomerDao的save方法执行保存操作
        dao.save(cc);
        // 5. 重定向到success.jsp页面：使用重定向可以避免出现表单的重复提交问题。
        // System.out.println(request.getParameter("name"));
        response.sendRedirect("success.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
