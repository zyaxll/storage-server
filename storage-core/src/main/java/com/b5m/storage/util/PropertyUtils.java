package com.b5m.storage.util;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Property工具类,用于处理Property相关功能,如读写Properties文件等
 * 
 * @author 丹青生
 *
 * @date 2015-8-11
 */
public abstract class PropertyUtils {

	private PropertyUtils(){}

	/**
	 * 搜索和读取classpath下指定配置文件内容(总是不会返回null)
	 * 
	 * @param classLoader 类加载器
	 * @param config 配置文件名(eq.config.properties)
	 * @return 读取到的Properties配置信息,如果配置文件不存在则返回空的properties对象
	 * @throws IOException IO错误
	 */
	public static Properties load(ClassLoader classLoader, String config) throws IOException {
		Properties properties = new Properties();
		Enumeration<URL> urls = null;
		if(classLoader != null){
			urls = classLoader.getResources(config);
		}else {
			urls = ClassLoader.getSystemResources(config);
		}
		if (urls != null) {
			if(urls.hasMoreElements()) {
				properties.load(urls.nextElement().openStream());
//			}else if(LOGGER.isDebugEnabled()){
//				LOGGER.debug("未能在classpath下找到配置文件:{}", config);
			}
		}
		return properties;
	}
	
}
