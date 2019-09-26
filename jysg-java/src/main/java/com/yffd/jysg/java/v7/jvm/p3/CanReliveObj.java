package com.yffd.jysg.java.v7.jvm.p3;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月10日 下午2:57:17 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CanReliveObj {
	public static CanReliveObj obj;
	
	@Override
	protected void finalize() throws Throwable {
	    super.finalize();
	    System.out.println("CanReliveObj finalize called");
	    obj=this;
	}
	
	@Override
	public String toString(){
	    return "I am CanReliveObj";
	}

	public static void main(String[] args) throws InterruptedException {
		obj=new CanReliveObj();
		obj=null;   //可复活
		System.gc();
		Thread.sleep(1000);
		if(obj==null){
		   System.out.println("obj 是 null");
		}else{
		   System.out.println("obj 可用");
		}
		System.out.println("第二次gc");
		obj=null;    //不可复活
		System.gc();
		Thread.sleep(1000);
		if(obj==null){
			System.out.println("obj 是 null");
		}else{
			System.out.println("obj 可用");
		}
	}

}

