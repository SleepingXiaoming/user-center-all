package com.ming.usercenter.pojo.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 1608273850971212735L;

    private String loginInWay;
    private String userPassword;
}
