package com.ming.usercenter.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 错误码
 */
@AllArgsConstructor
public enum ErrorCode {
    SUCCESS(0, "ok", ""),
    PARAMS_ERROR(40000, "请求参数错误", ""),
    NULL_ERROR(40001, "请求数据为空", ""),
    WRONG_LOGIN(40002, "账户或者密码错误", ""),
    NOT_LOGIN(40100, "未登录", ""),
    NO_AUTH(40101, "无权限", ""),
    SYSTEM_ERROR(50000, "系统内部异常", "");


    /**
     * 状态码信息
     */
    private final Integer code; // 错误码
    /**
     * 状态码描述(详情)
     */
    private final String message; // 简略错误信息
    /**
     *
     */
    private final String description; // 错误描述

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
