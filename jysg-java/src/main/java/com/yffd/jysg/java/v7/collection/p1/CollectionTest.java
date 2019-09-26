package com.yffd.jysg.java.v7.collection.p1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月19日 上午11:27:50 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CollectionTest {

	public static void main(String[] args) {
		/**
		 * List接口
		 * 内部是：Object[]
		 * 每次扩容增加的数量为：原来数组长度的一半
		 */
		ArrayList<?> arrayList = new ArrayList<>();
		/**
		 * List接口
		 * 内部是：Object[]
		 * 所有的方法都是同步方法-synchronized
		 */
		Vector<?> vector = new Vector();
		/**
		 * List接口
		 * 内部是：Object[]
		 * 所有的方法都是同步方法-synchronized
		 * 继承Vector
		 */
		Stack<?> stack = new Stack();
		
		//******************************************************
		
		/**
		 * List接口
		 * 内部是：双端链表
		 */
		LinkedList<?> linkdList = new LinkedList<>();
		
		
		/**
		 * Set接口
		 * 内部是：HashMap.HashMap
		 * 元素作为HashMap的key，value为固定对象实例-Object
		 */
		HashSet<?> hashSet = new HashSet<>();
		/**
		 * Set接口
		 * 继承HashSet
		 * 内部是：HashMap
		 * 只提供了构造方法
		 */
		LinkedHashSet<?> linkedHashSet = new LinkedHashSet<>();
		/**
		 * Set接口
		 * 内部是：TreeMap.TreeMap
		 */
		TreeSet<?> treeSet = new TreeSet<>();
		
		//******************************************************
		
		/**
		 * Map接口
		 * 内部是：数组链表结构-链表为单项链表（尾），如果数组索引位置的链表格式大于8个，链表会转换成二叉树结构
		 * 		数组的长度是以2的倍数增长
		 * 	
		 * 		集合内元素总数量 / 数组长度 = 负载因子（0.75），此值大于0.75，将进行数组大小的扩容
		 */
		HashMap<?, ?> hashMap = new HashMap<>();
		hashMap.keySet().iterator();
		/**
		 * Map接口
		 * 继承HashMap
		 * 用一个双端链表维护顺序
		 */
		LinkedHashMap<?, ?> linkedHashMap = new LinkedHashMap<>();
		linkedHashMap.keySet().iterator();
		/**
		 * Map接口
		 * 
		 */
		TreeMap<?, ?> treeMap = new TreeMap<>();
		treeMap.keySet().iterator();
		/**
		 * Map接口
		 * 分段锁：锁只在数组上某个索引位置上的链表对象；
		 */
		ConcurrentHashMap<?, ?> concurrentHashMap = new ConcurrentHashMap<>();
		concurrentHashMap.keySet().iterator();
		
	}

	
}

