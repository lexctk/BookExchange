package com.bookexchange.servlets.books;

import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.bookexchange.mongodb.model.Book;
import com.bookexchange.mongodb.model.BookOwnerInformation;
import com.bookexchange.mongodb.model.User;
import com.bookexchange.mongodb.util.MongoConnection;
import com.bookexchange.mongodb.util.MongoUtil;
import com.bookexchange.util.JsonParser;
import com.bookexchange.util.MiscUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;


/**
 * Servlet implementation class Books
 */
@WebServlet("/app/books")
public class Books extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Books() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if (id == null || id.length() <= 0) {
			//no id, show all books
			request.getRequestDispatcher("/books/books.jsp").forward(request, response);
		} else {
			//has id, show one book and users offering it
			MongoConnection mongo = MongoConnection.getInstance();
			MongoDatabase database = mongo.database;
			Book book = MongoUtil.getOneBook (id, database);
			request.setAttribute("id", id);
			request.setAttribute("book", book);
			
			HttpSession session = request.getSession(false);
			User currentUser = MongoUtil.getCurrentUser(session, database);
			
			if (book.getBookOwnerInformation() != null && book.getBookOwnerInformation().size() > 0) {
				
				List<BookOwnerInformation> bookOwnerInformationList = book.getBookOwnerInformation();
				ArrayList<User> users = new ArrayList<User>();
				ArrayList<String> dates = new ArrayList<String>();
				MongoCollection<Document> collection = database.getCollection("users");
				boolean isOwner = false;
				
				for(BookOwnerInformation bookOwnerInformation : bookOwnerInformationList) {
					ObjectId objectId = new ObjectId(bookOwnerInformation.getUserID());
					FindIterable<Document> it = collection.find(eq("_id", objectId));
					for(Document doc : it) {
						
						String json = doc.toJson();
						Gson gson = new GsonBuilder().create();
			    		User user = gson.fromJson(json, User.class);
			    		// Gson doesn't parse ObjectId types correctly, update value manually:
			    		user.set_id(objectId);
			    		
			    		users.add(user);
			    		dates.add(MiscUtil.getDateDifference(bookOwnerInformation.getDateAdded()));
			    		
			    		if (currentUser.get_id().equals(user.get_id())) isOwner = true;
					}
				}
				request.setAttribute("isOwner", isOwner);
				request.setAttribute("dates", dates);
				request.setAttribute("users", users);
			}
			request.getRequestDispatcher("/books/book-single.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String method = request.getParameter("_method");
		
		if (method == null || method.length() <= 0) {
			// add new book to database
			Book book = JsonParser.stringsToBook(
					request.getParameter("id"), 
					request.getParameter("title"), 
					request.getParameter("authors"), 
					request.getParameter("description"), 
					request.getParameter("categories"), 
					request.getParameter("language"), 
					request.getParameter("thumbnail"), 
					request.getParameter("identifiers")
					);
			
			MongoConnection mongo = MongoConnection.getInstance();
			MongoDatabase database = mongo.database;
			
			HttpSession session = request.getSession(false);
			User user = MongoUtil.getCurrentUser(session, database);
			
			MongoUtil.addBookToCollection(book, user, database);
			response.sendRedirect("profile");
			
		} else if (method.equalsIgnoreCase("DELETE")) {
			// delete book from database
			
			String bookId = request.getParameter("id");
			
			MongoConnection mongo = MongoConnection.getInstance();
			MongoDatabase database = mongo.database;
			
			HttpSession session = request.getSession(false);
			User user = MongoUtil.getCurrentUser(session, database);
			Book book = MongoUtil.getOneBook(bookId, database);
			
			List<BookOwnerInformation> bookOwnerInformationList = book.getBookOwnerInformation();
			
			for(BookOwnerInformation bookOwnerInformation : bookOwnerInformationList) {
				ObjectId objectId = new ObjectId(bookOwnerInformation.getUserID());
				
				if (objectId.equals(user.get_id())) {
					// current user is owner and has permission to delete
					MongoCollection<Document> collection;
					Bson update;
					
					// remove book from user's list of books
					update = Updates.pull("userBookList",
							new Document("bookID", book.getId())
					);
					collection = database.getCollection("users");
					collection.updateOne(eq("_id", user.get_id()), update);
					
					// remove user from book owners
					if (bookOwnerInformationList.size() > 1) {
						// book has other owners, delete user from owner list
						update = Updates.pull("bookOwnerInformation",
								new Document("userID", user.get_id().toString())
						);
						collection = database.getCollection("books");
						collection.updateOne(eq("id", book.getId()), update);
					} else {
						// there are no other owners, delete book
						collection = database.getCollection("books");
						collection.deleteOne(new Document("id", book.getId()));
					}
					
					break;
				}
			}
			
			response.sendRedirect("profile");
		}
	}	
}
