package com.yffd.jysg.java.v7.p3;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月22日 下午5:19:26 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class IntegerTest {

	public static void main(String[] args) {
		Integer i1 = 127;
		Integer i2 = 127;
		System.out.println(i1 == i2);
		System.out.println(i1.equals(i2));
		System.out.println("-------------------------");
		
		Integer i3 = 128;
		Integer i4 = 128;
		System.out.println(i3 == i4);
		System.out.println(i3.equals(i4));
	}

}

