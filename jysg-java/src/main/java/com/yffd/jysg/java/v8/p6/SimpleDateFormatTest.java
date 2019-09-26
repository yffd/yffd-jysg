package com.yffd.jysg.java.v8.p6;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月4日 下午3:37:05 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class SimpleDateFormatTest {

	@Test
	public void test01() throws Exception {
		// 线程不安全
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
		
		ExecutorService pool = Executors.newFixedThreadPool(10);
		
		Callable<Date> callable = new Callable<Date>() {
			Random r = new Random();
			@Override
			public Date call() throws Exception {
				int num = r.nextInt(30);
				while(num<10) num = r.nextInt(30);
				System.out.println("2018-12-" + num);
				return sdf.parse("2018-12-" + num);
			}
		};
		
		List<Future<Date>> results = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Future<Date> future = pool.submit(callable);
			results.add(future);
		}
		
		for (Future<Date> future : results) {
			System.out.println(future.get());
		}
		
		pool.shutdown();
	}
	
	@Test
	public void test02() throws Exception {
		ExecutorService pool = Executors.newFixedThreadPool(10);
		
		Callable<Date> task = new Callable<Date>() {
			Random r = new Random();
			@Override
			public Date call() throws Exception {
				int num = r.nextInt(30);
				while(num<10) num = r.nextInt(30);
				System.out.println("2018-12-" + num);
				return DateFormatThreadLocal.convert("2018-12-" + num);
			}
		};
		
		List<Future<Date>> results = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			results.add(pool.submit(task));
		}
		
		for (Future<Date> future : results) {
			System.out.println(future.get());
		}
		
		pool.shutdown();
	}
	
	@Test
	public void test03() throws Exception {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		Callable<LocalDate> task = new Callable<LocalDate>() {

			@Override
			public LocalDate call() throws Exception {
				LocalDate ld = LocalDate.parse("20161121", dtf);
				return ld;
			}
			
		};

		ExecutorService pool = Executors.newFixedThreadPool(10);
		
		List<Future<LocalDate>> results = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			results.add(pool.submit(task));
		}
		
		for (Future<LocalDate> future : results) {
			System.out.println(future.get());
		}
		
		pool.shutdown();
	}
}

