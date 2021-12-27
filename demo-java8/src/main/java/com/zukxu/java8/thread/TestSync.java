package com.zukxu.java8.thread;

/**
 * test synchronized
 *
 * @author zukxu
 * CreateTime: 2021/5/7 0007 23:18
 */
public class TestSync implements Runnable {

		private String name;

		//private static MethodSync methodSync = new MethodSync();
		private final MethodSync methodSync = new MethodSync();

		public TestSync(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			methodSync.method(name);
		}

		public static void main(String[] args) {
			Thread t1 = new Thread(new TestSync("A"));
			Thread t2 = new Thread(new TestSync("B"));
			t1.start();
			t2.start();
		}

	public static class MethodSync {

		public synchronized void method(String name) {
			System.out.println(name + " start a sync method");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(name + " end a sync method");
		}
	}
}
