package com.ming.usercenter.pojo.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: xiaoming
 * @date: 2022/09/12 18:47
 */
@Data
public class UserDelete implements Serializable {
    private static final long serialVersionUID = -1410039480865381350L;
    private Long id;
}
