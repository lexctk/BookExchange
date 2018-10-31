package com.bookexchange.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	
	public static String getValue (String key) {
		
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = PropertiesUtil.class.getResourceAsStream("/config.properties");
			prop.load(input);
			return prop.getProperty(key);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
