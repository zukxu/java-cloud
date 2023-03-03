package com.zukxu.design.structural.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 组合组件类
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 17:25:28
 */
public class Composite implements Component {

    private List<Component> components = new ArrayList<>();

    public void addComponent(Component component) {
        components.add(component);
    }

    public void removeComponent(Component component) {
        components.remove(component);
    }

    @Override
    public void operation() {
        System.out.println("执行组合组件操作");
        for(Component component : components) {
            component.operation();
        }
    }

}