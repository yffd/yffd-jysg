package com.yffd.jysg.java.v7.jvm.mat.p2;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月26日 下午4:22:57 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class JavaDeadLock {
	public static void main(String[] args) throws Exception {
		Thread.sleep(5000);
        Resource r1 = new Resource();
        Resource r0 = new Resource();

        Thread myTh1 = new LockThread1(r1, r0);
        Thread myTh0 = new LockThread0(r1, r0);

        myTh1.setName("DeadLock-1 ");
        myTh0.setName("DeadLock-0 ");

        myTh1.start();
        myTh0.start();
    }
}

    class Resource {
        private int i;
    
        public int getI() {
            return i;
        }
    
        public void setI(int i) {
            this.i = i;
        }
        
    }

    class LockThread1 extends Thread {
        private Resource r1, r2;
    
        public LockThread1(Resource r1, Resource r2) {
            this.r1 = r1;
            this.r2 = r2;
        }
    
        @Override
        public void run() {
            int j = 0;
            while (true) {
                synchronized (r1) {
                    System.out.println("The first thread got r1's lock " + j);
                    synchronized (r2) {
                        System.out.println("The first thread got r2's lock  " + j);
                    }
                }
                j++;
            }
        }
    
    }

    class LockThread0 extends Thread {
        private Resource r1, r2;
    
        public LockThread0(Resource r1, Resource r2) {
            this.r1 = r1;
            this.r2 = r2;
        }
    
        @Override
        public void run() {
            int j = 0;
            while (true) {
                synchronized (r2) {
                    System.out.println("The second thread got r2's lock  " + j);
                    synchronized (r1) {
                        System.out.println("The second thread got r1's lock" + j);
                    }
                }
                j++;
            }
        }
    
    }
