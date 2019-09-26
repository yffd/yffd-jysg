package com.yffd.jysg.java.v8.p2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.yffd.jysg.java.v8.p1.Employee;
import org.junit.Test;

/*
 * 一、Stream API 的操作步骤：
 * 1. 创建 Stream
 * 		1）Collection 提供了两个方法  stream() 与 parallelStream()
 * 		2）通过 Arrays 中的 stream() 获取一个数组流
 * 		3）通过 Stream 类中静态方法 of()
 * 2. 中间操作
 * 3. 终止操作(终端操作)
 * 
 */
/*
	 筛选与切片
		filter——接收 Lambda ， 从流中排除某些元素。
		limit——截断流，使其元素不超过给定数量。
		skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
		distinct——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
		
	映射：map——接收 Lambda ， 将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
		allMatch——检查是否匹配所有元素
		anyMatch——检查是否至少匹配一个元素
		noneMatch——检查是否没有匹配的元素
		findFirst——返回第一个元素
		findAny——返回当前流中的任意元素
		count——返回流中元素的总个数
		max——返回流中最大值
		min——返回流中最小值
		
	归约
		reduce(T identity, BinaryOperator) / reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。
		collect——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
			汇总统计、分组、分区
*/
public class StreamAPITest {
	
	@Test
	public void test01() {
		List<String> list = new ArrayList<String>();
		Stream<String> stream = list.stream();//获取一个顺序流
		Stream<String> parallelStream = list.parallelStream();//获取一个并行流
		
		Integer[] nums = new Integer[10];
		Stream<Integer> stream1 = Arrays.stream(nums);//获取一个顺序流
		
		Stream<Integer> stream2 = Stream.of(1,2,3);
		
		Stream<Integer> stream3 = Stream.iterate(10, (x) -> x+2).limit(20);//创建一个无限流
		stream3.forEach(System.out::println);
		
		Stream<Double> stream4 = Stream.generate(Math::random).limit(10);//创建一个无限流
		stream4.forEach(System.out::println);
	}
	
	List<Employee> emps = Arrays.asList(
			new Employee(102, "李四", 59, 6666.66),
			new Employee(101, "张三", 18, 9999.99),
			new Employee(103, "王五", 28, 3333.33),
			new Employee(105, "田七", 38, 5555.55),
			new Employee(104, "赵六", 8, 7777.77),
			new Employee(104, "赵六", 8, 7777.77),
			new Employee(104, "赵六", 8, 7777.77)
	);
	
	@Test
	public void test02() {
		//外部迭代
		Iterator<Employee> it = emps.iterator();
		while(it.hasNext()) System.out.println(it.next());
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>");
		
		// 内部迭代
		Stream<Employee> stream = emps.stream()
				.filter((x) -> {
					System.out.println("过滤中。。。。");
					return x.getAge()<40;
					});
		stream.forEach(System.out::println);
	}
	
	@Test
	public void test03() {
		emps.stream().filter((x) -> { 
			System.out.println("短路！");
			return x.getAge()<40;
			}).limit(2)
		.forEach(System.out::println);
	}
	
	@Test
	public void test04() {
		emps.parallelStream()
		.filter((x) -> x.getSalary()>5000)
		.skip(2)
		.forEach(System.out::println);
	}
	
	@Test
	public void test05() {
		emps.stream().distinct().forEach(System.out::println);
	}
	
	@Test
	public void test06() {
		Integer[] nums = new Integer[]{1,2,3,4,5};
		Function<Integer, Integer> function = (x) -> x*x;
		for(Integer i : nums) {
			System.out.println(function.apply(i));
		}
		System.out.println(">>>>>>>>>>>>>>>>");
		Arrays.stream(nums).map((x) -> x*x).forEach(System.out::println);
	}
	
	@Test
	public void test07() {
		Optional<Integer> count = emps.stream().map((x) -> 1).reduce((x, y) -> x+y);
		count = emps.stream().map((x) -> 1).reduce(Integer::sum);
		System.out.println(count.get());
		
		emps.stream().map((e) -> e.getName()).forEach(System.out::println);
		
		List<String> strList = Arrays.asList("aa","bb","cc");
		strList.stream().map(String::toUpperCase).forEach(System.out::println);
		
		Stream<Stream<Character>> stream = strList.stream().map(StreamAPITest::filterCharater);
		stream.forEach((e) -> {
			e.forEach(System.out::println);
		});
		
		Stream<Character> stream2 = strList.stream().flatMap(StreamAPITest::filterCharater);
		stream2.forEach(System.out::println);
	}
	
	public static Stream<Character> filterCharater(String str) {
		List<Character> list = new ArrayList<>();
		for(Character c : str.toCharArray()) {
			list.add(c);
		}
		return list.stream();
	}
	
	@Test
	public void test08() {
		// 自然排序
		List<String> strList = Arrays.asList("a4", "a5", "a9", "a6", "a1", "a4", "a3");
		strList.stream().sorted().forEach(System.out::print);
		
		System.out.println();
		// 定制排序
		strList.stream().sorted((x, y) -> -x.compareTo(y)).forEach(System.out::print);
	}
	
	@Test
	public void test09() {
		boolean b1 = emps.stream().allMatch((e) -> e.getName().equals("赵六"));
		System.out.println(b1);
		boolean b2 = emps.stream().anyMatch((e) -> e.getName().equals("赵六"));
		System.out.println(b2);
		boolean b3 = emps.stream().noneMatch((e) -> e.getName().equals("赵六s"));
		System.out.println(b3);
		
		Optional<Employee> result = emps.stream().sorted((x, y) -> Double.compare(x.getSalary(), y.getSalary())).findFirst();
		System.out.println(result.get());
		System.out.println("...............");
		System.out.println(emps.parallelStream().filter((x) -> x.getSalary() < 5000).findAny().get());
		System.out.println(emps.parallelStream().filter((x) -> x.getSalary() > 5000).count());
		
		System.out.println(emps.stream().map((e) -> e.getSalary()).max(Double::compare).get());
		System.out.println(emps.stream().min((x, y) -> Double.compare(x.getSalary(), y.getSalary())).get());
	}
	
	@Test
	public void test10() {
		Stream<Employee> stream = emps.stream()
				 .filter((e) -> e.getName().equals("赵六"));
		long count = stream.count();//流进行了终止操作后，不能再次使用
		
		stream.map(Employee::getSalary).max(Double::compare);
	}
	
	@Test
	public void test11() {
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		Integer r1 = list.stream().reduce(0, (x, y) -> x+y);
		System.out.println(r1);
		
		Optional<Double> r2 = emps.stream().map(Employee::getSalary).reduce(Double::sum);
		System.out.println(r2.get());
	}
	
	@Test
	public void test12() {
		Integer num = emps.stream().map(Employee::getName)
		.flatMap(StreamAPITest::filterCharater)
		.map((x) -> {
			if(x.equals('六')) return 1;
			else return 0;
		})
		.reduce(0, Integer::sum);
		System.out.println(num);
	}
	
	@Test
	public void test13() {
		List<String> list = emps.stream().map(Employee::getName)
				.collect(Collectors.toList());
		list.forEach(System.out::println);
		System.out.println("---------------------------");
		Set<String> set = emps.stream().map(Employee::getName)
				.collect(Collectors.toSet());
		set.forEach(System.out::println);
		System.out.println("---------------------------");
		HashSet<String> hashSet = emps.stream().map(Employee::getName)
				.collect(Collectors.toCollection(HashSet::new));
		hashSet.forEach(System.out::println);
		System.out.println("---------------------------");
	}
	
	@Test
	public void test14() {
		Optional<Double> max = emps.stream().map(Employee::getSalary).collect(Collectors.maxBy(Double::compareTo));
		System.out.println(max.get());
		
		Double sum = emps.stream().collect(Collectors.summingDouble(Employee::getSalary));
		System.out.println(sum);
		
		Double avg = emps.stream().collect(Collectors.averagingDouble(Employee::getSalary));
		System.out.println(avg);
		
		Long count = emps.stream().collect(Collectors.counting());
		System.out.println(count);
		
		DoubleSummaryStatistics dss = emps.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
		System.out.println(dss.getMax());
		System.out.println(dss.getAverage());
		System.out.println(dss.getSum());
		System.out.println(dss.getCount());
	}
	
	@Test
	public void test15() {
		// 分组
		Map<String, List<Employee>> groups = emps.stream().collect(Collectors.groupingBy(Employee::getName));
		groups.forEach((x, y) -> {
			System.out.println(x);
			System.out.println(y);
		});
		
		System.out.println("------------------");
		// 多级分组
		Map<String, Map<String, List<Employee>>> groups2 = emps.stream()
				.collect(Collectors.groupingBy(Employee::getName, Collectors.groupingBy((e) -> {
					if(e.getAge() >= 60)
						return "老年";
					else if(e.getAge() >= 35)
						return "中年";
					else
						return "成年";
				})));
		groups2.forEach((x, y) -> {
			System.out.println(x);
			System.out.println(y);
		});
		
		System.out.println("------------------");
		// 分区
		Map<Boolean, List<Employee>> parties = emps.stream()
				.collect(Collectors.partitioningBy((e) -> e.getSalary() >= 5000));
		parties.forEach((x, y) -> {
			System.out.println(x);
			System.out.println(y);
		});
		
		System.out.println("------------------");
		// 连接
		String str = emps.stream()
				.map(Employee::getName)
				.collect(Collectors.joining("-" , "$$$", "###"));
		System.out.println(str);
		
		
		Optional<Double> sum = emps.stream()
				.map(Employee::getSalary)
				.collect(Collectors.reducing(Double::sum));
		System.out.println(sum.get());
	}
}

