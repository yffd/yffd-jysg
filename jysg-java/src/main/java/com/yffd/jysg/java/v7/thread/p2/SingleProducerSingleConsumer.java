package com.yffd.jysg.java.v7.thread.p2;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月30日 上午11:14:53 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class SingleProducerSingleConsumer {

	public static void main(String[] args) {
//		Resource rs = new Resource();
//		new Thread(new Producer(rs)).start();
//		new Thread(new Consumer(rs)).start();
		
		Resource1 rs1 = new Resource1();
		new Thread(new Producer1(rs1)).start();
		new Thread(new Consumer1(rs1)).start();
	}

}

class Producer implements Runnable {
	private Resource rs;
	
	public Producer(Resource rs) {
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
			this.rs.create();
		}
	}
	
}
class Consumer implements Runnable {
	private Resource rs;
	
	public Consumer(Resource rs) {
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

class Resource {
	/** 资源编号 */
	private int num = 0;
	/** 资源标记 */
	private boolean flag = false;
	
	public synchronized void create() {
		if(flag) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + "：生产者----" + ++num);
		flag = true;
		this.notify();
	}
	
	public synchronized void destroy() {
		if(!flag) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + "：消费者******" + num);
		flag = false;
		this.notify();
	}
}

class Producer1 implements Runnable {
	Resource1 rs1;
	public Producer1(Resource1 rs1) {
		this.rs1 = rs1;
	}
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(6);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.rs1.put();
		}
		
	}
}
class Consumer1 implements Runnable {
	Resource1 rs1;
	public Consumer1(Resource1 rs1) {
		this.rs1 = rs1;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.rs1.take();
		}
	}
	
}
class Resource1 {
	private int num = 0;
	private boolean flag = false;
	
	private Object lock = new Object();
	
	public void put() {
		synchronized(lock) {
			if(flag) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + "：放入-------" + ++num);
			flag = true;
			lock.notify();
		}
	}
	
	public void take() {
		synchronized(lock) {
			if(!flag) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + "：取出**" + num);
			flag = false;
			lock.notify();
		}
	}
}







































