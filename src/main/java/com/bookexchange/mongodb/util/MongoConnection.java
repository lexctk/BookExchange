package com.bookexchange.mongodb.util;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {
	
	private static MongoConnection instance = null;
	
	public MongoClient mongo = null;
	public MongoDatabase database = null;
	
	private MongoConnection() {
		
		//TODO
		//String mlab = System.getenv("MLAB");
		String mlab = "mongodb://book_adm:cAsbid-fokdyz-2vidbi@ds115523.mlab.com:15523/book_exchange";

		mongo = MongoClients.create (mlab);
		database = mongo.getDatabase("book_exchange");		
	}
	
	public static MongoConnection getInstance() {
		if (instance == null) {
			instance = new MongoConnection();
		}
		return instance;
	}	
}
