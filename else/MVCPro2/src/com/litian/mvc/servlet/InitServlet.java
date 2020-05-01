package com.litian.mvc.servlet;

import com.litian.mvc.dao.factory.CustomerDAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: ${NAME}.java
 * @time: 2020/5/1 18:24
 * @desc: |
 */

// 设置应用启动的时候，自动加载该Servlet
@WebServlet(name = "InitServlet", loadOnStartup = 1)
public class InitServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {

        // 读取类路径下的配置文件
        InputStream in = getServletContext().getResourceAsStream("/WEB-INF/classes/switch.properties");
        Properties pp = new Properties();
        try {
            pp.load(in);
            // 获取type属性值
            String type = pp.getProperty("type");
            // 赋给了CustomerDAOFactory的type属性。
            CustomerDAOFactory.getInstance().setType(type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
