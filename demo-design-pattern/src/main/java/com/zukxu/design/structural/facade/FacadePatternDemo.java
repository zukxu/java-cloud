package com.zukxu.design.structural.facade;

/**
 * <p>
 * 示例
 * Computer作为外观类，对CPU、Memory和HardDisk三个子系统类进行了封装，
 * 提供了start()和shutdown()两个统一的接口供客户端使用。
 * 客户端只需要创建一个Computer对象，
 * 然后调用start()和shutdown()方法即可完成计算机的开启和关闭操作。
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 17:45:36
 */
public class FacadePatternDemo {

    public static void main(String[] args) {
        Computer computer = new Computer();
        computer.start();
        computer.shutdown();
    }

}
