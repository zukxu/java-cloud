package com.zukxu.design.structural.adapter;

/**
 * <p>
 * 对象适配器
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 17:07:10
 */
public class ObjectAdapter implements Target {

    private Adaptee adaptee;

    public ObjectAdapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void request() {
        adaptee.specificRequest();
    }

}
