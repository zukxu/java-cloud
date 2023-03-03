package com.zukxu.design.structural.bridge;

/**
 * <p>
 * 扩展抽象部分A
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 17:15:45
 */
public class RefinedAbstractionA implements Abstraction {

    Implementor implementor;

    public RefinedAbstractionA(Implementor implementor) {
        this.implementor = implementor;
    }

    @Override
    public void operation() {
        implementor.operationImpl();
    }

}
