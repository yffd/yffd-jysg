package com.yffd.jysg.java.v8.p1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.junit.Test;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月3日 下午2:02:16 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class Java8Test {
	List<Employee> emps = Arrays.asList(
			new Employee(101, "张三", 18, 9999.99),
			new Employee(102, "李四", 59, 6666.66),
			new Employee(103, "王五", 28, 3333.33),
			new Employee(104, "赵六", 8, 7777.77),
			new Employee(105, "田七", 38, 5555.55)
	);
	
	@Test
	public void test_3() {
		emps.stream().filter(e -> e.getAge()<34).forEach(System.out::println);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>");
		
		emps.stream().map(e -> e.getName()).forEach(System.out::println);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>");
		
		emps.stream().map(Employee::getName)
			.limit(3)
			.sorted()
			.forEach(System.out::println);
	}
	
	@Test
	public void test_2() {
		List<Employee> list = filterEmployee(emps, e -> e.getAge()>18);
		list.forEach(System.out::println);
		
		System.out.println(">>>>>>>>>>>>>>>>");
		
		List<Employee> list2 = filterEmployee(list, (e)-> e.getName().equals("李四"));
		list2.forEach(System.out::println);
	}
	
	// 策略设计模式
	public List<Employee> filterEmployee(List<Employee> emps, MyPredicate<Employee> mp){
		List<Employee> list = new ArrayList<>();
		for (Employee employee : emps) {
			if(mp.test(employee)){
				list.add(employee);
			}
		}
		return list;
	}
		
	@Test
	public void test_1() {
		Comparator<String> comparator = (x, y) -> Integer.compare(x.length(), y.length());
		TreeSet<String> treeSet = new TreeSet<>(comparator);
		treeSet.add("11");
		treeSet.add("2");
		treeSet.add("333");
		treeSet.add("444444");
		treeSet.add("55");
		treeSet.add("6");
		System.out.println(treeSet);
	}
}

