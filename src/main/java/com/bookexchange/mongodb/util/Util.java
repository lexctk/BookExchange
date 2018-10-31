package com.bookexchange.mongodb.util;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;

import com.bookexchange.mongodb.model.Book;
import com.bookexchange.mongodb.model.User;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Util {

	public static Document authenticateUser (String email, String password, MongoDatabase database) {
		Document userFound = null;
		
		MongoCollection<Document> collection = database.getCollection("users");
		
		//there's only one user with this email
		Document profile = collection.find(eq("email", email)).first();
		if (profile != null) {
			if (profile.containsKey("password") && BCrypt.checkpw(password, (String)profile.get("password"))) {
				userFound = profile;
			}
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
	
	public static boolean addBooktoCollection (Book book, User user, MongoDatabase database) {
		boolean bookadded = false;
		MongoCollection<Document> collection = database.getCollection("books");
		
		Document findBook = collection.find(eq("id", book.getId())).first();
		if (findBook != null) {
			// this book already exists in collection, just update user ownership and book ownership
		} else {
			// it's a new book, add to collection update user ownership and book ownership
			System.out.println("Check");
		}
		return bookadded;
	}
}
