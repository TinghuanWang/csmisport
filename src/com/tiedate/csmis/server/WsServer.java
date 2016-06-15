package com.tiedate.csmis.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName="WsServer",targetNamespace="http://impl.ws.com")
public interface WsServer {

	@WebMethod
	public String getUserName(@WebParam(name = "name") String name);
	
	@WebMethod
	public void setUserName();
}
