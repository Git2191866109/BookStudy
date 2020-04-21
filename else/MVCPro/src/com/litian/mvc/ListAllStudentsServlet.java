package com.litian.mvc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: ${NAME}.java
 * @time: 2020/4/21 19:21
 * @desc: |
 */

@WebServlet(name = "ListAllStudentsServlet", urlPatterns = "/listAllStudents")
public class ListAllStudentsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // request.setAttribute("lists", Arrays.asList("AA", "BB", "CC"));
        // request.getRequestDispatcher("/students.jsp").forward(request, response);

        StudentDao sd = new StudentDao();
        List<Student> students = sd.getAll();

        request.setAttribute("students", students);
        request.getRequestDispatcher("/students.jsp").forward(request, response);
    }
}
