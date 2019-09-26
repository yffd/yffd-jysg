package com.yffd.jysg.java.v7.jvm.mat.p2;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月26日 下午2:27:21 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class JavaHeapTest {

	public final static int OUTOFMEMORY = 200000000;
    
    private String oom;

    private int length;
    
    StringBuffer tempOOM = new StringBuffer();

    public JavaHeapTest(int leng) throws Exception {
        this.length = leng;
       
        int i = 0;
        while (i < leng) {
            i++;
            try {
                tempOOM.append("a");
            } catch (OutOfMemoryError e) {
               e.printStackTrace();
               break;
            }
            if(i%20000000 == 0) {
            	Thread.sleep(100);
            	System.out.println("ddd");
            }
            System.out.println("sss=" + i);
        }
        this.oom = tempOOM.toString();

    }

    public String getOom() {
        return oom;
    }

    public int getLength() {
        return length;
    }

    public static void main(String[] args) throws Exception {
    	Thread.sleep(5000);
        JavaHeapTest javaHeapTest = new JavaHeapTest(OUTOFMEMORY);
        System.out.println(javaHeapTest.getOom().length());
        
        System.in.read();
        
    }

}

