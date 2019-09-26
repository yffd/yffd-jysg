package com.yffd.jysg.java.v7.jvm.mat.p2;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月26日 下午4:04:24 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class JavaThreadTest extends Thread {
	public static void main(String[] args) throws Exception {
        Thread.sleep(5000);
		JavaThreadTest mt1 = new JavaThreadTest("Thread a");
		JavaThreadTest mt2 = new JavaThreadTest("Thread b");
        
        mt1.setName("My-Thread-1 ");
        mt2.setName("My-Thread-2 ");
        
        mt1.start();
        mt2.start();
    }
    
    public JavaThreadTest(String name) {
    }

    public void run() {
        
        while (true) {
            
        }
    }
}

