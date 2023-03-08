package com.zukxu.design.behavioral.command;

/**
 * <p>
 * RemoteControl类有两个成员变量，分别是switchOnCommand和switchOffCommand。
 * 在构造函数中，我们将这两个成员变量初始化为SwitchOnCommand和SwitchOffCommand对象。
 * pressSwitchOnButton()方法会执行switchOnCommand命令，以打开灯和风扇。
 * pressSwitchOffButton()方法会执行switchOffCommand命令，以关闭灯和风扇。
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 15:12:47
 */
public class RemoteControl {

    private Command switchOnCommand;

    private Command switchOffCommand;

    public RemoteControl(Command switchOnCommand, Command switchOffCommand) {
        this.switchOnCommand = switchOnCommand;
        this.switchOffCommand = switchOffCommand;
    }

    public void pressSwitchOnButton() {
        switchOnCommand.execute();
    }

    public void pressSwitchOffButton() {
        switchOffCommand.execute();
    }

    public void pressUndoButton() {
        switchOnCommand.undo();
    }

}

