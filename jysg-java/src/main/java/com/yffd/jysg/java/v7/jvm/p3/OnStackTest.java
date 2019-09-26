package com.yffd.jysg.java.v7.jvm.p3;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月4日 下午5:31:37 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class OnStackTest {

	public static void main(String[] args) {
		long b = System.currentTimeMillis();
        for(int i=0;i<100000000;i++){
            alloc();
        }
        long e = System.currentTimeMillis();
        System.out.println(e-b);
	}
	
	public static void alloc(){
        byte[] b = new byte[2];
        b[0]=1;
    }

}

