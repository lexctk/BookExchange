package com.bookexchange.servlets;

import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import java.util.ArrayList;

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
		
		HttpSession session = request.getSession(false);
		
		MongoConnection mongo = MongoConnection.getInstance();
		MongoDatabase database = mongo.database;
		
		// TODO: DRY!
		MongoCollection<Document> collection = database.getCollection("books");
		ArrayList<Book> books = new ArrayList<Book>();
		
		User user = Util.getCurrentUser(session, database);
		if (user.getBookIDs() != null) {
			for(String bookID : user.getBookIDs()){
				FindIterable<Document> it = collection.find(eq("id", bookID));
				for(Document doc : it) {
					String json = doc.toJson();
					Gson gson = new GsonBuilder().create();
		    		Book book = gson.fromJson(json, Book.class);
		    		books.add(book);
				}
			}
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
		
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse (response);
		request.setAttribute("thumbnail", request.getContextPath() + "/resources/images/new_book.png");
		request.setAttribute("title", "Add new");
		request.setAttribute("authors", "");
		request.setAttribute("link", request.getContextPath() + "/app/books/newsearch");
		request.getRequestDispatcher("/books/book.jsp").forward(request, mockHttpServletResponse);
		buildHTML.append(mockHttpServletResponse.getOutput());
		
		response.getWriter().write(buildHTML.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
