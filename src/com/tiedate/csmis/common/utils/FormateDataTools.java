package com.tiedate.csmis.common.utils;

import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/***
 * xml格式化工具类
 * <p/>
 * Created by wth on 2016/6/17.
 * 
 */
public class FormateDataTools {

	public FormateDataTools(){
		
	}
	
	 /**
     * 生成返回结果 xml 格式字符串
     *
     * @param keyStr
     * @return
     */
    public static String formatXml(String keyStr) {
        Document document = DocumentHelper.createDocument();
        StringWriter sw = new StringWriter();
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            XMLWriter xw = new XMLWriter(sw, format);
            if (keyStr == null || keyStr == "") {
                keyStr = "0";
            }
            Element root = document.addElement("root");

            Element result = root.addElement("result");
            Element message = result.addElement("message");

            Element key = result.addElement("key");
            if (keyStr == "error" || keyStr == "0") {
                message.setText("ERROR");
                key.setText("0");
            } else if (keyStr == "success" || keyStr == "1") {
                message.setText("SUCCESS");
                key.setText("1");
            } else {
                message.setText(keyStr);
                key.setText("0");
            }
            xw.write(document);
            xw.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sw.toString();

    }
}
