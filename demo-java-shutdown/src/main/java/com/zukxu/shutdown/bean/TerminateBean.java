package com.zukxu.shutdown.bean;

import javax.annotation.PreDestroy;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/16 0016 14:43
 */
public class TerminateBean {
    @PreDestroy
    public void preDestroy() {
        System.out.println("TerminalBean is destroyed");
    }
}
