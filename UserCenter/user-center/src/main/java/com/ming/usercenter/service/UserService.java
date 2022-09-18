package com.ming.usercenter.service;

import com.ming.usercenter.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 *
 * @author Administrator
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2022-09-02 18:54:17
 */
public interface UserService extends IService<User> {
    String SALT = "UserCenter";


    /**
     * 用户注册
     *
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String email,String studentNumber);

    /**
     * 用户登录
     *
     * @param loginInWay
     * @param password
     * @return 返回用户脱敏信息
     */
    User userLogin(String loginInWay, String password, HttpServletRequest request);


    /**
     * 用户信息脱敏
     *
     * @param user
     * @return 返回 脱敏了的用户信息
     */
    User desensitizationUser(User user);


    /**
     * 请求注销用户
     *
     * @return
     */
    Integer userLogout(HttpServletRequest request);
}
