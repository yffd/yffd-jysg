package com.yffd.jysg.java.v7.collection.p2;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月20日 上午11:22:45 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class Test {

	public static void main(String[] args) {
//		testHashMap();
//		testTreeMap();
//		test(13);
	}

	private static void testHashMap() {
		HashMap<Persion, Integer> hashMap = new HashMap<>();
		{
			Persion p = new Persion("zhangsan", 4);
			hashMap.put(p, 1);
		}
		{
			Persion p = new Persion("zhangsan", 3);
			hashMap.put(p, 1);
		}
		{
			Persion p = new Persion("zhangsan", 1);
			p.setVersion(10);
			hashMap.put(p, 1);
		}
		{
			Persion p = new Persion("zhangsan", 1);
			p.setVersion(11);
			hashMap.put(p, 1);
		}
		Set<Persion> keys = hashMap.keySet();
		for(Persion p : keys) {
			System.out.println(p);
		}
		
	}

	private static void testTreeMap() {
		TreeMap<Persion, Integer> treeMap = new TreeMap<>();
		{
			Persion p = new Persion("zhangsan", 4);
			treeMap.put(p, 1);
		}
		{
			Persion p = new Persion("zhangsan", 3);
			treeMap.put(p, 1);
		}
		{
			Persion p = new Persion("zhangsan", 1);
			treeMap.put(p, 1);
		}
		{
			Persion p = new Persion("zhangsan", 2);
			treeMap.put(p, 1);
		}
		Set<Persion> keys = treeMap.keySet();
		for(Persion p : keys) {
			System.out.println(p);
		}
		
//		System.out.println(treeMap.size());
//		System.out.println(treeMap.pollFirstEntry());
//		System.out.println(treeMap.size());
//		System.out.println(treeMap.pollFirstEntry());
//		System.out.println(treeMap.size());
	}

	public static int test(int cap) {
		int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n + 1;
	}
}

