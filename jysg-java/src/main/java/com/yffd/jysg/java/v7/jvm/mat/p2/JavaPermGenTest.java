package com.yffd.jysg.java.v7.jvm.mat.p2;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月26日 下午3:09:52 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class JavaPermGenTest {

	private static List<Object> insList = new ArrayList<Object>();

    public static void main(String[] args) throws Exception {
    	Thread.sleep(5000);
        permLeak();
        
        System.in.read();
    }

    private static void permLeak() throws Exception {
        for (int i = 0; i < 10000; i++) {
        	Thread.sleep(2);
            URL[] urls = getURLS();
            URLClassLoader urlClassloader = new URLClassLoader(urls, null);
            Class<?> logfClass = Class.forName("org.apache.commons.logging.LogFactory", true, urlClassloader);
            Method getLog = logfClass.getMethod("getLog", String.class);
            Object result = getLog.invoke(logfClass, "PermGenTest");
            insList.add(result);
            System.out.println(i + ": " + result);
        }
    }

    private static URL[] getURLS() throws MalformedURLException {
        File libDir = new File("D:/java/dev/maven/repository/commons-logging/commons-logging/1.1.1");
        File[] subFiles = libDir.listFiles();
        int count = subFiles.length;
        URL[] urls = new URL[count];
        for (int i = 0; i < count; i++) {
            urls[i] = subFiles[i].toURI().toURL();
        }
        return urls;
    }

}

