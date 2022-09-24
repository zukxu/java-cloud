package com.zukxu.demoliteflow.enums;

/**
 * <p>
 * 环境变量
 * </p>
 *
 * @author xupu
 * @since 2022-05-06 17:00
 */
public enum Env {
    PROD("1"), DEV("0");

    private String env;

    Env(String env) {
        this.env = env;
    }

    public String getType() {
        return env;
    }
}
