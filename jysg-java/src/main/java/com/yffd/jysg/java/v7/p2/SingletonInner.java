package com.yffd.jysg.java.v7.p2;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2019年1月7日 下午5:46:14 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class SingletonInner {
	
	private SingletonInner() {
		
	}
	
	public static SingletonInner getInstance() {
		return Inner.t;
	}
	
	private static class Inner {
		private static SingletonInner t = new SingletonInner();
	}
	
	public static void main(String[] args) {
		SingletonInner t1 = SingletonInner.getInstance();
		SingletonInner t2 = SingletonInner.getInstance();
		SingletonInner t3 = SingletonInner.getInstance();
		SingletonInner t4 = SingletonInner.getInstance();
		System.out.println(t1);
		System.out.println(t2);
		System.out.println(t3);
		System.out.println(t4);
	}

}

