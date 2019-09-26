package com.yffd.jysg.java.v8.p3;

import java.util.concurrent.RecursiveTask;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年12月4日 下午2:45:00 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class ForkJoinCalculate extends RecursiveTask<Long> {
	private static final long serialVersionUID = -4637475449249572364L;
	
	private long start;
	private long end;
	
	private static final long THRESHOLD = 10000L; //临界值
	
	public ForkJoinCalculate(long start, long end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	protected Long compute() {
		long length = end - start;
		
		if(length <= THRESHOLD){
			long sum = 0;
			for (long i = start; i <= end; i++) {
				sum += i;
			}
			return sum;
		}else{
			long middle = (start + end) / 2;
			
			ForkJoinCalculate left = new ForkJoinCalculate(start, middle);
			left.fork(); //拆分，并将该子任务压入线程队列
			
			ForkJoinCalculate right = new ForkJoinCalculate(middle+1, end);
			right.fork();
			
			return left.join() + right.join();
		}
	}

}

