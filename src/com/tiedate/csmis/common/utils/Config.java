/**
 * 
 */
package com.tiedate.csmis.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; 

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author jingwu.chen
 */
public class Config {
	private final static String APP_PROPERTIES_FILE="/config/dir.properties";
    protected final static Log logger = LogFactory.getLog(Config.class);
    protected static Properties p = null;
 

    /**
     * init
     */
    protected static void init() {
        InputStream in = null;
        try {
            in = Config.class.getClassLoader().getResourceAsStream(APP_PROPERTIES_FILE);
            if (in != null){
            	if ( p == null )
            		p = new Properties();
                p.load(in);
            }
        } catch (IOException e) {
            logger.error("load " + APP_PROPERTIES_FILE + " into Constants error!");
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("close " + APP_PROPERTIES_FILE + " error!");
                }
            }
        }
    }

    /**
     * 
     * @param key          property key.
     * @param defaultValue 
     */
    protected static String getProperty(String key, String defaultValue) {
    	if ( p == null )
    		init();
    	String temp= p.getProperty(key, defaultValue);
    	if(StringUtils.isNotEmpty(temp)){
    		temp = temp.trim();
    	} 
        return temp;
    }
    
    /**
     * 
     * @param key          property key.
     */
    public static String getStringProperty(String key) {
    	if ( p == null )
    		init();
    	String temp=p.getProperty( key );
    	if(StringUtils.isNotEmpty(temp)){
    		temp = temp.trim();
    	}
        return temp;
    }
}
