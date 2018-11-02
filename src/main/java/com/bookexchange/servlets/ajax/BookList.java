package com.bookexchange.servlets.ajax;

import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;

import com.bookexchange.mongodb.model.Book;
import com.bookexchange.mongodb.model.User;
import com.bookexchange.mongodb.util.MongoConnection;
import com.bookexchange.mongodb.util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class BookList
 */
@WebServlet("/app/booklist")
public class BookList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookList() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder buildHTML = new StringBuilder();
		String listType = "";
		
		// get ajax query parameter
		String classes = request.getParameter("classes");
		if (classes != null) {
			List<String> options = new ArrayList<String>(Arrays.asList(classes.split(" ")));
			if (options.contains("list-user")) listType = "user";
			if (options.contains("list-all")) listType = "all";
		}
		
		// build book list based on query
		MongoConnection mongo = MongoConnection.getInstance();
		MongoDatabase database = mongo.database;
		ArrayList<Book> books = new ArrayList<Book>();
		
		HttpSession session = request.getSession(false);
		
		switch (listType) {
			case "user":  
				books = buildUserBooks(session, database);
				break;
			case "all":
				books = buildAllBooks(session, database);
				break;
		}
		
		for (int i = 0; i < books.size(); i++) {
			Book oneBook = books.get(i);
			MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse (response);
			request.setAttribute("title", oneBook.getTitle());
			request.setAttribute("authors", oneBook.getAllAuthors());
			request.setAttribute("thumbnail", oneBook.getThumbnail());
			request.setAttribute("link", request.getContextPath() + "/app/books/" + oneBook.getId() + "/edit");
			request.getRequestDispatcher("/books/book.jsp").forward(request, mockHttpServletResponse);
			
			buildHTML.append(mockHttpServletResponse.getOutput());
		}

		response.getWriter().write(buildHTML.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private ArrayList<Book> buildUserBooks(HttpSession session, MongoDatabase database) {
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

	private ArrayList<Book> buildAllBooks(HttpSession session, MongoDatabase database) {
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
}
