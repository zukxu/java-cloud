package com.zukxu.design.structural.proxy;

/**
 * <p>
 * 真实主题类
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 17:56:30
 */
public class RealSubject implements Subject {

    @Override
    public void request() {
        System.out.println("RealSubject's request() method is called.");
    }

}
