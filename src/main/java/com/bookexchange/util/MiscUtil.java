package com.bookexchange.util;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;


public class MiscUtil {
	
	
	/**
	 * Get a key value from config.properties file
	 * @param key
	 * @return value as string
	 */
	public static String getValue (String key) {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = MiscUtil.class.getResourceAsStream("/config.properties");
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
	
	/**
	 * Return current time/date in string format '2011-12-03T10:15:30'
	 * @return string of current ISO time/date
	 */
	public static String nowToString () {
		LocalDateTime now = LocalDateTime.now();
		return now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}
	
	/**
	 * Get month from date string
	 * @param date
	 * @return month as string, in English (January, February...)
	 */
	public static String getRegisteredMonth(String date) {
		LocalDate dateTime = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		DateTimeFormatter df_en = DateTimeFormatter.ofPattern("MMMM").withLocale(Locale.ENGLISH);
		return dateTime.format(df_en);
	}
	
	/**
	 * Get year from date string
	 * @param date
	 * @return year as 'YYYY' format
	 */
	public static String getRegisteredYear(String date) {
		LocalDate dateTime = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		DateTimeFormatter df_en = DateTimeFormatter.ofPattern("yyyy").withLocale(Locale.ENGLISH);
		return dateTime.format(df_en);		
	}
}
