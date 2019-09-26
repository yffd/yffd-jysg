package com.yffd.jysg.java.v7.p4;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年11月21日 下午1:40:57 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class BinarySearch {

	public static void main(String[] args) {
		int[] sortArr = {1, 3, 4, 4, 5, 6, 8, 9, 28, 45, 56, 78, 81};
		int ret = binarySearch(45, sortArr);
		System.out.println(ret);
	}

	public static int binarySearch(int num, int[] sortArr) {
		int low = 0;
		int hight = sortArr.length - 1;
		int mid = sortArr.length / 2;
		while (low <= hight) {
			mid = (low + hight) / 2;
			if (num > sortArr[mid]) {
				low = mid + 1;
			} else if (num < sortArr[mid]) {
				hight = mid - 1;
			} else {
				return mid;
			}
		}
		return -1;
	}
}

