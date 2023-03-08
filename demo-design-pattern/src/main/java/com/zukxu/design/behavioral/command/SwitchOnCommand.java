package com.zukxu.design.behavioral.command;

import com.zukxu.design.behavioral.command.entity.Fan;
import com.zukxu.design.behavioral.command.entity.Light;

/**
 * <p>
 * 开启
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 14:45:53
 */
public class SwitchOnCommand implements Command {

    private Light light;

    private Fan fan;

    public SwitchOnCommand(Light light, Fan fan) {
        this.light = light;
        this.fan = fan;
    }

    public void execute() {
        light.switchOn();
        fan.switchOn();
    }

    public void undo() {
        light.switchOff();
        fan.switchOff();
    }

}



