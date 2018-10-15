package com.bookexchange.mongodb.util;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Util {
	
    public static MongoCollection<Document> collection (MongoDatabase database, String collectionName) {
    	return database.getCollection(collectionName);
    }

	public static Document searchUser (String email, String password, MongoDatabase database) {
		Document userFound = null;
		
		MongoCollection<Document> collection = database.getCollection("users");
		
		FindIterable<Document> it = collection.find(and(eq("email", email), eq("password", password)));
		for(@SuppressWarnings("unused") Document doc : it) {
			userFound = doc;
		}

		return userFound;
	}
	
	public static boolean searchEmail (String email, MongoDatabase database) {
		boolean emailFound = false;
		
		MongoCollection<Document> collection = database.getCollection("users");
		
		FindIterable<Document> it = collection.find(eq("email", email));
		for(@SuppressWarnings("unused") Document doc : it) {
			emailFound = true;
		}

		return emailFound;
	}	
}
