package com.bookexchange.mongodb.util;

import com.mongodb.client.MongoClients;
import com.bookexchange.util.PropertiesUtil;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {
	
	private static MongoConnection instance = null;
	
	public MongoClient mongo = null;
	public MongoDatabase database = null;
	
	private MongoConnection() {
		
		//get mlab url from environment variable or from properties file
		String mlab = System.getenv("MLAB");
		if (mlab == null) mlab = PropertiesUtil.getValue ("MLAB");

		mongo = MongoClients.create(mlab);
		
		database = mongo.getDatabase("book_exchange");
	}
	
	public static MongoConnection getInstance() {
		if (instance == null) {
			instance = new MongoConnection();
		}
		return instance;
	}	
}
