package com.bookexchange.servlets;

import java.io.IOException;

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.getRequestDispatcher("/books.jsp").forward(request, response);
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
		
		User user = new User ();
		
        HttpSession session = request.getSession(false);
        if (session != null) {
            user.setEmail((String) session.getAttribute("email"));
            user.setUsername((String) session.getAttribute("username"));
            user.setFirstname((String) session.getAttribute("firstname"));
            user.setLastname((String) session.getAttribute("lastname"));
        }		
   
		Util.addBooktoCollection(book, user, database);
		
		response.sendRedirect("app/profile");
	}
}
