package com.bookexchange.servlets.books;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookexchange.mongodb.model.Book;
import com.bookexchange.mongodb.model.User;
import com.bookexchange.mongodb.util.MongoConnection;
import com.bookexchange.mongodb.util.Util;
import com.bookexchange.util.JsonBookParser;
import com.mongodb.client.MongoDatabase;


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
			Book book = Util.getOneBook (id, database);
			request.setAttribute("id", id);
			request.setAttribute("book", book);
			
			if (book.getUserIDs() != null && book.getUserIDs().size() > 0) {
				ArrayList<User> users = new ArrayList<User>();
				users = Util.buildBookUsers(book.getUserIDs(), database);
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
		
		// add new book to database
		Book book = JsonBookParser.stringsToBook(
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
		User user = Util.getCurrentUser(session, database);

		Util.addBookToCollection(book, user, database);
		response.sendRedirect("profile");
	}	
}
