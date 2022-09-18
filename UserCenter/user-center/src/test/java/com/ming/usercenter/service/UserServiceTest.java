package com.ming.usercenter.service;

import com.ming.usercenter.pojo.User;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void testAddUser() {
        User user = new User();
        user.setUsername("Xiaoming");
        user.setUserAccount("123");
        user.setAvatarUrl("https://himg.bdimg.com/sys/portrait/item/pp.1.710d4d60.WTM8p65W6-iIRGtzGsvJMg.jpg?tt=1662117985721");
        user.setGender(0);
        user.setUserPassword("qwe");
        user.setPhone("123");
        user.setEmail("456");
        boolean save = userService.save(user);
        System.out.println(user.getId());
        assertTrue(save);
    }

    @Test
    void userRegister() {
        // 非空
        long result = userService.userRegister("", "qwertqwert", "qwertqwert", "ravingxiaoming@gmail.com", "A19191919");
        assertEquals(-1, result);
        // 账户长度小于四位
        result = userService.userRegister("qwe", "qwertqwert", "qwertqwert", "ravingxiaoming@gmail.com", "A19191919");
        assertEquals(-1, result);
        // 密码小于 8 位
        result = userService.userRegister("qwer", "qwert", "qwert", "ravingxiaoming@gmail.com", "A19191919");
        assertEquals(-1, result);
        // 账户不能重复
        result = userService.userRegister("123", "qwertqwert", "qwertqwert", "ravingxiaoming@gmail.com", "A19191919");
        assertEquals(-1, result);
        // 密码不相同
        result = userService.userRegister("qwer", "qwertqwert", "qwertqwer", "ravingxiaoming@gmail.com", "A19191919");
        assertEquals(-1, result);
        // 账号包含特殊字符
        result = userService.userRegister("qw#er", "qwertqwert", "qwertqwert", "ravingxiaoming@gmail.com", "A19191919");
        assertEquals(-1, result);
        // 成功
        result = userService.userRegister("sdafjhk", "qwertqwert", "qwertqwert", "ravingxiaoming@gmail.com", "A19191919");
        assertFalse(result > 0);
    }


    @Test
    void testConnect() {
        List<User> list = userService.list();
        for (User user : list) {
            System.out.println(user);
        }
    }


}