package com.tiedate.csmis.thread;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.TaskRejectedException;

public class BizManager {

	public static void main(String[] args) {

		try{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"classpath:config/spring-common.xml");
		TaskExecutor taskExecutor = (TaskExecutor)ctx.getBean("taskExecutor");
		int n = 100;
		for (int k = 0; k < n; k++) {
			//taskExecutor.execute(new ThreadTransCode());
			n++;
		}
		}catch (TaskRejectedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
