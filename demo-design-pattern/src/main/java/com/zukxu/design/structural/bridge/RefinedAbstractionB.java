package com.zukxu.design.structural.bridge;

/**
 * <p>
 * 扩展抽象部分B
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 17:15:45
 */
public class RefinedAbstractionB implements Abstraction {

    Implementor implementor;

    public RefinedAbstractionB(Implementor implementor) {
        this.implementor = implementor;
    }

    @Override
    public void operation() {
        implementor.operationImpl();
    }

}
