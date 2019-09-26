package com.yffd.jysg.java.v7.thread.p3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月30日 下午4:04:06 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CountDownLatchTest {

	public static void main(String[] args) throws Exception {
		int size = 10;
		ExecutorService pool = Executors.newFixedThreadPool(3);
		final CountDownLatch startSignal = new CountDownLatch(1);
		final CountDownLatch doneSignal = new CountDownLatch(size);
		
		for (int i = 0; i < size; i++) {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println(Thread.currentThread().getName() + ">>>准备开始");
						startSignal.await();
						System.out.println(Thread.currentThread().getName() + ">>>工作中");
						Thread.sleep(3000);
						doneSignal.countDown();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		System.out.println("准备完成。。。准备开始执行");
		Thread.sleep(2000);
		startSignal.countDown();
		doneSignal.await();
		
		System.out.println("已完成。。。。。");
		pool.shutdownNow();
		
		if (true) return;
		
		
		
		
		
		
		
		
		
		
		for (int i = 0; i < size; i++) {
			final int num = i;
			pool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(5);
						System.out.println(Thread.currentThread().getName() + " >>完成<< " + num);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					doneSignal.countDown();
				}
				
			});
		}
		try {
			doneSignal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("done...");
		pool.shutdown();
	}

}

