package com.ming.usercenter.utils;

import com.ming.usercenter.common.BaseResponse;
import com.ming.usercenter.common.ErrorCode;

/**
 * 返回工具类
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse<>(errorCode, message, description);
    }

    public static BaseResponse error(ErrorCode errorCode, String message) {
        return new BaseResponse<>(errorCode, message, "");
    }

    public static BaseResponse error(Integer code, String message, String description) {
        return new BaseResponse<>(code,null, message, description);
    }
}
