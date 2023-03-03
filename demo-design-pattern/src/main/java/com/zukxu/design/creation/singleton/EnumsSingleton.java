package com.zukxu.design.creation.singleton;

/**
 * <p>
 * 枚举单例
 * <br/>
 * 使用枚举实现单例模式是一种简单且线程安全的方式。
 * 因为枚举类型在Java中是线程安全的，并且只有在类被加载时才会创建其唯一的实例
 *
 * 枚举类型的构造函数默认是私有的，因此不能从外部实例化枚举类型
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 15:29:49
 */
public enum EnumsSingleton {
    INSTANCE;

    // 添加需要的成员变量和方法
    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // 添加需要的业务方法
    public void printInfo() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
    //    使用方法如下
    /*因为我们只有一个Singleton的枚举常量INSTANCE，所以我们可以轻松地对其进行操作并调用其方法。
    Singleton.INSTANCE.setName("Tom");
    Singleton.INSTANCE.setAge(30);
    Singleton.INSTANCE.printInfo();
    * */
}
