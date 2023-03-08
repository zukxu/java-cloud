package com.zukxu.design.behavioral.observer.demo1;

/**
 * <p>
 * 实现了Observer接口，并定义了一个名称属性name。当接收到主题发生变化的通知时，会调用update方法，并打印接收到的数据
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:37:27
 */
public class ConcreteObserver implements Observer {

    private String name;

    public ConcreteObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(Object data) {
        System.out.println(name + " received data: " + data);
    }

}
