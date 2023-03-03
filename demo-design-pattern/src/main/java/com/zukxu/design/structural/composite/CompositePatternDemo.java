package com.zukxu.design.structural.composite;

/**
 * <p>
 * 示例
 * Component是抽象组件类，其中定义了一个operation()方法，用于执行操作。
 * Leaf是叶子组件类，实现了Component接口，并实现了operation()方法以执行特定操作。
 * Composite是组合组件类，实现了Component接口，并包含一个List类型的组件列表，用于存储组合组件中包含的组件。
 * Composite还实现了addComponent()和removeComponent()方法，用于添加或删除组件。
 * operation()方法实现了组合组件的操作，并遍历组件列表，对每个组件调用operation()方法。
 * <p>
 * 在示例代码中，
 * 创建了一个叶子组件和一个组合组件，并将叶子组件添加到组合组件中。
 * 最后，调用了组合组件的operation()方法，执行了组合组件的操作
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 17:26:04
 */
public class CompositePatternDemo {

    public static void main(String[] args) {
        Composite composite = new Composite();
        Component component1 = new Leaf();

        composite.addComponent(component1);
        composite.operation();
    }

}
