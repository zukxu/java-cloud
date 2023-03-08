package com.zukxu.design.behavioral.templateMethod.demo1;

/**
 * <p>
 * 模板方法模式
 * 抽象类AbstractClass定义了算法的骨架，其中包含了一个模板方法templateMethod()和两个抽象方法primitiveOperation1()和primitiveOperation2()。
 * 具体子类ConcreteClass实现了这两个抽象方法。在主函数中，我们通过实例化ConcreteClass对象来调用templateMethod()方法
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 11:41:25
 */
public class TemplateMethodPatternDemo {

    public static void main(String[] args) {
        AbstractClass ac = new ConcreteClass();
        ac.templateMethod();
    }

}
