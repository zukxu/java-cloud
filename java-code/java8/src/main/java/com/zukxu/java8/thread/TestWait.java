package com.zukxu.java8.thread;

/**
 * test wait and notify
 *
 * @author zukxu
 * CreateTime: 2021/5/7 0007 23:12
 */
public class TestWait {
	public static boolean flag = false;

	public static int num = 0;

	public static void main(String[] args) {
		Man man = new Man();

		new Thread(man::getRunnable1).start();
		new Thread(man::getRunnable2).start();
	}

	public static class Man {

		public synchronized void getRunnable1() {
			for (int i = 0; i < 20; i++) {
				while (flag) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("生产：" + (++num) + "个");
				flag = true;
				notify();
			}
		}

		public synchronized void getRunnable2() {
			for (int i = 0; i < 20; i++) {
				while (!flag) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				//模拟加载时间
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("取出：" + (num--) + "个");
				System.out.println("------------------");

				flag = false;
				notify();
			}
		}
	}
}
