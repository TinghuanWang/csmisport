package com.tiedate.csmis.server;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.stereotype.Service;

import com.tiedate.csmis.thread.WsServerThread;

@Service("wsServiceBO")
@WebService(targetNamespace="http://impl.ws.com")
public class WsServerImpl implements WsServer {

	@Override
	public String getUserName(@WebParam(name = "name") String name) {
		// TODO Auto-generated method stub
//		new WsServerThread(name).run();
		try {
			System.out.println(name);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		return name;
	}

	@Override
	public void setUserName() {
		// TODO Auto-generated method stub
		
		System.out.println("--------SetUserName");
	}

	

}
