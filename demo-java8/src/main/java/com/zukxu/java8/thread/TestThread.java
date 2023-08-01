package com.zukxu.java8.thread;

/**
 * extend thread
 *
 * @author zukxu
 * CreateTime: 2021/5/7 0007 23:05
 */

public class TestThread extends Thread {
    private int count = 5;
    private String name;

    public TestThread(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        TestThread mTh1 = new TestThread("A");
        TestThread mTh2 = new TestThread("B");
        mTh1.start();
        mTh2.start();
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + "运行  count= " + count--);
            try {
                sleep((int) (Math.random() * 10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}

