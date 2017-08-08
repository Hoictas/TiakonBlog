package com.tiakon.junit;

import com.tiakon.entity.User;
import com.tiakon.service.UserService;
import com.tiakon.service.impl.UserServiceImpl;
import com.tiakon.utils.JDBCUtil;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

/**
 * Created by Hoictas on 2017/8/8.
 */
public class Junit5 {
    @Test
    void testGetConn() {
        Connection connection = JDBCUtil.getConnection();
        System.out.println(connection);
    }

    @Test
    void testUserService() {
        UserService userService = new UserServiceImpl();
        User user = new User();
        user.setUserName("tk");
        user.setPassword("tk");
        try {
            User login = userService.login(user);
            System.out.println(login);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
