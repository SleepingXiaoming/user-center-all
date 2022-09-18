package com.ming.usercenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class UserCenterApplicationTests {


    @Test
    void contextLoads() {

    }


    // 测试加密
    @Test
    void testDigest() throws NoSuchAlgorithmException {
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(("abcd" + "password").getBytes());
        System.out.println(md5DigestAsHex);
    }

}
