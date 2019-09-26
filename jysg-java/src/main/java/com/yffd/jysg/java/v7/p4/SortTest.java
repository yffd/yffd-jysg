package com.yffd.jysg.java.v7.p4;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月21日 上午10:22:03 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class SortTest {

	public static void main(String[] args) {
		int[] arr = {3, 4, 1, 9, 5, 8, 28, 4, 56, 81, 45, 6, 78};
		printArr(arr);
		// 快速排序
//		fastSortA(arr);
//		fastSortB(arr);
		
		// 冒泡排序
//		bubbleSortA(arr);
		bubbleSortB(arr);
		printArr(arr);
		
		
	}
	
	/**
	 * 冒泡排序：
	 * 缺点：交换次数过多
	 */
	public static void bubbleSortA(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 0; j < arr.length - i - 1; j++) {
				if (arr[j] > arr[j + 1]) {
					int tmp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = tmp;
				}
			}
		}
	}
	/**
	 * 冒泡排序：
	 * 通过记录最小索引位置，减少交换次
	 */
	public static void bubbleSortB(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			int idx = 0;
			int num = arr[0];
			int end = arr.length - i - 1;
			for (int j = 1; j <= end; j++) {
				if (arr[j] > num) {
					idx = j;
					num = arr[j];
				}
			}
			if (i != idx) {
				int tmp = arr[idx];
				arr[idx] = arr[end];
				arr[end] = tmp;
			}
		}
		
	}

	/**
	 * 快速排序：
	 * 缺点：交换次数过多
	 */
	public static void fastSortA(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i] > arr[j]) {
					int tmp = arr[i];
					arr[i] = arr[j];
					arr[j] = tmp;
				}
			}
		}
	}
	/**
	 * 快速排序：
	 * 通过记录最小索引位置，减少交换次
	 */
	public static void fastSortB(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			int minIdx = i;
			int minNum = arr[i];
			for (int j = i + 1; j < arr.length; j++) {
				if (minNum > arr[j]) {
					minIdx = j;
					minNum = arr[j];
				}
			}
			if (i != minIdx) {
				int tmp = arr[i];
				arr[i] = arr[minIdx];
				arr[minIdx] = tmp;
			}
		}
	}

	public static void printArr(int[] arr) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i = 0; i < arr.length; i++) {
			if (i < arr.length - 1)
				sb.append(arr[i]).append(", ");
			else {
				sb.append(arr[i]);
			}
		}
		sb.append("]");
		System.out.println(sb);
	}
}

