package com.yffd.jysg.java.v8.p5;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月4日 下午3:29:12 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class InterfaceDefaultMethodTest {
	
	public static void main(String[] args) {
		SubClass sc = new SubClass();
		System.out.println(sc.getName());
		
		MyInterface.show();
		MyFun.show();
	}
}

