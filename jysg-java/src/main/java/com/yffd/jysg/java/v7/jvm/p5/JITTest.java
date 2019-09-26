package com.yffd.jysg.java.v7.jvm.p5;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月10日 下午6:10:10 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class JITTest {
	
	// -XX:CompileThreshold=1000 -XX:+PrintCompilation
	public static void met(){
        int a=0,b=0;
        b=a+b;
    }
    
    public static void main(String[] args) {
        for(int i=0;i<1000;i++){
            met();
        }
    }

}

