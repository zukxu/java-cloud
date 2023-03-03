package com.zukxu.design.structural.bridge;

/**
 * <p>
 * 桥接模式
 * 定义了实现部分的接口Implementor，
 * 实现了两个具体的实现类ConcreteImplementorA和ConcreteImplementorB。
 * 定义了抽象部分的接口Abstraction，
 * 实现了两个扩展抽象部分类RefinedAbstractionA和RefinedAbstractionB。
 * <p>
 * 这些扩展类中都包含一个Implementor类型的成员变量，通过这个变量来调用实现部分的方法
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 17:13:13
 */
public class BridgePatternDemo {

    public static void main(String[] args) {
        Implementor implementorA = new ConcreteImplementorA();
        Abstraction abstractionA = new RefinedAbstractionA(implementorA);
        abstractionA.operation();

        Implementor implementorB = new ConcreteImplementorB();
        Abstraction abstractionB = new RefinedAbstractionB(implementorB);
        abstractionB.operation();
    }

}
