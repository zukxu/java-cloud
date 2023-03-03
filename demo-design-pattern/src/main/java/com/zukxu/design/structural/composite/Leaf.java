package com.zukxu.design.structural.composite;

/**
 * <p>
 * 叶子组件类
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 17:24:56
 */
public class Leaf implements Component {

    @Override
    public void operation() {
        System.out.println("执行叶子组件操作");
    }

}
