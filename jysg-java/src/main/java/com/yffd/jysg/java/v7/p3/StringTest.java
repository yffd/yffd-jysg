package com.yffd.jysg.java.v7.p3;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月22日 下午4:54:56 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class StringTest {

	public static void main(String[] args) {
		String s1 = "abc";
		String s2 = "abc";
		System.out.println(s1 == s2);
		System.out.println(s1.equals(s2));
		System.out.println("-------------------------");
		
		String s3 = new String("abc");
		String s4 = new String("abc");
		System.out.println(s3 == s4);
		System.out.println(s3.equals(s4));
		System.out.println(s3.hashCode());
		System.out.println(s4.hashCode());
		System.out.println("-------------------------");
		
		System.out.println(s1 == s3);
		System.out.println(s1.equals(s3));
		System.out.println("-------------------------");
		
		String ss1 = s3.intern();
		String ss2 = s4.intern();
		System.out.println(ss1 == ss2);
		System.out.println(s1 == ss1);
		System.out.println(s1 == ss2);
		System.out.println(s3 == ss2);
	}

}

