package com.zukxu.design.behavioral.mediator.demo1;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 聊天室
 * 在上面的代码中，我们使用一个Map来维护用户列表，每当有用户加入或离开聊天室时，我们都需要更新用户列表。
 * 在sendMessage方法中，我们根据接收者的名字从用户列表中获取接收者对象，并将消息发送给接收者。
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:47:41
 */
public class ChatRoomImpl implements ChatRoom {

    private Map<String, User> users = new HashMap<>();

    @Override
    public void sendMessage(User sender, User receiver, String message) {
        receiver.receiveMessage(sender, message);
    }

    @Override
    public void addUser(User user) {
        users.put(user.getName(), user);
    }

    public void removeUser(User user) {
        users.remove(user.getName());
    }

}

