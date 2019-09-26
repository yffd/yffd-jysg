package com.yffd.jysg.java.v7.jvm.p1;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月19日 上午11:25:04 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class OutOfMemory_allocat_direct {

	public static void main(String[] args) {
		for (int i = 0; i < 1024 * 1024; i++) {
			System.out.println("num :" + i);
			ByteBuffer.allocateDirect(1024 * 1024 * 1024);
//			System.gc();
		}
	}

}

