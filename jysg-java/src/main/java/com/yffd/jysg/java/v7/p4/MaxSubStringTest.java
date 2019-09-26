package com.yffd.jysg.java.v7.p4;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月21日 上午9:21:12 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class MaxSubStringTest {

	public static void main(String[] args) {
		String s1 = "asdfrw1k2y3cqwerovkfg83klf35";
		String s2 = "q12q4wheurdw23";
		String ret = getMaxSub(s1, s2);
		System.out.println(ret);

	}
	
	public static String getMaxSub(String s1, String s2) {
		String max = s1.length() >= s2.length() ? s1 : s2;
		String min = s1.length() < s2.length() ? s1 : s2;
		
		for (int i = 0; i< min.length(); i++) {
			for (int s = 0, e = (min.length() - i); e <= min.length(); s++, e++) {
				String sub = min.substring(s, e);
				if (max.contains(sub)) return sub;
			}
		}
		return null;
	}

}

