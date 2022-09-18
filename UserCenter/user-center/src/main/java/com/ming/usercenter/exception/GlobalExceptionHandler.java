package com.ming.usercenter.exception;

import com.ming.usercenter.common.BaseResponse;
import com.ming.usercenter.common.ErrorCode;
import com.ming.usercenter.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BusinessException.class})
    public BaseResponse busiessExceptionHandler(BusinessException exception) {
        log.error("businessException" + exception.getMessage(), exception);
        return ResultUtils.error(exception.getCode(), exception.getMessage(), exception.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException exception) {
        log.error("runtimeException", exception);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, exception.getMessage(), "");
    }
}
