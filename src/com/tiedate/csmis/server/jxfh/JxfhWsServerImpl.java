package com.tiedate.csmis.server.jxfh;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tiedate.csmis.common.utils.FormateDataTools;
import com.tiedate.csmis.serverdata.jxfh.*;;

/**   
* @Description: 佳讯飞鸿接口服务器实现类
* @author WTH   
* @date 2016/6/16 
* @version V1.0
*/ 
@Service("wsServiceJxfh")
@WebService(targetNamespace = "http://www.tddx.com.cn/")
public class JxfhWsServerImpl implements JxfhWsServer {

	private static final Logger logger = Logger.getLogger(JxfhWsServerImpl.class);
	
	 /**
     * 作业提交同步
     *
     * @return
     */
    public String workSync(String xml) {
        System.out.println("==============================================");
        System.out.println(xml);
        System.out.println("==============================================");
        String backValue = "";
        try {

            backValue = FormateDataTools.formatXml(DataInformationServer.workSync(xml));
            return backValue;
        } catch (Exception ex) {
            logger.error("Error");
            backValue = FormateDataTools.formatXml("");
            ex.printStackTrace();
        }
        return backValue;
    }

    /**
     * 维修作业完成 同步
     *
     * @return
     */
    public String workcompleteSync(String xml) {
        System.out.println("==============================================");
        System.out.println(xml);
        System.out.println("==============================================");
        String backValue = "";
        try {

            backValue = FormateDataTools.formatXml(DataInformationServer.workcompleteSync(xml));
            return backValue;
        } catch (Exception ex) {
            logger.error("Error");
            backValue = FormateDataTools.formatXml("");
            ex.printStackTrace();
        }
        return backValue;
    }

    /**
     * 多媒体终端信息同步
     *
     * @return
     */
    public String terminalSync(String xml) {
        String backValue = "";
        try {

            backValue = FormateDataTools.formatXml(DataInformationServer.terminalSync(xml));
            return backValue;
        } catch (Exception ex) {
            logger.error("Error");
            backValue = FormateDataTools.formatXml("");
            ex.printStackTrace();
        }
        return backValue;
    }

    /**
     * 设备缺陷上报同步
     *
     * @return
     */
    public String bugRptSync(String xml) {
        String backValue = "";
        try {

            backValue = FormateDataTools.formatXml(DataInformationServer.bugRptSync(xml));
            return backValue;
        } catch (Exception ex) {
            logger.error("Error");
            backValue = FormateDataTools.formatXml("");
            ex.printStackTrace();
        }
        return backValue;
    }
    
    /***
     * 请求检验项
     *
     * @param xml
     * @return xml
     */
    public String checkEndSync(String xml) {
        String backValue = "";
        try {
            System.out.println("检验项请求提交的xml----------");
            System.out.println(xml);
            /***
             *  默认 -1
             */
            backValue = DataInformationCheckout.checkEnd(xml);//转向第三方（监测）接口交互。
            backValue = FormateDataTools.formatXml(backValue);
        } catch (Exception ex) {
            logger.error("Error");
            backValue = FormateDataTools.formatXml("");
            ex.printStackTrace();
        }
        return backValue;
    }
}
