package com.ming.usercenter.pojo.request;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserUpdate implements Serializable {

    private static final long serialVersionUID = -2344374416463603971L;
    private Long id;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private Integer gender;


    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户状态 0-正常
     */
    private Integer userStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否注销账号 0-未注销账号 1-已注销账号
     */
    private Integer isDelete;

    /**
     * 0 - 普通用户
     * 1 - 管理员
     */
    private Integer userRole;

}
