package com.ming.usercenter.pojo.request;

import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: xiaoming
 * @date: 2022/09/12 19:39
 */
@Data
public class UserSearch {
//    private Long id;
    private String username;
    private String account;
    private Integer gender;
    private String phone;
    private String email;
    private Integer userStatus;
    private Date createTime;
    private Integer userRole;
    private String studentNumber;
}
