package com.yffd.jysg.java.v7.thread.p3;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月30日 下午5:22:21 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CyclicBarrierTest {

	public static void main(String[] args) {
		int num = 10;
		CyclicBarrier barrier = new CyclicBarrier(num, new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "：特殊任务");
			}
			
		});
		for (int i = 1; i <= num; i++) {
			new Thread(new WriterTask(barrier, 500 * i)).start();
		}
	}
}

class WriterTask implements Runnable {
	CyclicBarrier barrier;
	long sleepTime = 0;
	public WriterTask(CyclicBarrier barrier, long sleepTime) {
		this.barrier = barrier;
		this.sleepTime = sleepTime;
	}

	@Override
	public void run() {
		try {
			System.out.println(Thread.currentThread().getName() + "：开始写入");
			Thread.sleep(sleepTime);
			System.out.println(Thread.currentThread().getName() + "：写入完成，等待其他线程写入完毕");
			barrier.await();
			System.out.println(Thread.currentThread().getName() + "：写入完成，后续任务");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		
	}
	
}