package com.yffd.jysg.java.v7.thread.p3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月4日 下午4:42:16 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class AlternateABCD {
	//ABCABCABC…… 依次递归
	public static void main(String[] args) {
		MyTaskWork task = new MyTaskWork();
		int totalLoop = 5;
	
		new Thread(new Runnable() {
			@Override
			public void run() {
				task.loopA(totalLoop);
			}
		}, "A").start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				task.loopB(totalLoop);
			}
		}, "B").start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				task.loopC(totalLoop);
			}
		}, "C").start();
	}
}

class MyTaskWork {
	int threadNum = 1;
	ReentrantLock lock = new ReentrantLock();
	Condition c1 = lock.newCondition();
	Condition c2 = lock.newCondition();
	Condition c3 = lock.newCondition();
	
	public void loopA(int totalLoop) {
		lock.lock();
		try {
			if(threadNum!=1) {
				c1.await();
			}
			for (int i = 0; i < totalLoop; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
			}
			threadNum = 2;
			c2.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	public void loopB(int totalLoop) {
		lock.lock();
		try {
			if(threadNum!=2) {
				c2.await();
			}
			for (int i = 0; i < totalLoop; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
			}
			threadNum = 3;
			c3.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	public void loopC(int totalLoop) {
		lock.lock();
		try {
			if(threadNum!=3) {
				c3.await();
			}
			for (int i = 0; i < totalLoop; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
			}
			threadNum = 1;
			c1.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}

