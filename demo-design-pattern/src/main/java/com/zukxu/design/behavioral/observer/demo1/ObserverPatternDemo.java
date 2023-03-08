package com.zukxu.design.behavioral.observer.demo1;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:38:32
 */
public class ObserverPatternDemo {

    public static void main(String[] args) {
        ConcreteSubject subject = new ConcreteSubject();

        // 创建两个观察者
        Observer observer1 = new ConcreteObserver("Observer 1");
        Observer observer2 = new ConcreteObserver("Observer 2");

        // 注册观察者到主题中
        subject.registerObserver(observer1);
        subject.registerObserver(observer2);

        // 主题发生变化，通知所有的观察者
        subject.setData("Hello World!");

        // 移除一个观察者
        subject.removeObserver(observer2);

        // 主题再次发生变化，通知所有的观察者
        subject.setData("Goodbye!");
    }

}
