package com.yffd.jysg.java.v7.thread.p2;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月30日 下午2:04:46 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class SingleProducerMultiConsumer {

	public static void main(String[] args) {
		ResourceA rs = new ResourceA();
		new Thread(new ProducerA(rs)).start();
		new Thread(new ConsumerA(rs)).start();
		new Thread(new ConsumerA(rs)).start();
		new Thread(new ConsumerA(rs)).start();

	}

}

class ProducerA implements Runnable {
	ResourceA rs;
	public ProducerA(ResourceA rs) {
		this.rs = rs;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(7);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.rs.create();
		}
		
	}
}
class ConsumerA implements Runnable {
	ResourceA rs;
	
	public ConsumerA(ResourceA rs) {
		this.rs = rs;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.rs.destroy();
		}
	}
	
}
class ResourceA {
	private int num = 0;
	private boolean flag = false;
	
	private Object lock = new Object();
	
	public void create() {
		synchronized(lock) {
			while(flag) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + "：生产------" + ++num);
			flag = true;
			lock.notify();
		}
	}
	
	public void destroy() {
		synchronized(lock) {
			while(!flag) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + "：销毁**" + num);
			flag = false;
			lock.notify();
		}
	}
}

