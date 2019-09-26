package com.yffd.jysg.java.v8.p4;

import java.util.Optional;

import com.yffd.jysg.java.v8.p1.Employee;
import org.junit.Test;

/*
 * 一、Optional 容器类：用于尽量避免空指针异常
 * 	Optional.of(T t) : 创建一个 Optional 实例
 * 	Optional.empty() : 创建一个空的 Optional 实例
 * 	Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例
 * 	isPresent() : 判断是否包含值
 * 	orElse(T t) :  如果调用对象包含值，返回该值，否则返回t
 * 	orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值
 * 	map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()
 * 	flatMap(Function mapper):与 map 类似，要求返回值必须是Optional
 */
public class OptionalTest {

	@Test
	public void test01() {
		Optional<Employee> op = Optional.of(new Employee());
		Employee emp = op.get();
		System.out.println(emp);
		
		Optional<Employee> op1 = Optional.ofNullable(null);
		System.out.println(op1.get());
		
//		Optional<Employee> op2 = Optional.empty();
//		System.out.println(op2.get());
	}
	
	@Test
	public void test02() {
		Optional<Employee> op = Optional.ofNullable(new Employee());
		op = Optional.ofNullable(null);
		if(op.isPresent()) System.out.println(op);
		System.out.println("----------------------");
		
		Employee emp = op.orElse(new Employee("zhangshan"));
		System.out.println(emp);
		System.out.println("----------------------");
		
		Employee emp2 = op.orElseGet(() -> new Employee("lisi"));
		System.out.println(emp2);
		System.out.println("----------------------");
		
		Optional<Employee> op3 = Optional.of(new Employee(101, "张三", 18, 9999.99));
//		op3 = Optional.ofNullable(null);
		Optional<String> op4 = op3.map(Employee::getName);
		System.out.println(op4.get());
		
		Optional<String> op5 = op3.flatMap((e) -> Optional.of(e.getName()));
		System.out.println(op5.get());
	}
	
	@Test
	public void test03() {
		Optional<Godness> godness = Optional.ofNullable(new Godness("貂蝉"));
		
		Optional<NewMan> op = Optional.ofNullable(new NewMan(godness));
		String name = getGodnessName2(op);
		System.out.println(name);
	}
	
	public String getGodnessName2(Optional<NewMan> man){
		return man.orElse(new NewMan())
				  .getGodness()
				  .orElse(new Godness("西施"))
				  .getName();
	}
}

