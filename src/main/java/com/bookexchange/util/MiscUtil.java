package com.bookexchange.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
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
	
	public static String getDateDifference(String date) {
		LocalDate now = LocalDate.now();
		LocalDate dateTime = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		
		Period period = Period.between(dateTime, now);
		
		if (period.getYears() > 0) return period.getYears() + "y";
		if (period.getMonths() > 0) return period.getMonths() + "m";
		if (period.getDays() > 0) return period.getDays() + "d";
		
		return "today";
	}
	
	public static String getJSONString (String query, String index, String baseAPI, String fieldsAPI, String apiKeyName, String apiKeyEnv) throws RuntimeException, UnsupportedEncodingException {
		
		StringBuilder json = new StringBuilder(); 

		query = URLEncoder.encode(query, "utf-8");
		query = baseAPI + query + fieldsAPI;
		
		if (index.length()>0) {
			query += "&startIndex=" + index;
		}
		
		HttpURLConnection conn = null;
        BufferedReader reader = null;
        
        try {  
            URL url = new URL(query);  
            
            conn = (HttpURLConnection)url.openConnection();  
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            String APIKey = System.getenv(apiKeyEnv);
    		if (APIKey == null) APIKey = MiscUtil.getValue (apiKeyEnv);
    		
            conn.setRequestProperty(apiKeyName, APIKey);
            
            if (conn.getResponseCode() != 200) {
                return ("{\"error\": \"API doesn't respond\"");
            }
            
	        reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
	        
            String output = null;  
            while ((output = reader.readLine()) != null)  
            	json.append(output);  
        } catch(MalformedURLException e) {
            e.printStackTrace();
            return ("{\"error\": \"Try again\"");
        } catch(IOException e) {  
            e.printStackTrace();
            return ("{\"error\": \"Something went wrong\"");
        }
        finally {
            if(reader!=null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null) {
                conn.disconnect();
            }
        }        
        		
		return json.toString();
	}	
}
