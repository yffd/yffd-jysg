package com.yffd.jysg.java.v7.thread.p3;

import java.util.concurrent.Semaphore;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月30日 下午6:02:17 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class SemaphoreTest {

	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(5);
		int num = 69;
		for (int i = 0; i < num; i++) {
			new Thread(new WorkTask(semaphore, i)).start();
		}
	}
	
}

class WorkTask implements Runnable {
	Semaphore semaphore;
	int num;
	
	public WorkTask(Semaphore semaphore, int num) {
		this.semaphore = semaphore;
		this.num = num;
	}

	@Override
	public void run() {
		try {
			this.semaphore.acquire();
			System.out.println(Thread.currentThread().getName() + "：正在执行。。。" + num);
			Thread.sleep((long) (Math.random() * 1000));
			this.semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

