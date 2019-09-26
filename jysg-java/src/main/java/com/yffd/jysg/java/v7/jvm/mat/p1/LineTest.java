package com.yffd.jysg.java.v7.jvm.mat.p1;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月21日 下午2:07:07 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class LineTest {

	public static void main(String[] args) throws Exception {
		Point a = new Point(0, 0);
		Point b = new Point(1, 1);
		Point c = new Point(5, 3);
		Point d = new Point(9, 8);
		Point e = new Point(2, 1);
		Point f = new Point(9, 4);
		Point g = new Point(4, 7);
		
		Line aLine = new Line(a, b);
		Line bLine = new Line(c, d);
		Line cLine = new Line(e, f);
		Line dLine = new Line(f, g);
		
		a = null;
		b = null;
		c = null;
		d = null;
//		e = null;
//		f = null;
		g = null;
		
		Thread.sleep(10000000);

	}

}

