package com.yffd.jysg.java.v8.p6;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

import org.junit.Test;

/**
 * Instant : 时间戳。 （使用 Unix 元年  1970年1月1日 00:00:00 所经历的毫秒值）
 * Duration : 用于计算两个“时间”间隔
 * Period : 用于计算两个“日期”间隔	 
 * TemporalAdjuster : 时间校正器
 * DateTimeFormatter : 解析和格式化日期或时间
 * ZonedDate、ZonedTime、ZonedDateTime ： 带时区的时间或日期
 */
public class LocalDateTimeTest {

	@Test
	public void test01() {
		LocalDate ld = LocalDate.now();
		System.out.println(ld);
		
		LocalTime lt = LocalTime.now();
		System.out.println(lt);
		
		LocalDateTime ldt = LocalDateTime.now();
		System.out.println(ldt);
		System.out.println(ldt.MAX);
		System.out.println(ldt.MIN);
		
		LocalDateTime ldt3 = ldt.plusYears(20);
		System.out.println(ldt3);
		
		LocalDateTime ldt4 = ldt.minusMonths(2);
		System.out.println(ldt4);
		
		System.out.println(ldt.getYear());
		System.out.println(ldt.getMonthValue());
		System.out.println(ldt.getDayOfMonth());
		System.out.println(ldt.getHour());
		System.out.println(ldt.getMinute());
		System.out.println(ldt.getSecond());
	}
	
	@Test
	public void test02() {
		//Instant : 时间戳。 （使用 Unix 元年  1970年1月1日 00:00:00 所经历的毫秒值）
		Instant ins = Instant.now();
		System.out.println(ins);
		
		OffsetDateTime odt = ins.atOffset(ZoneOffset.ofHours(8));
		System.out.println(odt);
		
		System.out.println(ins.getNano());
		System.out.println(ins.getEpochSecond());
		
		Instant ins2 = Instant.ofEpochSecond(150);
		System.out.println(ins2);
	}
	
	@Test
	public void test03(){
		//Duration : 用于计算两个“时间”间隔
		//Period : 用于计算两个“日期”间隔
		Instant ins1 = Instant.now();
		System.out.println("--------------------");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		Instant ins2 = Instant.now();
		System.out.println("所耗费时间为：" + Duration.between(ins1, ins2).getSeconds());
		System.out.println("----------------------------------");
		
		LocalDate ld1 = LocalDate.now();
		LocalDate ld2 = LocalDate.of(2011, 1, 1);
		
		Period pe = Period.between(ld2, ld1);
		System.out.println(pe.getYears());
		System.out.println(pe.getMonths());
		System.out.println(pe.getDays());
	}
	
	@Test
	public void test4(){
		//TemporalAdjuster : 时间校正器
		LocalDateTime ldt = LocalDateTime.now();
		System.out.println(ldt);
		
		LocalDateTime ldt2 = ldt.withDayOfMonth(12);
		System.out.println(ldt2);
		
		LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
		System.out.println(ldt3);
		
		//自定义：下一个工作日
		LocalDateTime ldt5 = ldt.with((l) -> {
			LocalDateTime ldt4 = (LocalDateTime) l;
			
			DayOfWeek dow = ldt4.getDayOfWeek();
			
			if(dow.equals(DayOfWeek.FRIDAY)){
				return ldt4.plusDays(3);
			}else if(dow.equals(DayOfWeek.SATURDAY)){
				return ldt4.plusDays(2);
			}else{
				return ldt4.plusDays(1);
			}
		});
		
		System.out.println(ldt5);
	}
	
	@Test
	public void test05(){
		// DateTimeFormatter : 解析和格式化日期或时间
//			DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss E");
		
		LocalDateTime ldt = LocalDateTime.now();
		String strDate = ldt.format(dtf);
		
		System.out.println(strDate);
		
		LocalDateTime newLdt = ldt.parse(strDate, dtf);
		System.out.println(newLdt);
	}
	
	@Test
	public void test06(){
		Set<String> set = ZoneId.getAvailableZoneIds();
		set.forEach(System.out::println);
	}
	
	@Test
	public void test07(){
		//ZonedDate、ZonedTime、ZonedDateTime ： 带时区的时间或日期
		LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
		System.out.println(ldt);
		
		ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("US/Pacific"));
		System.out.println(zdt);
	}
}

