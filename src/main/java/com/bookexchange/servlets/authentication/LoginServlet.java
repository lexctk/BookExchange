package com.bookexchange.servlets.authentication;

import java.io.IOException;

import org.bson.Document;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookexchange.mongodb.util.MongoConnection;
import com.bookexchange.mongodb.util.Util;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// get request parameters for userID and password
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		MongoConnection mongo = MongoConnection.getInstance();
		MongoDatabase database = mongo.database;
	
		Document isUserFound = Util.searchUser(email.toLowerCase(), password, database);

		
		if (isUserFound != null) {
			request.setAttribute("username", isUserFound.get("username"));
			request.setAttribute("firstname", isUserFound.get("firstname"));
			request.setAttribute("lastname", isUserFound.get("lastname"));
			
			Cookie cookie = new Cookie("email", (String) isUserFound.get("email"));
            response.addCookie(cookie);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/books");
			requestDispatcher.forward(request, response);			
			
		} else {
			
			request.setAttribute("message", "Email or password is wrong");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
			requestDispatcher.forward(request, response);
		}

	}

}

