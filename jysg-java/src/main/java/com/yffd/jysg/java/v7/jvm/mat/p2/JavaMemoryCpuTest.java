package com.yffd.jysg.java.v7.jvm.mat.p2;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月26日 下午3:51:03 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class JavaMemoryCpuTest {
	
	public static void main(String[] args) throws Exception {
		Thread.sleep(5000);
        cpuFix();
        
        System.in.read();
    }


    /**
     * cpu 运行固定百分比
     * 
     * @throws InterruptedException
     */
    public static void cpuFix() throws InterruptedException {
        // 80%的占有率
        int busyTime = 8;
        // 20%的占有率
        int idelTime = 2;
        // 开始时间
        long startTime = 0;
        
        while (true) {
            // 开始时间
            startTime = System.currentTimeMillis();
            
            /*
             * 运行时间
             */
            while (System.currentTimeMillis() - startTime < busyTime) {
                ;
            }
            
            // 休息时间
            Thread.sleep(idelTime);
        }
    }
}

