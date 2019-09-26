package com.yffd.jysg.java.v8.p2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;

/*
 * 一、Lambda 表达式的基础语法：Java8中引入了一个新的操作符 "->" 该操作符称为箭头操作符或 Lambda 操作符
 * 						    箭头操作符将 Lambda 表达式拆分成两部分：
 * 
 * 左侧：Lambda 表达式的参数列表
 * 右侧：Lambda 表达式中所需执行的功能， 即 Lambda 体
 * 
 * 语法格式一：无参数，无返回值
 * 		() -> System.out.println("Hello Lambda!");
 * 
 * 语法格式二：有一个参数，并且无返回值
 * 		(x) -> System.out.println(x)
 * 
 * 语法格式三：若只有一个参数，小括号可以省略不写
 * 		x -> System.out.println(x)
 * 
 * 语法格式四：有两个以上的参数，有返回值，并且 Lambda 体中有多条语句
 *		Comparator<Integer> com = (x, y) -> {
 *			System.out.println("函数式接口");
 *			return Integer.compare(x, y);
 *		};
 *
 * 语法格式五：若 Lambda 体中只有一条语句， return 和 大括号都可以省略不写
 * 		Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
 * 
 * 语法格式六：Lambda 表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断”
 * 		(Integer x, Integer y) -> Integer.compare(x, y);
 * 
 * 上联：左右遇一括号省
 * 下联：左侧推断类型省
 * 横批：能省则省
 * 
 * 二、Lambda 表达式需要“函数式接口”的支持
 * 函数式接口：接口中只有一个抽象方法的接口，称为函数式接口。 可以使用注解 @FunctionalInterface 修饰
 * 			 可以检查是否是函数式接口
 * 
 * 三、Java8 内置的四大核心函数式接口
 * 
 * Consumer<T> : 消费型接口
 * 		void accept(T t);
 * 
 * Supplier<T> : 供给型接口
 * 		T get(); 
 * 
 * Function<T, R> : 函数型接口
 * 		R apply(T t);
 * 
 * Predicate<T> : 断言型接口
 * 		boolean test(T t);
 */
public class LambdaTest {

	@Test
	public void test() {
		int num = 0;
		System.out.println(Thread.currentThread().getName());
		Runnable r1 = new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + ">>" + num);
			}
		};
		
		new Thread(r1).start();
		
		System.out.println(">>>>>>>>>>>>>>>>>>>");
		
		Runnable r2 = () -> {System.out.println(Thread.currentThread().getName());};
		new Thread(r2).start();
		
	}
	
	@Test
	public void consumerTest() {
		Consumer<String> consumer = (t) -> System.out.println("size:" + t.length());
		consumer.accept("12345");
		
		this.consumerMethod(200D, (x) -> System.out.println("吃个饭，花了"+ x +"块大洋，太特么贵了。。。"));
	}
	private void consumerMethod(Double money, Consumer<Double> consumer) {
		consumer.accept(money);
	}
	
	@Test
	public void supplierTest() {
		Supplier<Date> supplier = () -> new Date(); 
		System.out.println(supplier.get());
		
		System.out.println(">>>>>>>>>>>>>");
		
		List<Integer> list = this.supplierMethod(3, () -> (int)(Math.random()*100));
		list.forEach(System.out::println);
	}
	
	private List<Integer> supplierMethod(int count, Supplier<Integer> supplier) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			list.add(supplier.get());
		}
		return list;
	}
	
	@Test
	public void functionTest() {
		Function<Integer, String> function = (x) -> x + "-";
		String result = function.apply(10);
		System.out.println(result);
		
		System.out.println(">>>>>>>>>>>>>>");
		
		String tmp = this.functionMethod(10, (x) -> "小黑花费 " + x + " 块大洋");
		System.out.println(tmp);
	}
	
	private String functionMethod(int money, Function<Integer, String> function) {
		System.out.println("逻辑前。。。。。。。。。。。");
		String ret = function.apply(money);
		System.out.println("逻辑后。。。。。。。。。。。");
		return ret;
	}
	
	@Test
	public void predicateTest() {
		Predicate<String> predicate = (t) -> "xiaoniu".equals(t);
		boolean result = predicate.test("xiaoniu");
		System.out.println(result);
		System.out.println(predicate.test("bainiu"));
	}
}

