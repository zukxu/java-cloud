package com.zukxu.design.behavioral.observer.demo1;

/**
 * <p>
 * 观察者接口
 * 定义了一个update方法，用于接收主题发生变化时传递的数据。
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:35:17
 */

public interface Observer {

    void update(Object data);

}
