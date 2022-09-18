package com.ming.usercenter.pojo.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author Xiaoming
 */
@Data
public class UserRegisterRequest implements Serializable {

    // 序列化id  防止序列化过程中出现冲突
    private static final long serialVersionUID = 782025566164879709L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String email;
    private String studentNumber;
}
