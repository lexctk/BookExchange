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
import javax.servlet.http.HttpSession;

import com.bookexchange.mongodb.util.MongoConnection;
import com.bookexchange.mongodb.util.MongoUtil;
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

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.jsp");
		requestDispatcher.forward(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// get request parameters for userID and password
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		MongoConnection mongo = MongoConnection.getInstance();
		MongoDatabase database = mongo.database;
	
		Document isUserFound = MongoUtil.authenticateUser(email.toLowerCase(), password, database);
		
		if (isUserFound != null) {
			
			//get the old session and invalidate
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            //generate a new session
            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("username", isUserFound.get("username"));
            newSession.setAttribute("_id", isUserFound.get("_id"));
            
            //setting session to expire
            newSession.setMaxInactiveInterval(15*60);

            Cookie cookie = new Cookie("username", (String) isUserFound.get("username"));
            response.addCookie(cookie);
            
			response.sendRedirect("app/dashboard");
			
		} else {
			
			request.setAttribute("message", "Email or password is wrong");
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.jsp");
			requestDispatcher.forward(request, response);
		}

	}

}

