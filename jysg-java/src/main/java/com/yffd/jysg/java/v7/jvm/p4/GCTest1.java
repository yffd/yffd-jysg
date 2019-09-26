package com.yffd.jysg.java.v7.jvm.p4;

import java.util.Vector;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月10日 下午1:56:10 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class GCTest1 {

	public static void main(String[] args) {
		for (int i = 0; i < 150; i++) {
			System.out.println("-------num: " + i);
			printMemory();
			allocate(10);
		}
	}

	public static void method1() {
		Vector<byte[]> v = new Vector<>();
		for (int i = 0; i < 25; i++) {
			v.add(new byte[1 * 1024 * 1024]);
		}
	}
	
	public static void allocate(int mb) {
		byte[] bytes = new byte[mb * 1024 * 1024];
	}
	
	public static void printMemory() {
		System.out.println("max:  " + Runtime.getRuntime().maxMemory()/1024/1024 + "M");
		System.out.println("total:" + Runtime.getRuntime().totalMemory()/1024/1024 + "M");
		System.out.println("free: " + Runtime.getRuntime().freeMemory()/1024/1024 + "M");
	}
}

