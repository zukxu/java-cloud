package com.zukxu.design.behavioral.command;

import com.zukxu.design.behavioral.command.entity.Fan;
import com.zukxu.design.behavioral.command.entity.Light;

/**
 * <p>
 * 关闭
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 14:45:53
 */

public class SwitchOffCommand implements Command {

    private Light light;

    private Fan fan;

    public SwitchOffCommand(Light light, Fan fan) {
        this.light = light;
        this.fan = fan;
    }

    public void execute() {
        light.switchOff();
        fan.switchOff();
    }

    public void undo() {
        light.switchOn();
        fan.switchOn();
    }

}

