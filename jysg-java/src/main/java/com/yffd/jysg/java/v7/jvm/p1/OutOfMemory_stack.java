package com.yffd.jysg.java.v7.jvm.p1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月18日 下午4:43:45 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class OutOfMemory_stack {

	// -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -Xss1m
	public static void main(String[] args) throws Exception {
		AtomicInteger num = new AtomicInteger(0);
		method(num);
		System.in.read();
	}
	
	public static void method(AtomicInteger num) {
		System.out.println(num.getAndIncrement());
		printMemory();
		method(num);
	}
	
	public static void printMemory() {
		System.out.println("free size  :" + Runtime.getRuntime().freeMemory() / (1024 *1024) + " M");
		System.out.println("max size   :" + Runtime.getRuntime().maxMemory() / (1024 *1024) + " M");
		System.out.println("total size :" + Runtime.getRuntime().totalMemory() / (1024 *1024) + " M");
		System.out.println("************");
	}
	
}

