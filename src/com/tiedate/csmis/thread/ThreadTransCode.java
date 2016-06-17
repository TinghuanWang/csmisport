package com.tiedate.csmis.thread;

public class ThreadTransCode implements Runnable {

	private String name;

	public ThreadTransCode() {
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int i=0;
		while(i<1000){
			System.out.println("--------------子线程执行任务");
			i++;
		}
	}

}
