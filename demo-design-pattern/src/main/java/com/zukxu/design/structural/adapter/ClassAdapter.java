package com.zukxu.design.structural.adapter;

/**
 * <p>
 * 类适配器
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 17:06:35
 */
public class ClassAdapter extends Adaptee implements Target {

    @Override
    public void request() {
        specificRequest();
    }

}
