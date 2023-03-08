package com.zukxu.design.behavioral.mediator.demo1;

/**
 * <p>
 * 聊天室接口
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:47:41
 */
public interface ChatRoom {

    void sendMessage(User sender, User receiver, String message);

    void addUser(User user);

}
