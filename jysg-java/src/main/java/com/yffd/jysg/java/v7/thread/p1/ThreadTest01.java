package com.yffd.jysg.java.v7.thread.p1;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月23日 下午4:22:40 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class ThreadTest01 {
	public static final int SIZE = 1024;
	
	public static void main(String[] args) {
		test1();
	}
	
	public static void test1() {
		for (int i=0; i<1000; i++) {
			byte[] bytes = new byte[1024];
		}
	}

}

