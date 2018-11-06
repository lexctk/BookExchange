package com.bookexchange.mongodb.util;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;

import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;

import com.bookexchange.mongodb.model.Book;
import com.bookexchange.mongodb.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Util {

	/**
	 * Search user in database by email, and if password matches database value, return user as Document
	 * 
	 * @param email
	 * @param password (hash)
	 * @param database
	 * @return user, or null if no match.
	 */
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
	
	/**
	 * Check if email exists in database (user already registered)
	 * @param email
	 * @param database
	 * @return true if email exists, false if not
	 */
	public static boolean searchEmail (String email, MongoDatabase database) {
		boolean emailFound = false;
		
		MongoCollection<Document> collection = database.getCollection("users");
		
		FindIterable<Document> it = collection.find(eq("email", email));
		for(@SuppressWarnings("unused") Document doc : it) {
			emailFound = true;
		}

		return emailFound;
	}
	
	/**
	 * Get current logged in user from session information. 
	 * 
	 * @param session
	 * @param database
	 * @return user object
	 */
	public static User getCurrentUser (HttpSession session, MongoDatabase database) {
		User user = null;
		
        if (session == null) {
        	return user;
        }
        MongoCollection<Document> collection = database.getCollection("users");
        
        String userJson = collection.find(and(
        		eq("username", (String) session.getAttribute("username")), 
        		eq("_id", (ObjectId) session.getAttribute("_id"))
        		)).first().toJson();
        Gson gson = new GsonBuilder().create();
		user = gson.fromJson(userJson, User.class);
		
		// for some reason gson doesn't parse _id
		user.set_id((ObjectId) session.getAttribute("_id"));

        return user;
	}

	public static Book getOneBook (String id, MongoDatabase database) {
		MongoCollection<Document> collection = database.getCollection("books");
		
		Document bookDoc = collection.find(eq("id", id)).first();
	
		String json = bookDoc.toJson();
		Gson gson = new GsonBuilder().create();
		Book book = gson.fromJson(json, Book.class);

		return book;
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
	
	public static ArrayList<Book> buildUserBooks(HttpSession session, MongoDatabase database) {
		ArrayList<Book> books = new ArrayList<Book>();
		MongoCollection<Document> collection = database.getCollection("books");
		
		User user = Util.getCurrentUser(session, database);
		if (user.getBookIDs() != null) {
			for(String bookID : user.getBookIDs()) {
				FindIterable<Document> it = collection.find(eq("id", bookID));
				for(Document doc : it) {
					String json = doc.toJson();
					Gson gson = new GsonBuilder().create();
		    		Book book = gson.fromJson(json, Book.class);
		    		books.add(book);
				}
			}
		}
		return books;
	}

	public static ArrayList<Book> buildAllBooks(HttpSession session, MongoDatabase database) {
		ArrayList<Book> books = new ArrayList<Book>();
		MongoCollection<Document> collection = database.getCollection("books");
		
		BasicDBObject query = new BasicDBObject();
		
		User user = Util.getCurrentUser(session, database);
		if (user.getBookIDs() != null) {
			query.put("id", new BasicDBObject("$nin", user.getBookIDs()));
		}
		
		FindIterable<Document> it = collection.find(query);
		for(Document doc : it) {
			String json = doc.toJson();
			Gson gson = new GsonBuilder().create();
    		Book book = gson.fromJson(json, Book.class);
    		books.add(book);
		}

		return books;
	}	
	
	public static ArrayList<User> buildBookUsers(List<String> userIDs, MongoDatabase database) {
		ArrayList<User> users = new ArrayList<User>();
		MongoCollection<Document> collection = database.getCollection("users");
		
		for(String userID : userIDs) {
			var objectId = new ObjectId(userID);
			FindIterable<Document> it = collection.find(eq("_id", objectId));
			for(Document doc : it) {
				String json = doc.toJson();
				Gson gson = new GsonBuilder().create();
	    		User user = gson.fromJson(json, User.class);
	    		users.add(user);
			}
		}
		return users;
	}
}
