package com.bookexchange.servlets.authentication;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		String username = request.getParameter("username");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");

		MongoConnection mongo = MongoConnection.getInstance();
		MongoDatabase database = mongo.database;
		
		boolean isEmailFound = Util.searchEmail(email, database);

		
		if (isEmailFound) {
			request.setAttribute("message", "Email already registered");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.jsp");
			requestDispatcher.forward(request, response);			
			
		} else {
			Document doc = new Document("email", email).append("password", password)
					.append("username", username).append(firstname, firstname).append(lastname, lastname);
			MongoCollection<Document> collection = Util.collection(database, "users");
			
			collection.insertOne(doc);
			
			//get the old session and invalidate
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
			
            //generate a new session
            HttpSession newSession = request.getSession(true);
            
            newSession.setAttribute("username", username);
            
            //setting session to expire
            newSession.setMaxInactiveInterval(15*60);

            Cookie cookie = new Cookie("username", username);
            response.addCookie(cookie);	
            
            response.sendRedirect("app/books");
		}

	}

}
