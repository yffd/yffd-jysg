package com.yffd.jysg.java.v7.p1;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月21日 下午4:00:03 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class MyTest {

	public static void main(String[] args) throws Exception {
		
		Student s = new Student();
		System.out.println(s.FLAG);
		System.out.println(s.name);
		s.method1();
		s.method2();
		
//		Integer i1 = 123;
//		i1.compareTo(null);
		
		Class.forName("com.yffd.demo.java.test.TestBean");
		Class.forName("com.yffd.demo.java.test.TestBean");
		ClassLoader loader = MyTest.class.getClassLoader();
		Class.forName("com.yffd.demo.java.test.TestBean", true, loader);
		Class.forName("com.yffd.demo.java.test.TestBean", true, loader);
	}

}

