package com.yffd.jysg.java.v8.p6;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月4日 下午3:36:24 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class DateFormatThreadLocal {
	
	private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>(){
		
		protected DateFormat initialValue(){
			return new SimpleDateFormat("yyyyMMdd");
		}
		
	};
	
	public static final Date convert(String source) throws ParseException{
		return df.get().parse(source);
	}
}

