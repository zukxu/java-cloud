package com.zukxu.design.behavioral.mediator.demo1;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:48:05
 */
public class User {

    private String name;

    private ChatRoom chatRoom;

    public User(String name, ChatRoom chatRoom) {
        this.name = name;
        this.chatRoom = chatRoom;
    }

    public void sendMessage(User receiver, String message) {
        chatRoom.sendMessage(this, receiver, message);
    }

    public void receiveMessage(User sender, String message) {
        System.out.printf("%s received message from %s: %s%n", name, sender.getName(), message);
    }

    public String getName() {
        return name;
    }

}

