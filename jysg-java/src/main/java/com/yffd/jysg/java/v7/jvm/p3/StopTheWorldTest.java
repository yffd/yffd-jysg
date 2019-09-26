package com.yffd.jysg.java.v7.jvm.p3;

import java.util.HashMap;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月10日 下午3:03:46 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class StopTheWorldTest {
	// -Xmx512M -Xms512M -XX:+UseSerialGC -Xloggc:gc.log -XX:+PrintGCDetails -Xmn1m -XX:PretenureSizeThreshold=50 -XX:MaxTenuringThreshold=1
	public static void main(String[] args) {
		new PrintThread().start();
		new MyThread().start();
	}
	
	public static class MyThread extends Thread {
		HashMap<Long,byte[]> map=new HashMap<Long,byte[]>();
		@Override
		public void run(){
			try{
				while(true){
					if(map.size()*512/1024/1024>=450){
						System.out.println("=====准备清理=====:"+map.size());
						map.clear();
					}
					
					for(int i=0;i<1024;i++){
						map.put(System.nanoTime(), new byte[512]);
					}
					Thread.sleep(1);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public static class PrintThread extends Thread {
		public static final long starttime=System.currentTimeMillis();
		@Override
		public void run(){
			try{
				while(true){
					long t=System.currentTimeMillis()-starttime;
					System.out.println("time:"+t);
					Thread.sleep(100);
				}
			}catch(Exception e){
				
			}
		}
	}

}

