package com.yffd.jysg.java.v7.jvm.p2;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月7日 下午5:26:06 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class VolatileStopTest {

	public static void main(String[] args) throws Exception {
		VolatileStopThread t = new VolatileStopThread();
		
		t.start();
		Thread.sleep(1000);
		t.stopMe();
		Thread.sleep(1000);
	}

}

class VolatileStopThread extends Thread {
	private /*volatile*/ boolean stop = false;
	
	public void stopMe() {
		this.stop = true;
	}
	
	@Override
	public void run() {
		int i = 0;
		while(!stop) {
			System.out.println(i++);
		}
		System.out.println("stop thread");
	}
	
}
