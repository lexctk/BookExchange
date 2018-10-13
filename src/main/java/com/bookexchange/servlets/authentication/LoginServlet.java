package com.bookexchange.servlets.authentication;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
	
		boolean isUserFound = Util.searchUser(email, password, database);

		
		if (isUserFound) {
			response.sendRedirect("/books");
			
		} else {
			
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/index.jsp");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>Email or password is wrong.</font>");
			requestDispatcher.include(request, response);
		}

	}

}

