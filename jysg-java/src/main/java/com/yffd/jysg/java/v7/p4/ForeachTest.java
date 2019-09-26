package com.yffd.jysg.java.v7.p4;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月20日 下午2:53:38 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class ForeachTest {
	private static final String FLAG_SYMBOL = "*";
	private static final String FLAG_BLANK = " ";
	
	public static void main(String[] args) {
//		printA(5);
//		printB(5);
//		printC(5);
//		printD(5);
//		printE(5);
//		printF(5);
//		printG(5);
		printH(5);

	}


	/*
		    *    
		   * *   
		  *   *  
		 *     * 
		*********
	*/
	public static void printH(int lay) {
		for (int i = 0; i < lay; i++) {
			if (i == (lay-1)) {
				for (int j = 1; j <= (lay * 2 - 1); j++) {
					System.out.print(FLAG_SYMBOL);
				}
				break;
			}
			for (int j = 1; j <= (lay * 2 - 1); j++) {
				if (j == (lay - i) || j == (lay + i))
					System.out.print(FLAG_SYMBOL);
				else 
					System.out.print(FLAG_BLANK);
			}
			System.out.println();
		}
	}

	/*
		*********
		 *******
		  *****
		   ***
		    *
	*/
	public static void printG(int lay) {
		for (int i = 1; i <= lay; i++) {
			for (int j = 1; j < i; j++) {
				System.out.print(FLAG_BLANK);
			}
			for (int j = 0; j < (lay * 2 - 1 - (i-1) * 2); j++) {
				System.out.print(FLAG_SYMBOL);
			}
			System.out.println();
		}
	}
	
	/*
		    *
		   ***
		  *****
		 *******
		*********
	*/
	public static void printF(int lay) {
		for (int i = 1; i <= lay; i++) {
			for (int j = 1; j <= (lay - i); j++) {
				System.out.print(FLAG_BLANK);
			}
			for (int j = 0; j < (i * 2 - 1); j++) {
				System.out.print(FLAG_SYMBOL);
			}
			System.out.println();
		}
	}
	/*
		*****
		 ****
		  ***
		   **
		    *
	*/
	public static void printE(int lay) {
		for (int i = 0; i < lay; i++) {
			for (int j = 0; j < i; j++) {
				System.out.print(FLAG_BLANK);
			}
			for (int j = 0; j < (lay - i); j++) {
				System.out.print(FLAG_SYMBOL);
			}
			System.out.println();
		}
	}
	/*
		    *
		   **
		  ***
		 ****
		*****
	*/
	public static void printD(int lay) {
		for (int i = 0; i < lay; i++) {
			for (int j = 0; j < (lay - i -1); j++) {
				System.out.print(FLAG_BLANK);
			}
			for (int j = 0; j <= i; j++) {
				System.out.print(FLAG_SYMBOL);
			}
			System.out.println();
		}
	}
	/*
		*
		**
		***
		****
		*****
	*/
	public static void printC(int lay) {
		for (int i = 0; i < lay; i++) {
			for (int j = 0; j <= i; j++) {
				System.out.print(FLAG_SYMBOL);
			}
			System.out.println();
		}
	}
	/*
		*****
		****
		***
		**
		*
	*/
	public static void printB(int lay) {
		for (int i = 0; i < lay; i++) {
			for (int j = 0; j < (lay - i); j++) {
				System.out.print(FLAG_SYMBOL);
			}
			System.out.println();
		}
	}
	/*
	 			*	  **	***	  ****
	 |----| |---*| |--**| |-***| |****|
	*/
	public static void printA(int lay) {
		for (int i = 0; i < lay; i++) {
			for (int j = 1; j <= (lay - i); j++) {
				System.out.print("-");
			}
			for (int j = 1; j <= i; j++) {
				System.out.print(FLAG_SYMBOL);
			}
			System.out.print(" ");
		}
	}
}

