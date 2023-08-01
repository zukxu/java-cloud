package com.zukxu.design.behavioral.observer.demo1;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 实现了Subject接口，并定义了一个数据属性data。
 * registerObserver、removeObserver和notifyObservers方法的实现与上面定义的接口中的描述一致。
 * 当setData方法被调用时，它会将传递进来的数据保存到data属性中，并调用notifyObservers方法通知所有的观察者。
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:36:21
 */

public class ConcreteSubject implements Subject {

    private List<Observer> observers;

    private Object data;

    public ConcreteSubject() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(data);
        }
    }

    public void setData(Object data) {
        this.data = data;
        notifyObservers();
    }

}
