package com.yffd.jysg.java.v7.thread.p3;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月30日 下午3:38:05 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class ThreadPool {
	private static final int POOL_SIZE = 5;
	private static final ExecutorService servicePool = Executors.newFixedThreadPool(POOL_SIZE);
	
	public static void main(String[] args) throws Exception {
//		test1();
		test2();
	}

	private static void test2() throws Exception {
		try {
			while(true) {
				Callable<Double> task = new Callable<Double>() {
					@Override
					public Double call() throws Exception {
						System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>" + Thread.currentThread().getName());
						return Math.random();
					}
				};
				Future<Double> result = servicePool.submit(task);
				System.out.println(result.get());
			}
		} finally {
			servicePool.shutdown();
		}
	}

	private static void test1() {
		try {
			for (int i = 0; i < 9; i++) {
				final int num = i;
				servicePool.execute(new Runnable() {
					@Override
					public void run() {
						System.out.println(Thread.currentThread() + "：num=" + num);
					}
					
				});
			}
		} finally {
			servicePool.shutdown();
		}
	}

}

