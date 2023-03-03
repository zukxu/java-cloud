package com.zukxu.design.structural.adapter;

/**
 * <p>
 * Target是目标接口，定义了客户端可以使用的方法。
 * Adaptee是被适配者，已经存在的不兼容接口。
 * ClassAdapter和ObjectAdapter都是适配器，用来把Adaptee适配成Target接口。
 * <p>
 * ClassAdapter使用了继承，继承了Adaptee类，同时也实现了Target接口。这个适配器重写了request()方法，将specificRequest()方法调用了一遍。
 * <p>
 * ObjectAdapter使用了组合，持有了一个Adaptee对象，同时实现了Target接口。这个适配器在实现request()方法时，将specificRequest()方法调用了一遍
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 17:07:54
 */
public class AdapterPatternDemo {

    public static void main(String[] args) {
        Target adapter1 = new ClassAdapter();
        adapter1.request();

        Adaptee adaptee = new Adaptee();
        Target adapter2 = new ObjectAdapter(adaptee);
        adapter2.request();
    }

}
