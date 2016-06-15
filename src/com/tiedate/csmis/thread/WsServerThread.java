package com.tiedate.csmis.thread;

public class WsServerThread implements Runnable {
	private String vcName;
	
	public WsServerThread(String name){
		this.vcName = name;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized (this) {
			getCheck();
		}

	}
	
	public String getCheck(){
		try {
		System.out.println(vcName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
