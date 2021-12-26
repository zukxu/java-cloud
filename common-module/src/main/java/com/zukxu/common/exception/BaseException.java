package com.zukxu.common.exception;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-16 17:03
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = -6214014474569672570L;

    public BaseException() {}

    public BaseException(String message) {
        super(message);
    }
}
