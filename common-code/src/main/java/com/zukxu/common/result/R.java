package com.zukxu.common.result;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author xupu
 * @Description 封装返回类
 * @Date 2021-09-16 16:33
 */
@Data
@Slf4j
@Accessors(chain = true)
public class R<T> implements Serializable {
    /**
     * 仅反馈成功，无详细数据，请用这个
     */
    public static final R<Void> OK = new R<>(CommREnum.OK);
    /**
     * 未知错误，无详细数据，请用这个
     */
    public static final R<Void> FAIL = new R<>(CommREnum.FAIL);

    private static final long serialVersionUID = -45239331572204682L;
    private int code;
    private String msg;
    private T data;

    private R() {}

    private R(IREnum rEnum) {
        this.code = rEnum.code();
        this.msg = rEnum.message();
    }

    /**
     * @param data data
     * @param <T>  T
     *
     * @return R<T>
     */
    public static <T> R<T> ok(T data) {
        return genResult(CommREnum.OK, data);
    }

    /**
     * @param msg  消息
     * @param data 数据
     * @param <T>  T
     *
     * @return R<T>
     */
    public static <T> R<T> ok(String msg, T data) {
        return genResult(CommREnum.OK.getCode(), msg, data);
    }

    /**
     * @param msg msg
     * @param <T> <T>
     *
     * @return R
     */
    public static <T> R<T> fail(String msg) {
        return fail(msg, null);
    }

    /**
     * @param msg  msg
     * @param data data
     *
     * @return R<T>
     */
    public static <T> R<T> fail(String msg, T data) {
        return genResult(CommREnum.FAIL.getCode(), msg, data);
    }

    /**
     * @param code code int
     * @param msg  msg String
     * @param <T>  T
     *
     * @return R
     */
    public static <T> R<T> fail(int code, String msg) {
        return genResult(code, msg, null);
    }

    /**
     * @param status RStatus
     * @param <T>    T
     *
     * @return R
     */
    public static <T> R<T> fail(CommREnum status) {
        return genResult(status, null);
    }

    /**
     * @param status RStatus
     * @param data   data
     * @param <T>    T
     *
     * @return R
     */
    private static <T> R<T> genResult(CommREnum status, T data) {
        R<T> r = new R<>();
        r.setCode(status.getCode());
        r.setMsg(status.getMessage());
        r.setData(data);
        log.info("返回结果:{}", JSON.toJSONString(r));
        return r;
    }

    /**
     * generate a R instance
     *
     * @param code code
     * @param msg  msg
     * @param data data
     * @param <T>  <T>
     *
     * @return R
     */
    private static <T> R<T> genResult(int code, String msg, T data) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setData(data);
        r.setMsg(msg);
        log.info("返回结果:{}", JSON.toJSONString(r));
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
