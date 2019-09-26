package com.yffd.jysg.java.v7.thread.p1;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月22日 下午6:03:21 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class ThreadTest {

	public static void main(String[] args) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("bb");
			}
		}) {
			@Override
			public void run() {
				System.out.println("aa");
			}
		}.start();
	}

}

