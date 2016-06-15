package com.tiedate.csmis.client;

import java.util.Date;

import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.tiedate.csmis.server.WsServer;

public class TestClient {

	public static void main(String[] args) {
		Long sLong = new Date().getTime();
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(WsServer.class);
		factory.setAddress("http://localhost:8080/cp/webservice/myWebservice");
		WsServer wsServer = (WsServer)factory.create();
		int i=0;
		while (i<10000) {
			wsServer.getUserName("eclipse-request--"+i);
			i++;
		}
		
//		ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
//        factory.setServiceClass(WsServer.class);
//        factory.setAddress("http://localhost:8080/cp/webservice/myWebservice");
////        factory.getServiceFactory().setDataBinding(new AegisDatabinding());
//        WsServer client = (WsServer) factory.create();
//        int i=0;
//		while (i<10000) {
//			client.getUserName("lisi"+i);
//			i++;
//		}
//		
		Long sLong2 = new Date().getTime();
		System.out.println(sLong2-sLong);
	}

}
