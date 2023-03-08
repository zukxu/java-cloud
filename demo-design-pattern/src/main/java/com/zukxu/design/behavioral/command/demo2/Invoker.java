package com.zukxu.design.behavioral.command.demo2;

import com.zukxu.design.behavioral.command.Command;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 15:19:58
 */
@Service
public class Invoker {

    /** 用于保存当前的命令 */
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void execute() {
        command.execute();
    }

    public void undo() {
        command.undo();
    }

}
