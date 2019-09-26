package com.yffd.jysg.java.v7.jvm.p4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月14日 下午5:56:23 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class OutOfMemoryTest {

	public static void main(String[] args) {
		heapOOM();
//		permOOM();
	}
	
	public static void permOOM() {
		for (int i = 0; i < 1024*1024; i++) {
			CglibBean bean = new CglibBean(new HashMap());
//			System.out.println(bean);
		}
	}
	
	public static void heapOOM() {
		List<byte[]> list = new ArrayList<byte[]>();
		for (int i = 0; i < 1024*1024; i++) {
			list.add(new byte[1024*1024]);
		}
	}

}

