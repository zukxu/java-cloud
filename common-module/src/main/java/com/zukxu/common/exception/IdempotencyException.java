package com.zukxu.common.exception;

/**
 * <p>
 *  幂等性异常
 * </p>
 *
 * @author xupu
 * @since 2022/12/14 20:45
 */
public class IdempotencyException extends RuntimeException {
    private static final long serialVersionUID = -6214014474569672570L;

    public IdempotencyException() {}

    public IdempotencyException(String message) {
        super(message);
    }
}
