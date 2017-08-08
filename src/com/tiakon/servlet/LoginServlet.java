package com.tiakon.servlet;

import com.tiakon.entity.User;
import com.tiakon.service.UserService;
import com.tiakon.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Hoictas on 2017/8/8.
 */
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        HttpSession session = request.getSession();
        //System.out.println(username);
        //System.out.println(password);
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        UserService userService = new UserServiceImpl();
        User resultSetUser = null;
        try {
            resultSetUser = userService.login(user);
        } catch (Exception e) {
            new RuntimeException(e.getMessage());
        }
        if (resultSetUser == null) {
            System.out.println("登录失败");
            session.setAttribute("user", user);
            session.setAttribute("error", "用户名或密码错误");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            session.setAttribute("currentUser", resultSetUser);
            response.sendRedirect("main.jsp");
        }

    }
}
