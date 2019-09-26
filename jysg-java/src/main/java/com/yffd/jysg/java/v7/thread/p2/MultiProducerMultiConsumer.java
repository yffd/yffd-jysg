package com.yffd.jysg.java.v7.thread.p2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月30日 下午2:19:51 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class MultiProducerMultiConsumer {

	public static void main(String[] args) {
//		ResourceC rs = new ResourceC();
//		new Thread(new ProducerC(rs)).start();
//		new Thread(new ProducerC(rs)).start();
//		new Thread(new ProducerC(rs)).start();
//		new Thread(new ProducerC(rs)).start();
//		
//		new Thread(new ConsumerC(rs)).start();
//		new Thread(new ConsumerC(rs)).start();
//		new Thread(new ConsumerC(rs)).start();
		
		ResourceD rs = new ResourceD();
		new Thread(new ProducerD(rs)).start();
		new Thread(new ProducerD(rs)).start();
		new Thread(new ProducerD(rs)).start();
		new Thread(new ProducerD(rs)).start();
		new Thread(new ProducerD(rs)).start();
		
		new Thread(new ConsumerD(rs)).start();
		new Thread(new ConsumerD(rs)).start();
		new Thread(new ConsumerD(rs)).start();
	}

}

class ProducerC implements Runnable {
	ResourceC rs;
	
	public ProducerC(ResourceC rs) {
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

class ConsumerC implements Runnable {
	ResourceC rs;
	
	public ConsumerC(ResourceC rs) {
		this.rs = rs;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(6);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.rs.destroy();
		}
	}
	
}
class ResourceC {
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
			System.out.println(Thread.currentThread().getName() + "：生产---" + ++num);
			flag = true;
			lock.notifyAll();
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
			System.out.println(Thread.currentThread().getName() + "：销毁**********" + num);
			flag = false;
			lock.notifyAll();
		}
	}
	
}


class ConsumerD implements Runnable {
	ResourceD rs;
	
	public ConsumerD(ResourceD rs) {
		this.rs = rs;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(9);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.rs.destroy();
		}
	}
}

class ProducerD implements Runnable {
	ResourceD rs;
	
	public ProducerD(ResourceD rs) {
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

class ResourceD {
	private int num = 0;
	private boolean flag = false;
	
	private Lock lock = new ReentrantLock();
	private Condition conditionA = lock.newCondition();
	private Condition conditionB = lock.newCondition();
	
	public void create() {
		lock.lock();
		try {
			while(flag) {
				try {
					conditionA.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + "：生产--" + ++num);
			flag = true;
			conditionB.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	public void destroy() {
		lock.lock();
		try {
			while(!flag) {
				try {
					conditionB.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + "：销毁******" + num);
			flag = false;
			conditionA.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
}
