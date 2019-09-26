package com.yffd.jysg.java.v7.thread.p3;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月30日 下午6:02:46 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class AlternateMulti {

	public static void main(String[] args) {
		AtomicInteger count = new AtomicInteger(0);
		Task a = new Task(count, 4, 0, "A");
		Task b = new Task(count, 4, 1, "B");
		Task c = new Task(count, 4, 2, "C");
		Task d = new Task(count, 4, 3, "D");
		a.start();
		b.start();
		c.start();
		d.start();
	}
	
}

class Task extends Thread {
	AtomicInteger count;
	int threadCount;
	int threadOrder;
	String printWord;
	
	public Task(AtomicInteger count, int threadCount, int threadOrder, String flag) {
		this.count = count;
		this.threadCount = threadCount;
		this.threadOrder = threadOrder;
		this.printWord = flag;
	}

	@Override
	public void run() {
		while(true) {
			synchronized (count) {
				if ((count.get() % threadCount) == threadOrder) {
					System.out.println(Thread.currentThread().getName() + "：" + printWord + "：>>" + count.getAndAdd(1));
					count.notifyAll();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					try {
						count.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}

