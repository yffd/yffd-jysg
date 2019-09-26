package com.yffd.jysg.java.v7.p1;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月21日 下午4:05:43 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class Student extends Person {

	@Override
	public void method1() {
		System.out.println("method1....Student");
	}

	@Override
	public Student method3() {
		return null;
	}

	public static void method2() {
		System.out.println("method2.....Student");
	}
}

