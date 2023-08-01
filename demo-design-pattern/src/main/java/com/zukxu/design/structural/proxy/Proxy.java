package com.zukxu.design.structural.proxy;

/**
 * <p>
 * 代理类
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 17:56:57
 */
public class Proxy implements Subject {

    private RealSubject realSubject;

    @Override
    public void request() {
        if (realSubject == null) {
            realSubject = new RealSubject();
        }
        System.out.println("Proxy's request() method is called.");
        realSubject.request();
    }

}
