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
import com.bookexchange.mongodb.model.UserBookList;
import com.bookexchange.mongodb.model.User;
import com.bookexchange.util.MiscUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * @author lexa
 *
 */
public class MongoUtil {

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
	
	/**
	 * Get one book based on id
	 * @param id
	 * @param database
	 * @return Book object
	 */
	public static Book getOneBook (String id, MongoDatabase database) {
		MongoCollection<Document> collection = database.getCollection("books");
		
		Document bookDoc = collection.find(eq("id", id)).first();
	
		String json = bookDoc.toJson();
		Gson gson = new GsonBuilder().create();
		Book book = gson.fromJson(json, Book.class);

		return book;
	}
	
	/**
	 * Get one user based on id
	 * @param id
	 * @param database
	 * @return User object
	 */
	public static User getOneUser (String id, MongoDatabase database) {
		MongoCollection<Document> collection = database.getCollection("users");
		
		ObjectId _id = new ObjectId(id);
		Document userDoc = collection.find(eq("_id", _id)).first();
	
		String json = userDoc.toJson();
		Gson gson = new GsonBuilder().create();
		User user = gson.fromJson(json, User.class);
		
		// for some reason gson doesn't parse _id
		user.set_id(_id);

		return user;
	}	
	
	/**
	 * Add a book to the database and to a user's collection.
	 * 
	 * If the book already exists in the database, only add user to the 
	 * list of users that own the book and add book to the list of books that user owns.
	 * 
	 * @param book
	 * @param user
	 * @param database
	 * @return true if added successfully, false otherwise
	 */
	public static boolean addBookToCollection (Book book, User user, MongoDatabase database) {
		boolean bookAdded = false;
		
		MongoCollection<Document> collection = database.getCollection("books");
		
		Document findBook = collection.find(eq("id", book.getId())).first();
		if (findBook != null) {
			
			// this book already exists in collection, update book owners list
	       	Document doc = new Document ();
        	doc.append("userID", user.get_id().toString());
        	doc.append("dateAdded", MiscUtil.nowToString());
        	
        	Document locationDoc = new Document()
        			.append("locality", user.getLocation().getLocality())
        			.append("country", user.getLocation().getCountry())
        			.append("lat", user.getLocation().getLat())
        			.append("lng", user.getLocation().getLng());
        	doc.append("location", locationDoc);
        	
        	collection.updateOne(
        			eq("id", book.getId()),
        			new Document("$push", new Document("bookOwnerInformation", doc)));
        	
        	// add book to user's list
        	bookAdded = addBookToUser (book, user, database);
        	
		} else {
			// it's a new book, add to collection update user ownership and book ownership
			if (book.addUser(user.get_id().toString(), user.getLocation())) {
				
				// true: insert book in collection
				Gson gson = new Gson();
		        String json = gson.toJson(book);
		        Document document = Document.parse(json);
		        collection.insertOne(document);
		        
		        bookAdded = addBookToUser (book, user, database);
		        
			} else {
				//user already owns this book, don't add it. 
				//TODO: user message!
				return false;
			}

		}
		return bookAdded;
	}
	
	/**
	 * Add a book to the list of books a user owns
	 * @param book
	 * @param user
	 * @param database
	 * @return true if added successfully
	 */
	public static boolean addBookToUser (Book book, User user, MongoDatabase database) {
		boolean bookAdded = false;

        MongoCollection<Document> collection = database.getCollection("users");
        Document findUser = collection.find(eq("_id", user.get_id())).first();
        
        if (findUser == null) {
        	//TODO: handle error: this is an exception, user should be logged in so should exist in the database!
        } else {
        	Document doc = new Document ();
        	doc.append("bookID", book.getId());
        	doc.append("dateAdded", MiscUtil.nowToString());
        	collection.updateOne(
        			eq("_id", user.get_id()),
        			new Document("$push", new Document("userBookList", doc)));
        	bookAdded = true;
        }
        return bookAdded;
	}
	
	/**
	 * Build a lit of books the current user owns. 
	 * @param session
	 * @param database
	 * @return list of books
	 */
	public static ArrayList<Book> buildUserBooks(HttpSession session, MongoDatabase database) {
		
		ArrayList<Book> books = new ArrayList<Book>();
		MongoCollection<Document> collection = database.getCollection("books");
		
		User user = getCurrentUser(session, database);
		
		if (user.getUserBookList() != null) {
			for(UserBookList userBookList : user.getUserBookList()) {
				
				FindIterable<Document> it = collection.find(eq("id", userBookList.getBookID()));
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

	public static ArrayList<Book> buildUserIdBooks(String id, MongoDatabase database) {
		
		ArrayList<Book> books = new ArrayList<Book>();
		MongoCollection<Document> collection = database.getCollection("books");
		
		User user = getOneUser(id, database);
		
		if (user.getUserBookList() != null) {
			for(UserBookList userBookList : user.getUserBookList()) {
				
				FindIterable<Document> it = collection.find(eq("id", userBookList.getBookID()));
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
	
	/**
	 * Build a list of all the books in the database, excluding books current user owns
	 * @param session
	 * @param database
	 * @return list of books
	 */
	public static ArrayList<Book> buildAllBooks(HttpSession session, MongoDatabase database) {
		ArrayList<Book> books = new ArrayList<Book>();
		MongoCollection<Document> collection = database.getCollection("books");
		
		BasicDBObject query = new BasicDBObject();
		
		//exclude books current user owns
		User user = getCurrentUser(session, database);
		if (user.getUserBookList() != null) {
			List<String> bookIDs = new ArrayList<>(user.getUserBookList().size());
			for (UserBookList userBookList : user.getUserBookList()) {
				bookIDs.add(userBookList.getBookID());
			}
			query.put("id", new BasicDBObject("$nin", bookIDs));
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

}
