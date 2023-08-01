package com.zukxu.common.exception;

/**
 * @author xupu
 * @Description 业务异常
 * @Date 2021-09-16 17:04
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 6457345120164751995L;

    private int code;
    private String message;
    private String details;

    public BusinessException() {}

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
