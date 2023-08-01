package com.zukxu.gencode.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xupu
 * @Description 封装数据格式返回
 * @Date 2021-09-15 9:20
 */
@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = -45239331572204682L;

    private int code;
    private String msg;
    private T data;

    private R() {}

    /**
     * 通用返回成功
     *
     * @return R<Void>
     */
    public static <T> R<T> ok() {
        return ok(null);
    }

    /**
     * @param data data
     * @param <T>  T
     * @return R<T>
     */
    public static <T> R<T> ok(T data) {
        return genResult(RStatus.OK, data);
    }

    /**
     * @param msg  消息
     * @param data 数据
     * @param <T>  T
     * @return R<T>
     */
    public static <T> R<T> ok(String msg, T data) {
        return genResult(RStatus.OK.getCode(), msg, data);
    }

    /**
     * 通用返回失败，未知错误
     *
     * @return R<T>
     */
    public static <T> R<T> fail() {
        return genResult(RStatus.FAIL, null);
    }

    /**
     * @param msg msg
     * @param <T> <T>
     * @return R
     */
    public static <T> R<T> fail(String msg) {
        return fail(msg, null);
    }

    /**
     * @param msg  msg
     * @param data data
     * @return R<T>
     */
    public static <T> R<T> fail(String msg, T data) {
        return genResult(RStatus.FAIL.getCode(), msg, data);
    }

    /**
     * @param code code int
     * @param msg  msg String
     * @param <T>  T
     * @return R
     */
    public static <T> R<T> fail(int code, String msg) {
        return genResult(code, msg, null);
    }

    /**
     * @param status RStatus
     * @param data   T data
     * @param <T>    T
     * @return R
     */
    public static <T> R<T> fail(RStatus status, T data) {
        return genResult(status, data);
    }

    /**
     * @param status RStatus
     * @param data   data
     * @param <T>    T
     * @return R
     */
    private static <T> R<T> genResult(RStatus status, T data) {
        R<T> r = new R<>();
        r.setCode(status.getCode());
        r.setMsg(status.getMsg());
        r.setData(data);
        return r;
    }

    /**
     * generate a R instance
     *
     * @param code code
     * @param msg  msg
     * @param data data
     * @param <T>  <T>
     * @return R
     */
    private static <T> R<T> genResult(int code, String msg, T data) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }

    //chain calls methods
    //R.ok().code(200).message("成功").data(null);
    //R.fail().code(500).message("失败").data(null);
    public R<T> data(T data) {
        this.setData(data);
        return this;
    }

    public R<T> message(String message) {
        this.setMsg(message);
        return this;
    }

    public R<T> code(Integer code) {
        this.setCode(code);
        return this;
    }
}
