package com.yffd.jysg.java.v7.jvm.p1;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月18日 下午3:31:38 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class OutOfMemory_heap {

	// -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
	public static void main(String[] args) throws Exception {
		
		List<byte[][]> list = new ArrayList<>();
		for (int i = 0; i < 1024 * 1024; i++) {
			System.out.println("num :" + i);
			printMemory();
			byte[][] bytes = new byte[1024][1024]; // 1M
			list.add(bytes);
		}
		
//		oom_heap();
	}
	
	public static void printMemory() {
		System.out.println("free size  :" + Runtime.getRuntime().freeMemory() / (1024 *1024) + " M");
		System.out.println("max size   :" + Runtime.getRuntime().maxMemory() / (1024 *1024) + " M");
		System.out.println("total size :" + Runtime.getRuntime().totalMemory() / (1024 *1024) + " M");
		System.out.println("************");
	}
	
	public static void oom_heap() throws Exception {
		// -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -Xss1m
//		Thread.sleep(10000);
		for (int i = 0; i < 1000000; i++) {
			System.out.println("num :" + i);
			printMemory();
			new Thread(new SleepThread()).start();
		}
	}

	public static class SleepThread implements Runnable {
		@Override
		public void run() {
			try {
				System.out.println("thread name:" + Thread.currentThread().getName());
				Thread.sleep(10000000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

