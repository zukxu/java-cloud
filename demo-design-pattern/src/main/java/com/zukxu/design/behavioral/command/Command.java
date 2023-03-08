package com.zukxu.design.behavioral.command;

/**
 * <p>
 * 该接口包含一个执行方法和一个撤销方法
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 14:45:12
 */
public interface Command {

    void execute();

    void undo();

}
