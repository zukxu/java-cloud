package com.zukxu.design.structural.facade;

/**
 * <p>
 * 外观类
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 17:43:44
 */
public class Computer {

    private CPU cpu;

    private Memory memory;

    private HardDisk hardDisk;

    public Computer() {
        this.cpu = new CPU();
        this.memory = new Memory();
        this.hardDisk = new HardDisk();
    }

    public void start() {
        cpu.start();
        memory.start();
        hardDisk.start();
        System.out.println("Computer started!");
    }

    public void shutdown() {
        cpu.shutdown();
        memory.shutdown();
        hardDisk.shutdown();
        System.out.println("Computer shutdown!");
    }

}

