package com.bookexchange.mongodb.util;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;

import com.bookexchange.mongodb.model.Book;
import com.bookexchange.mongodb.model.User;
import com.google.gson.Gson;
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
	
	public static boolean addBookToCollection (Book book, User user, MongoDatabase database) {
		boolean bookAdded = false;
		
		MongoCollection<Document> collection = database.getCollection("books");
		
		Document findBook = collection.find(eq("id", book.getId())).first();
		if (findBook != null) {
			// TODO: this book already exists in collection, just update user ownership and book ownership
		} else {
			// it's a new book, add to collection update user ownership and book ownership
			if (book.addUser(user.get_id().toString())) {
				
				// true: insert book in collection
				Gson gson = new Gson();
		        String json = gson.toJson(book);
		        Document document = Document.parse(json);
		        collection.insertOne(document);
		        bookAdded = addBookToUser (book, user, database);
		        
			} else {
				// false: user already owns this book
				// TODO: send error message
			}

		}
		return bookAdded;
	}
	
	public static boolean addBookToUser (Book book, User user, MongoDatabase database) {
		boolean bookAdded = false;

        MongoCollection<Document> collection = database.getCollection("users");
        Document findUser = collection.find(eq("_id", user.get_id())).first();
        
        if (findUser == null) {
        	//TODO: handle error: this is an exception, user should be logged in so should exist in the database!
        } else {
        	collection.updateOne(
        			eq("_id", user.get_id()),
        			new Document("$push", new Document("bookIDs", book.getId())));
        	bookAdded = true;
        }
        return bookAdded;
	}
}
