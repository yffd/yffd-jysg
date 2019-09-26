package com.yffd.jysg.java.v7.p1;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月29日 下午1:53:48 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class TestBean {
	
	static {
		System.out.println("...static...");
	}
	
	{
		System.out.println("...no static...");
	}

	public TestBean() {
		System.out.println("...constructor...");
	}
	
	
}

