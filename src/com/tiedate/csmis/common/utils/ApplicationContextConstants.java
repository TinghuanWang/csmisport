package com.tiedate.csmis.common.utils;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextConstants {
	
	private static ClassPathXmlApplicationContext cxt = null;
	
	public static ClassPathXmlApplicationContext getApplicationContext(){
		try{
			if(cxt==null){
				cxt = new ClassPathXmlApplicationContext("classpath:config/spring-common.xml");
			}else{
				return cxt;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return cxt;
		
	}

}
