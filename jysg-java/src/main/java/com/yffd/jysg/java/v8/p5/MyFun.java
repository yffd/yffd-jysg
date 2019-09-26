package com.yffd.jysg.java.v8.p5;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月4日 下午3:26:52 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public interface MyFun {
	
	default String getName(){
		return "接口中的默认方法-MyFun";
	}
	
	public static void show(){
		System.out.println("接口中的静态方法-MyFun");
	}
	
}

