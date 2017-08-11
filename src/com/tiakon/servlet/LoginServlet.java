package com.tiakon.servlet;

import com.tiakon.entity.User;
import com.tiakon.service.UserService;
import com.tiakon.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.*;
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
        System.out.println("LoginServlet.java");
        User resultSetUser;
        HttpSession session = request.getSession();
        try {
            String username = request.getParameter("username").trim();
            String password = request.getParameter("password").trim();
            String remember = request.getParameter("remember");
            //System.out.println("remember-me:" + remember);
            User user = new User();
            user.setUserName(username);
            user.setPassword(password);

            UserService userService = new UserServiceImpl();
            resultSetUser = userService.login(user);

            if (resultSetUser == null) {
                System.out.println("登录失败");
                request.setAttribute("user", user);
                request.setAttribute("error", "用户名或密码错误");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            } else {
                if ("remember-me".equals(remember)) {
                    this.addCookies(username, password, response);
                }
                session.setAttribute("currentUser", resultSetUser);
                request.getRequestDispatcher("MainServlet").forward(request, response);
                return;
            }
        } catch (Exception e) {
            new RuntimeException(e);
        }finally {
            //在浏览器上直接输入/LoginServlet变白页，所以在这里添加处理。
            response.sendRedirect("/login.jsp");
            return;

        }

    }

    protected void addCookies(String userName, String password, HttpServletResponse response) {
        Cookie cookie = new Cookie("user", userName + "-" + password);
        //设置cookie的有效时间（秒）
        cookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(cookie);
    }
}
