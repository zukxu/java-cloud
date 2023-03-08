package com.zukxu.design.behavioral.command.entity;

/**
 * <p>
 * 风扇
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 15:14:23
 */
public class Fan {

    private boolean on;

    public void switchOn() {
        System.out.println("Fan is on");
        on = true;
    }

    public void switchOff() {
        System.out.println("Fan is off");
        on = false;
    }

    public boolean isOn() {
        return on;
    }

}
