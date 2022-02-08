package com.zukxu.mybatis.typehandler.entity;

/**
 * <p>
 * 加密实体
 * </p>
 *
 * @author xupu
 * @since 2022-02-08 10:39
 */
public class Encrypt {
    private String value;

    public Encrypt() {
    }

    public Encrypt(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
