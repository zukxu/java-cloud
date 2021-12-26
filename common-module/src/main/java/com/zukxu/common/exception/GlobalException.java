package com.zukxu.common.exception;

/**
 * @author xupu
 * @Description 全局异常
 * @Date 2021-09-16 17:08
 */
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = -181924189544935193L;

    private String message;
    private String details;

    public GlobalException() {}

    public GlobalException(String message) {
        this.message = message;
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
