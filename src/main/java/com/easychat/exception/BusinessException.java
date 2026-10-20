package com.easychat.exception;

import com.easychat.entity.enums.ResponseCodeEnum;

/**
 * 业务异常：由 service 层主动抛出，全局异常处理器统一转成 ResponseVO
 */
public class BusinessException extends RuntimeException {

    private ResponseCodeEnum codeEnum;

    private Integer code;

    private String message;

    public BusinessException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(Throwable e) {
        super(e);
    }

    public BusinessException(ResponseCodeEnum codeEnum) {
        super(codeEnum.getMsg());
        this.codeEnum = codeEnum;
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMsg();
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ResponseCodeEnum getCodeEnum() {
        return codeEnum;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 业务异常不需要堆栈信息，重写后不再填充堆栈，提升抛异常的性能
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
