package com.zukxu.design.behavioral.observer.demo1;

/**
 * <p>
 * 主题接口
 * registerObserver：注册一个观察者，将其添加到观察者列表中。
 * removeObserver：移除一个观察者，将其从观察者列表中删除。
 * notifyObservers：通知所有的观察者，让它们可以更新自己的状态。
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:35:03
 */
public interface Subject {

    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();

}

