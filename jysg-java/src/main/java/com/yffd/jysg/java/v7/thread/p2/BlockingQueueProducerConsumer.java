package com.yffd.jysg.java.v7.thread.p2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月30日 下午3:04:02 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class BlockingQueueProducerConsumer {

	public static void main(String[] args) {
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
		
		new Thread(new ProducerQueue(queue)).start();
		new Thread(new ConsumerQueue(queue)).start();
	}

}

class ProducerQueue implements Runnable {
	BlockingQueue<Integer> queue;
	int num = 0;
	public ProducerQueue(BlockingQueue<Integer> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while(true) {
			try {
				this.queue.put(++num);
				System.out.println(Thread.currentThread().getName() + "：放入-------" + num);
				Thread.sleep(7);
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>size:" + this.queue.size());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class ConsumerQueue implements Runnable {
	BlockingQueue<Integer> queue;
	
	public ConsumerQueue(BlockingQueue<Integer> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Integer num = this.queue.take();
				System.out.println(Thread.currentThread().getName() + "：取出**" + num);
				Thread.sleep(9);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
	

