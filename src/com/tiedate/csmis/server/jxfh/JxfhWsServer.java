package com.tiedate.csmis.server.jxfh;

import javax.jws.WebService;

/**   
* @Description: 佳讯飞鸿接口服务器接口
* @author WTH   
* @date 2016/6/16 
* @version V1.0
*/ 

@WebService(serviceName = "JxfhWsServer", targetNamespace = "http://www.tddx.com.cn/")
public interface JxfhWsServer {

	/**
	 * 作业提交同步
	 *
	 * @return
	 */
	public String workSync(String xml);

	/**
	 * 维修作业完成 同步
	 *
	 * @return
	 */
	public String workcompleteSync(String xml);

	/**
	 * 多媒体终端信息同步
	 *
	 * @return
	 */
	public String terminalSync(String xml);

	/**
	 * 设备缺陷上报同步
	 *
	 * @return
	 */
	public String bugRptSync(String xml);

	/***
	 * 请求检验项
	 *
	 * @param xml
	 * @return xml
	 */
	public String checkEndSync(String xml);
}
