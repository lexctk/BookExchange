package com.bookexchange.servlets.authentication;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;

import com.bookexchange.mongodb.util.MongoConnection;
import com.bookexchange.mongodb.util.Util;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
@MultipartConfig
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/register.jsp");
		requestDispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// get request parameters for userID and password
		String email = request.getParameter("email");
		String plainPassword = request.getParameter("password");
		String username = request.getParameter("username");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		
		String password = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
		
		Part filePart = request.getPart("userAvatar"); 
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); 
	    InputStream fileContent = filePart.getInputStream();

		MongoConnection mongo = MongoConnection.getInstance();
		MongoDatabase database = mongo.database;
		
		boolean isEmailFound = Util.searchEmail(email, database);

		
		if (isEmailFound) {
			request.setAttribute("message", "Email already registered, please sign in instead");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.jsp");
			requestDispatcher.forward(request, response);			
			
		} else {
			ObjectId _id = new ObjectId();
			LocalDateTime now = LocalDateTime.now();
			
			Document doc = new Document("_id", _id).append("email", email).append("password", password)
					.append("username", username).append("firstname", firstname).append("lastname", lastname)
					.append("registered", now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
			MongoCollection<Document> collection = database.getCollection("users");
			collection.insertOne(doc);

			//get the old session and invalidate
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
			
            //generate a new session
            HttpSession newSession = request.getSession(true);
            
            newSession.setAttribute("username", username);
            newSession.setAttribute("_id", _id);
            
            //setting session to expire
            newSession.setMaxInactiveInterval(15*60);

            Cookie cookie = new Cookie("username", username);
            response.addCookie(cookie);	
            
            response.sendRedirect("app/dashboard");
		}

	}

}
