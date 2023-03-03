package com.zukxu.design.creational.builder;

/**
 * <p>
 * 使用建造者模式可以方便地构建复杂对象，避免了调用者直接设置对象属性的问题
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 16:02:26
 */
public class BuilderPatternDemo {

    public static void main(String[] args) {
        User.Builder builder = new User.Builder("zuk", "xu");
        User user = builder.age(18).address("GZ").build();
        System.out.println(user);
    }

}
