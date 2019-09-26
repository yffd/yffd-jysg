package com.yffd.jysg.java.v7.jvm.p1;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.beans.BeanGenerator;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月18日 下午3:59:05 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class OutOfMemory_perm {

	// -Xms200m -Xmx200m -XX:+HeapDumpOnOutOfMemoryError -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=40m
	public static void main(String[] args) {
		Instant ins1 = Instant.now();
		for (int i = 0; i < 1024 * 1024; i++) {
			System.out.println("num :" + i);
			printBean();
			generateBean("kkkk_" + i, new HashMap());
		}
		Instant ins2 = Instant.now();
		System.out.println("time :" + Duration.between(ins1, ins2).getSeconds() + " 秒");
	}

	private static Object generateBean(String key, Map propertyMap) {
		BeanGenerator generator = new BeanGenerator();
		generator.addProperty(key, propertyMap.getClass());
		return generator.create();
	}
	
	public static void printBean() {
		//获取有关类型加载的JMX接口  
		ClassLoadingMXBean loadingBean = ManagementFactory.getClassLoadingMXBean();
		//显示数量信息（共加载过的类型数目，当前还有效的类型数目，已经被卸载的类型数目）  
		System.out.println("total   :" + loadingBean.getTotalLoadedClassCount());  
		System.out.println("active  :" + loadingBean.getLoadedClassCount());  
		System.out.println("unloaded:" + loadingBean.getUnloadedClassCount());
		System.out.println("************");
	}
	public static void printMemory() {
		System.out.println("free size  :" + Runtime.getRuntime().freeMemory() / (1024 *1024) + " M");
		System.out.println("max size   :" + Runtime.getRuntime().maxMemory() / (1024 *1024) + " M");
		System.out.println("total size :" + Runtime.getRuntime().totalMemory() / (1024 *1024) + " M");
		System.out.println("************");
	}
}

