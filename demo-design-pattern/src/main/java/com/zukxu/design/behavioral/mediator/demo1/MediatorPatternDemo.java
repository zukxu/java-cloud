package com.zukxu.design.behavioral.mediator.demo1;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:50:47
 */
public class MediatorPatternDemo {

    public static void main(String[] args) {
        ChatRoom chatRoom = new ChatRoomImpl();

        // 创建用户
        User user1 = new User("Tom", chatRoom);
        User user2 = new User("Jerry", chatRoom);

        // 将用户加入聊天室
        chatRoom.addUser(user1);
        chatRoom.addUser(user2);

        // 用户之间发送消息
        user1.sendMessage(user2, "Hello, Jerry!");
        user2.sendMessage(user1, "Hi, Tom!");

    }

}
