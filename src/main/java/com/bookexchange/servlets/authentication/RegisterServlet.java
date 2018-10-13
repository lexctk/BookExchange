package com.bookexchange.servlets.authentication;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.bookexchange.mongodb.util.MongoConnection;
import com.bookexchange.mongodb.util.Util;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/register.jsp");
		requestDispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// get request parameters for userID and password
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		MongoConnection mongo = MongoConnection.getInstance();
		MongoDatabase database = mongo.database;
		
		boolean isUserFound = Util.searchUser(email, password, database);

		
		if (isUserFound) {
			//TODO: display "user already exists message"
			response.sendRedirect("login");
			
		} else {
			
			Document doc = new Document("email", email).append("password", password);
			MongoCollection<Document> collection = Util.collection(database, "users");
			collection.insertOne(doc);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/books");
			requestDispatcher.forward(request, response);
		}

	}

}
