package com.yffd.jysg.java.v8.p2;

import com.yffd.jysg.java.v8.p1.Employee;
import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


/*
 * 一、方法引用：若 Lambda 体中的功能，已经有方法提供了实现，可以使用方法引用
 * 			  （可以将方法引用理解为 Lambda 表达式的另外一种表现形式）
 * 
 * 1. 对象的引用 :: 实例方法名
 * 
 * 2. 类名 :: 静态方法名
 * 
 * 3. 类名 :: 实例方法名
 * 
 * 注意：
 * 	 ①方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
 * 	 ②若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
 * 
 * 二、构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
 * 
 * 1. 类名 :: new
 * 
 * 三、数组引用
 * 
 * 	类型[] :: new;
 * 
 * 
 */
public class MethodRefTest {

	@Test
	public void test01() throws Exception {
		PrintStream ps = System.out;
		Consumer<String> consumer = (x) -> ps.println("*****" + x);
		consumer.accept("abc");
		
		System.out.println(">>>>>>>>>>>>>>>>");
		
		Consumer<String> consumer2 = ps::println;
		consumer2.accept("xiaohei");
	}
	
	@Test
	public void test02() {
		Employee emp = new Employee(101, "张三", 18, 9999.99);
		Supplier<String> supplier = () -> emp.getName();
		String result1 = supplier.get();
		System.out.println(result1);
		System.out.println(">>>>>>>>>>>>>>>>>>");
		
		Supplier<String> supplier2 = emp::getName;
		String result2 = supplier2.get();
		System.out.println(result2);
	}
	
	@Test
	public void test03() {
		BiFunction<Integer, Integer, Integer> bifunction = (x, y) -> Math.max(x, y);
		Integer result1 = bifunction.apply(20, 30);
		System.out.println(result1);
		
		System.out.println(">>>>>>>>>>>>>>>>>>");
		
		BiFunction<Integer, Integer, Integer> bifunction2 = Math::max;
		Integer result2 = bifunction2.apply(11, 32);
		System.out.println(result2);
	}
	
	@Test
	public void test04() {
		Comparator<Integer> comparator = (x, y) -> -Integer.compare(x, y);
		TreeSet<Integer> treeSet = new TreeSet<>(comparator);
		treeSet.add(11);
		treeSet.add(41);
		treeSet.add(33);
		treeSet.forEach(System.out::println);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>");
		
		Comparator<Integer> comparator2 = Integer::compare;
		TreeSet<Integer> treeSet2 = new TreeSet<>(comparator2);
		treeSet2.add(13);
		treeSet2.add(12);
		treeSet2.add(11);
		treeSet2.forEach(System.out::println);
	}
	
	@Test
	public void test05() {
		BiPredicate<String, String> bipredicate = (x, y) -> x.equals(y);
		boolean result1 = bipredicate.test("asdf", "asddf");
		System.out.println(result1);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		BiPredicate<String, String> bipredicate2 = String::equals;
		boolean result2 = bipredicate2.test("qwe", "qwe");
		System.out.println(result2);
		System.out.println(bipredicate.test("qq", "dd"));
	}
	
	@Test
	public void test06() {
		Supplier<Employee> supplier = () -> new Employee();
		Employee result1 = supplier.get();
		System.out.println(result1);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>");
		
		Supplier<Employee> supplier2 = Employee::new;
		Employee result2 = supplier2.get();
		System.out.println(result2);
	}
	
	@Test
	public void test07() {
		Function<String, Employee> function = Employee::new;
		Employee result1 = function.apply("小黑");
		System.out.println(result1);
		
		BiFunction<String, Integer, Employee> bifunction = Employee::new;
		System.out.println(bifunction.apply("小牛", 13));
	}
	
	@Test
	public void test08() {
		Function<Integer, String[]> function = (x) -> new String[x];
		String[] result1 = function.apply(3);
		System.out.println(result1.length);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>");
		
		Function<Integer, Employee[]> function2 = Employee[]::new;
		Employee[] result2 = function2.apply(5);
		System.out.println(result2);
		System.out.println(result2.length);
	}
}

