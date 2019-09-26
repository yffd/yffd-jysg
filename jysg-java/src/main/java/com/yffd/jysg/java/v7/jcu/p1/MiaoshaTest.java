package com.yffd.jysg.java.v7.jcu.p1;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2019年1月7日 下午5:53:00 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class MiaoshaTest {
	static Queue<String> queue = new ConcurrentLinkedDeque<>();
	
	static {
		for (int i = 0; i < 10000; i++) {
			queue.add("string_" + i);
		}
	}
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						String tmp = queue.poll();
						if(null==tmp) break;
						System.out.println(Thread.currentThread().getName() + " - " + tmp);
					}
				}
				
			}, "thread-" + i).start();
		}
	}

}

