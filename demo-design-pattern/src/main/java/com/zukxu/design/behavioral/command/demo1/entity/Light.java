package com.zukxu.design.behavioral.command.demo1.entity;

/**
 * <p>
 * 灯光
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 15:05:34
 */
public class Light {

    private boolean on;

    public void switchOn() {
        System.out.println("Light is on");
        on = true;
    }

    public void switchOff() {
        System.out.println("Light is off");
        on = false;
    }

    public boolean isOn() {
        return on;
    }

}
