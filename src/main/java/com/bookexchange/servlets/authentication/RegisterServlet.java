package com.bookexchange.servlets.authentication;

import java.io.IOException;

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
import com.bookexchange.util.AmazonS3Util;
import com.bookexchange.util.MiscUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
@MultipartConfig(fileSizeThreshold=1024*1024*10,maxFileSize=1024*1024*10,maxRequestSize=1024*1024*20)
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
		
		String locality = request.getParameter("locality");
		String country = request.getParameter("country");
		String lat = request.getParameter("lat");
		String lng = request.getParameter("lng");

		String avatarString = request.getParameter("userAvatar");
		
		// use bcrypt for password
		String password = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));    
	    
		MongoConnection mongo = MongoConnection.getInstance();
		MongoDatabase database = mongo.database;
		
		boolean isEmailFound = Util.searchEmail(email, database);
		
		if (isEmailFound) {
			request.setAttribute("message", "Email already registered, please sign in instead");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.jsp");
			requestDispatcher.forward(request, response);			
			
		} else {
			ObjectId _id = new ObjectId();

			String avatar = "";
			if (avatarString != null && !avatarString.isEmpty()) {
				try {
					Part filePart = request.getPart("userAvatar");
					avatar = AmazonS3Util.awsUpload(filePart, _id.toString());
				} catch (final Exception e) {
					System.out.println("Upload failed" + e.getMessage());
				}
			}
		    
			Document doc = new Document("_id", _id)
					.append("email", email)
					.append("password", password)
					.append("username", username)
					.append("registered", MiscUtil.nowToString());
			
			if (firstname != null && !firstname.isEmpty()) doc.append("firstname", firstname);
			if (lastname != null && !lastname.isEmpty()) doc.append("lastname", lastname);
			if (avatar != null && !avatar.isEmpty()) doc.append("avatar", avatar);
			
			Document locationDoc = new Document();
			if (locality != null && !locality.isEmpty()) locationDoc.append("locality", locality);
			if (country != null && !country.isEmpty()) locationDoc.append("country", country);
			if (lat != null && !lat.isEmpty()) locationDoc.append("lat", Double.parseDouble(lat));
			if (lng != null && !lng.isEmpty()) locationDoc.append("lng", Double.parseDouble(lng));
			
			doc.append("location", locationDoc);
					
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
