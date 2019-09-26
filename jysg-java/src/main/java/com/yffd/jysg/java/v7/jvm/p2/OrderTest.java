package com.yffd.jysg.java.v7.jvm.p2;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月7日 下午5:48:10 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class OrderTest {

	public static void main(String[] args) {
		OrderExample order = new OrderExample();
		
		Runnable r1 = new Runnable() {
			@Override
			public void run() {
				order.writer();
			}
		};
		
		Runnable r2 = new Runnable() {
			@Override
			public void run() {
				order.reader();
			}
		};
		
		new Thread(r1).start();
		new Thread(r2).start();
	}

}

class OrderExample {
	int a = 0;
	boolean flag = false;

	public void writer() {
	    a = 1;                   
	    flag = true;           
	}

	public void reader() {
	    if (flag) {                
	        System.out.println(a+1);     
	    }
	}

}

