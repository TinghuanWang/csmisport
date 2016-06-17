package com.tiedate.csmis.serverdata.jxfh;

import com.tiedate.csmis.common.constants.SystemConstants;
import com.tiedate.csmis.common.utils.Config;
import com.tiedate.csmis.common.utils.StringUtil;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 上海铁大电信
 * </p>
 * @author not attributable
 * @version 1.0
 */

public class DataInformationServer{
	private static final Logger logger = Logger.getLogger(DataInformationServer.class);

	private static  String uId = "";
	/**
     * 多媒体终端信息同步
     * ------------------------------------------------------------
     *	<?xml version="1.0" encoding="UTF-8"?>
		<ddzh version="1.0" data_type="terminal_inf">
			<data_item>
			<DEP_ID>0001</DEP_ID>
			<TERM_NAME>咸宁手持台1号cjw</TERM_NAME>
			<TERM_NUM>12345</TERM_NUM>
			</data_item>
			<data_item>
			<DEP_ID>0002</DEP_ID>
			<TERM_NAME>咸宁手持台2号</TERM_NAME>
			<TERM_NUM>3333333</TERM_NUM>
			</data_item>
		</ddzh>
     * -------------------------------------------------------------
     * @return
     * @throws IOException
     */
    public static String terminalSync(String xml)throws IOException{
    	String status = "error";

//		response.setContentType("application/json;charset=UTF-8");
//    	StringReader sr = new StringReader(xml);
//    	SAXReader reader = new SAXReader();

    	WebServiceTerminalQuery terminalQue = new WebServiceTerminalQuery();
    	Map<String, String> termMap = new HashMap<String, String>();
    	List<Object[]> allTerminal = terminalQue.getServiceTerminalList();
    	for(Object[] temp : allTerminal){
    		termMap.put(temp[0].toString(), temp[1].toString());
    	}
    	try {
    		//Document doc = r.read(new File("D:\\test.xml"));
			Document doc = DocumentHelper.parseText(xml);
			boolean encoding = "UTF-8".equals(doc.getXMLEncoding())
					||"utf-8".equals(doc.getXMLEncoding()) ? true : false;
			Element root = doc.getRootElement();
			if(null != root.attribute("data_type") && "terminal_inf".equals(root.attribute("data_type").getText())){
				List<Element> childs = root.elements("data_item");
				Object[] terminal = {};
				String code = "";
				String sortNum = "";
				String name = "";
				int count = 0;
				for(Element e:childs){
					code = e.element("DEP_ID").getText();
					name = e.element("TERM_NAME").getText();
					sortNum = e.element("TERM_NUM").getText();
					terminal = new Object[childs.size()];
					terminal[0] = code;
					terminal[1] = encoding ? name : new String(name.getBytes(),"utf-8");
					terminal[2] = sortNum;
					//更新逻辑
					//如果同步数据在本地机构中存在执行更新逻辑，否则在本地创建新的机构
					if(StringUtil.isNotEmpty(sortNum) && StringUtil.isNotEmpty(termMap.get(sortNum))){
						count = terminalQue.editTerminalInfo(terminal);
					}else{
						count = terminalQue.addTerminalInfo(terminal);
					}
					if(termMap.containsKey(code)){
						termMap.remove(code);
					}
				}
				//处理同步数据中不存在的本地机
				for(String tobeDelId : termMap.keySet()){
					if(StringUtil.isNotEmpty(tobeDelId))
						terminalQue.deleteTerminalInfo(tobeDelId);
				}
				//out.println("success");
				if (count > 0) {
					//生成XML
					saveFile(xml, "terminalSync.xml");
					status = "success";
				}
				//out.print(dept.toJsonString());
			}else{
				//out.println("error");
				logger.error("XML ERROR : Get wrong 'data_type'!["+root.attribute("data_type").getText()+"]");
				saveFile("XML ERROR : Get wrong 'data_type'!["+root.attribute("data_type").getText()+"]", "terminalSync.txt");
			}
		} catch (DocumentException e) {
			//out.println("error");
			logger.error(e.getMessage(),e);
			saveFile(e.getMessage(),"terminalSync.txt");
		}

    	return status;
    }
    /**
     * 作业提交同步
     * ------------------------------------------------------------
     <?xml version="1.0" encoding="UTF-8"?>
     <ddzh version="1.0" data_type="work_submit">
        <WORK_ID>0001</WORK_ID>
        <EQP_NAME>1号道岔</EQP_NAME>
        <Step>当前步骤</ Step>
        <Remark>我是备注</Remark>
        <AttchmentList>
            <Attachment>
                <Time>2015-04-03 15:12:13</Time>
                <Type>jpg</Type>
                <URL>http://123.56.107.219/dddd.jpg</URL>
            </Attachment>
            <Attachment>
                 <Time>2015-04-03 15:12:13</Time>
                <Type>jpg</Type>
                <URL>http://123.56.107.219/dddd.jpg</URL>
             </Attachment>
         </AttchmentList>
     </ddzh>

     * -------------------------------------------------------------
     * @return
     * @throws IOException
     */
    public static String workSync(String xml)throws IOException{
    	String status = "error";

    	//response.setContentType("application/json;charset=UTF-8");
    	/*StringReader sr = new StringReader(xml);
    	SAXReader reader = new SAXReader();*/

    	WebServiceTerminalQuery terminalQue = new WebServiceTerminalQuery();
    	Map<String, String> termMap = new HashMap<String, String>();
    	List<Object[]> allWork = terminalQue.getServiceWorkList();
    	for(Object[] temp : allWork){
    		termMap.put(temp[0].toString(), temp[1].toString());
    	}
    	try {
    		//Document doc = reader.read(new File("D:\\work.xml"));

			Document doc = DocumentHelper.parseText(xml);
			boolean encoding = "UTF-8".equals(doc.getXMLEncoding())
					||"utf-8".equals(doc.getXMLEncoding()) ? true : false;
    		Object[] works = {};
			Object[] img = {};
			String workId = "";
			String eqpName = "";
            String imgstep = "";
            String remark = "";
			String imgTime = "";
			String imgType = "";
			String data = "";
			Element root = doc.getRootElement();
			boolean on = true;
			int count = 0;
			if(null != root.attribute("data_type") && "work_submit".equals(root.attribute("data_type").getText())){
				List<Element> work = root.elements("WORK_ID");
				if(work.size() > 0) {
					Element workElement = (Element)work.get(0);
					workId = workElement.getText();
				} else {
					workId = "";
				}
				List<Element> nameList = root.elements("EQP_NAME");
				if(nameList.size() > 0) {
					Element nameElement = (Element)nameList.get(0);
					eqpName = nameElement.getText();
				} else {
					eqpName = "";
				}
                List<Element> stepList = root.elements("Step");
                if(stepList.size() > 0) {
                    Element stepElement = (Element)stepList.get(0);
                    imgstep = stepElement.getText();
                } else {
                    imgstep = "";
                }
                List<Element> remarkList = root.elements("Remark");
                if(remarkList.size() > 0) {
                    Element remarkElement = (Element)remarkList.get(0);
                    remark = remarkElement.getText();
                } else {
                    remark = "";
                }

                List<Element> imgChils = null;
                List<Element> Attchments = root.elements("AttchmentList");
                if(Attchments.size() > 0){
                    Element aa = (Element) Attchments.get(0);
                    imgChils = aa.elements();
                }


                works = new Object[5];
				works[0] = workId;
				works[1] = encoding ? eqpName : new String(eqpName.getBytes(),"utf-8");
				works[2] = encoding ? imgstep : new String(imgstep.getBytes(),"utf-8");
				works[3] = encoding ? remark : new String(remark.getBytes(),"utf-8");
				for (Element es:imgChils){
					imgTime = es.element("Time").getText();
					imgType = es.element("Type").getText();
					data = es.element("URL").getText();
					img = new Object[5];
					img[1] = imgTime;
					img[2] = imgType;
					img[3] = data;
					boolean falg = false;
					if(StringUtil.isNotEmpty(workId) && StringUtil.isNotEmpty(termMap.get(workId))){
						falg = true;
						count = terminalQue.editWorkSubmitInfo(works, img, falg, on);
						on = false;
					}else{
						count = terminalQue.editWorkSubmitInfo(works, img, falg, on);
						on = false;
					}
					if(termMap.containsKey(workId)){
						termMap.remove(workId);
					}
				}
				//处理同步数据中不存在的本地机�?
//				for(String tobeDelId : termMap.keySet()){
//					if(StringUtil.isNotEmpty(tobeDelId))
//						terminalQue.deleteTerminalInfo(tobeDelId);
//				}
				if (count > 0) {
					//生成XML
					saveFile(xml, "workSync.xml");
					status = "success";
				}
				//out.print(dept.toJsonString());
			}else{
				logger.error("XML ERROR : 路径错误------Get wrong 'data_type'!["+root.attribute("data_type").getText()+"]");
				saveFile("XML ERROR : 路径错误------Get wrong 'data_type'!["+root.attribute("data_type").getText()+"]", "workSync.txt");
			}
		} catch (DocumentException e) {
			logger.error(e.getMessage(),e);
			saveFile(e.getMessage(), "workSync.txt");
		}

    	return status;
    }

	/***
	 * 维修作业完成同步
	 <?xml version="1.0" encoding="utf-8" ?>
	 <ddzh version="1.0" data_type="fix_workcomplete">
		 <WORK_ID>0001</WORK_ID>
		 <Remark></Remark>
	 </ddzh>
	 * @param xml
	 * @return
	 * @throws IOException
	 */
	public static String workcompleteSync(String xml)throws IOException{
		String status = "error";

		//response.setContentType("application/json;charset=UTF-8");
    	/*StringReader sr = new StringReader(xml);
    	SAXReader reader = new SAXReader();*/

		WebServiceTerminalQuery terminalQue = new WebServiceTerminalQuery();
		Map<String, String> termMap = new HashMap<String, String>();
		List<Object[]> allWork = terminalQue.getServiceWorkCompleteList();
		for(Object[] temp : allWork){
			termMap.put(temp[0].toString(), temp[1].toString());
		}
		try {
			//Document doc = reader.read(new File("D:\\work.xml"));

			Document doc = DocumentHelper.parseText(xml);
			boolean encoding = "UTF-8".equals(doc.getXMLEncoding())
					||"utf-8".equals(doc.getXMLEncoding()) ? true : false;
			Object[] works = {};
			Object[] img = {};
			String workId = "";
			String Remark = "";

			Element root = doc.getRootElement();
			boolean on = true;
			int count = 0;
			if(null != root.attribute("data_type") && "fix_workcomplete".equals(root.attribute("data_type").getText())){
				List<Element> work = root.elements("WORK_ID");
				if(work.size() > 0) {
					Element workElement = (Element)work.get(0);
					workId = workElement.getText();
				} else {
					workId = "";
				}
				List<Element> nameList = root.elements("Remark");
				if(nameList.size() > 0) {
					Element nameElement = (Element)nameList.get(0);
					Remark = nameElement.getText();
				} else {
					Remark = "";
				}
				works = new Object[5];
				works[0] = workId;
				works[1] = encoding ? Remark : new String(Remark.getBytes(),"utf-8");

				boolean falg = false;
				if(StringUtil.isNotEmpty(workId) && StringUtil.isNotEmpty(termMap.get(workId))){
					falg = true;
					count = terminalQue.editWorkCompleteInfo(works, falg, on);
					on = false;
				}else{
					count = terminalQue.editWorkCompleteInfo(works, falg, on);
					on = false;
				}
				if(termMap.containsKey(workId)){
					termMap.remove(workId);
				}
				//处理同步数据中不存在的本地机�?
//				for(String tobeDelId : termMap.keySet()){
//					if(StringUtil.isNotEmpty(tobeDelId))
//						terminalQue.deleteTerminalInfo(tobeDelId);
//				}
				if (count > 0) {
					//生成XML
					saveFile(xml, "workcompleteSync.xml");
					status = "success";
				}
				//out.print(dept.toJsonString());
			}else{
				logger.error("XML ERROR : 路径错误------Get wrong 'data_type'![" + root.attribute("data_type").getText() + "]");
				saveFile("XML ERROR : 路径错误------Get wrong 'data_type'!["+root.attribute("data_type").getText()+"]", "workcompleteSync.txt");
			}
		} catch (DocumentException e) {
			logger.error(e.getMessage(),e);
			saveFile(e.getMessage(), "workcompleteSync.txt");
		}

		return status;
	}

    /**
     * 设备缺陷上报同步
     * ------------------------------------------------------------
     *	<?xml version="1.0" encoding="UTF-8"?>
		<ddzh version="1.0" data_type="eqp_bug_rpt">
			<STA_NAME>咸宁车站</STA_NAME>
			<TIME>2014-7-9 12:00:00</TIME>
			<REPORTER>张三</REPORTER>
			<EQP_TYPE>道岔</EQP_TYPE>
			<EQP_NAME>1号道岔</EQP_NAME>
			<DESCRIPTION>外壳裂缝</DESCRIPTION>
			<image>
				<TIME>2014-7-9 23:00:00</TIME>
				<TYPE>JPG</TYPE>
				<DATA>khksyidKFNASDF879QWERQWERNKHKAHSDF</DATA>
			</image>
			<image>
				<TIME>2014-7-9 23:00:00</TIME>
				<TYPE>GIF</TYPE>
				<DATA>khksyidKFNASDF879QWERQWERNKHKAHSDF</DATA>
			</image>
		</ddzh>
     * -------------------------------------------------------------
     * @return
     * @throws IOException
     */
    public static String bugRptSync(String xml)throws IOException{
    	String status = "error";

    	//response.setContentType("application/json;charset=UTF-8");
    	//StringReader sr = new StringReader(xml);
    	//SAXReader reader = new SAXReader();

    	WebServiceTerminalQuery terminalQue = new WebServiceTerminalQuery();
    	List<Object[]> allTerminal = terminalQue.getServiceBugRptList();
    	try {
    		//Document doc = reader.read(new File("D:\\image.xml"));
			Document doc = DocumentHelper.parseText(xml);
			boolean encoding = "UTF-8".equals(doc.getXMLEncoding())
					||"utf-8".equals(doc.getXMLEncoding()) ? true : false;
    		Object[] bug = {};
			Object[] img = {};
			String staName = "";
			String time = "";
			String reporter = "";
			String eqpType = "";
			String eqpName = "";
			String description = "";
			String imgTime = "";
			String imgType = "";
			String data = "";
			boolean falg = false;
			Element root = doc.getRootElement();
			if(null != root.attribute("data_type") && "eqp_bug_rpt".equals(root.attribute("data_type").getText())){
				List<Element> sta = root.elements("STA_NAME");
				Element staElement = (Element)sta.get(0);
				staName = staElement.getText();
				List<Element> timList = root.elements("TIME");
				Element timElement = (Element)timList.get(0);
				time = timElement.getText();
				List<Element> repoList = root.elements("REPORTER");
				Element repElement = (Element)repoList.get(0);
				reporter = repElement.getText();
				List<Element> typeList = root.elements("EQP_TYPE");
				Element typeElement = (Element)typeList.get(0);
				eqpType = typeElement.getText();
				List<Element> nameList = root.elements("EQP_NAME");
				Element nameElement = (Element)nameList.get(0);
				eqpName = nameElement.getText();
				List<Element> descList = root.elements("DESCRIPTION");
				Element descElement = (Element)descList.get(0);
				description = descElement.getText();
				bug = new Object[10];
				bug[0] = encoding ? staName : new String(staName.getBytes(),"utf-8");
				bug[1] = time;
				bug[2] = encoding ? reporter : new String(reporter.getBytes(),"utf-8");
				bug[3] = encoding ? eqpType : new String(eqpType.getBytes(),"utf-8");
				bug[4] = encoding ? eqpName : new String(eqpName.getBytes(),"utf-8");
				bug[5] = encoding ? description : new String(description.getBytes(),"utf-8");
				if(isExist(allTerminal,bug)) {
					falg = true;
				}
				int count = 0;
				boolean on = true;
					List<Element> imgChilds = root.elements("image");
					for (Element es:imgChilds){
						imgTime = es.element("TIME").getText();
						imgType = es.element("TYPE").getText();
						data = es.element("DATA").getText();
						img = new Object[5];
						if(StringUtil.isNotEmpty(uId)) {
							img[0] = uId;
						}
						img[1] = imgTime;
						img[2] = imgType;
						img[3] = data;
						//�?��更新逻辑
						//如果同步数据在本地机构中存在执行更新逻辑，否则在本地创建新的机构
						if(falg) {
							count = terminalQue.editBugRptInfo(bug, img, falg, on);
							on = false;
						} else {
							count = terminalQue.editBugRptInfo(bug, img, falg, on);
							on = false;
						}
					}
				if (count > 0) {
					//生成XML
					saveFile(xml, "bugRptSync.xml");
					status = "success";
				}
				//out.print(dept.toJsonString());
			}else{
				logger.error("XML ERROR : Get wrong 'data_type'!["+root.attribute("data_type").getText()+"]");
				saveFile("XML ERROR : Get wrong 'data_type'!["+root.attribute("data_type").getText()+"]", "bugRptSync.txt");
			}
		} catch (DocumentException e) {
			//out.println("error");
			logger.error(e.getMessage(),e);
			saveFile(e.getMessage(), "bugRptSync.txt");
		}

    	return status;
    }
   /**
    * 判断数据库是否有该条记录
    * @param objList 源集合
    * @param bugReport XML集合
    * @return boolean
    */
    public static boolean isExist(List<Object[]> objList, Object[] bugReport) {
    	boolean falg = false;
    	String tempStr = "";
    	for (int i = 0; i < objList.size(); i++) {
    		Object[] obj = (Object[]) objList.get(i);
    		for (int j = 0; j < obj.length; j++) {
				tempStr = obj[0].toString();
    			if(obj[i+1].toString().trim().equals(bugReport[i].toString().trim())){
        			falg = true;
        		} else {
        			falg = false;
        			break;
        		}
    		}

		}
    	if (falg) {
    		uId = tempStr;
    	}
    	return falg;
    }

    public static  void saveFile(String fileStr, String fileName) {
    	//指定文件夹生成XML文件
		String path = Config.getStringProperty(SystemConstants.XML_SAVED_DIR);
	    ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(fileStr.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Client.saveFile(bais, path + fileName, path);
    }


//	public static void main(String[] args) {
//		DataInformationServer servlet = new DataInformationServer();
//		try {
//			String str = servlet.workSync(null);
//			System.out.println(str);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
}
