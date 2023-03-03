package com.zukxu.design.structural.proxy;

/**
 * <p>
 * 抽象主题接口 Subject 定义了代理类和真实主题类都要实现的方法 request()。
 * 真实主题类 RealSubject 实现了 Subject 接口，并且实现了自己的 request() 方法。
 * 代理类 Proxy 也实现了 Subject 接口，并且在实现 request() 方法时通过实例化一个真实主题类对象 RealSubject 来实现具体的业务逻辑。
 * 这个过程中，代理类可以在真实主题类方法执行之前或之后进行额外的逻辑处理
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 17:57:29
 */
public class ProxyPatternDemo {

    public static void main(String[] args) {
        Proxy proxy = new Proxy();
        proxy.request();
    }

}
