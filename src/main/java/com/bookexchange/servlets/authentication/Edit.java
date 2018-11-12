package com.bookexchange.servlets.authentication;

import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import java.util.List;

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
import org.bson.conversions.Bson;
import org.mindrot.jbcrypt.BCrypt;

import com.bookexchange.mongodb.model.User;
import com.bookexchange.mongodb.model.UserBookList;
import com.bookexchange.mongodb.util.MongoConnection;
import com.bookexchange.mongodb.util.MongoUtil;
import com.bookexchange.util.AmazonS3Util;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

/**
 * Servlet implementation class EditServlet
 */
@WebServlet("/app/profile/edit")
@MultipartConfig(fileSizeThreshold=1024*1024*10,maxFileSize=1024*1024*10,maxRequestSize=1024*1024*20)
public class Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Edit() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		MongoConnection mongo = MongoConnection.getInstance();
		MongoDatabase database = mongo.database;
		
		User user = MongoUtil.getCurrentUser(session, database);
		request.setAttribute("user", user);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/edit.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// get request parameters for userID and password
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String plainPassword = request.getParameter("password");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		
		String locality = request.getParameter("locality");
		String country = request.getParameter("country");
		String lat = request.getParameter("lat");
		String lng = request.getParameter("lng");
		
		HttpSession session = request.getSession(false);
		MongoConnection mongo = MongoConnection.getInstance();
		MongoDatabase database = mongo.database;
		User currentUser = MongoUtil.getCurrentUser(session, database);
		
		Document updateUser = new Document();
		if (username != null && !username.isEmpty() && !username.equals(currentUser.getUsername())) 
			updateUser.append("username", username);
		
		if (email != null && !email.isEmpty() && !email.equals(currentUser.getEmail())) 
			updateUser.append("email", email);
		
		if (firstname != null && !firstname.isEmpty() && !firstname.equals(currentUser.getFirstname())) 
			updateUser.append("firstname", firstname);
		
		if (lastname != null && !lastname.isEmpty() && !lastname.equals(currentUser.getLastname())) 
			updateUser.append("lastname", lastname);
		
		if (plainPassword != null && !plainPassword.isEmpty()) {
			String password = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12)); 
			updateUser.append("password", password);
		}
		
		String avatar = "";
		try {
			Part filePart = request.getPart("userAvatar");
			if (filePart.getSize() > 0) {
				avatar = AmazonS3Util.awsUpload(filePart, currentUser.get_id().toString());
			}
		} catch (final Exception e) {
			System.out.println("Upload failed" + e.getMessage());
		}
		if (avatar.length() > 0) updateUser.append("avatar", avatar);
		
		if ((lat != null && !lat.isEmpty() && Double.parseDouble(lat) != currentUser.getLocation().getLat()) ||
				(lng != null && !lng.isEmpty() && Double.parseDouble(lng) != currentUser.getLocation().getLng())) {
			
			Document locationDoc = new Document()
	    			.append("locality", locality)
	    			.append("country", country)
	    			.append("lat", lat)
	    			.append("lng", lng);
			updateUser.append("location", locationDoc);
			
			//update all books locations
			List<UserBookList> userBookList = currentUser.getUserBookList();
			if (userBookList != null && userBookList.size() > 0) {
				
				MongoCollection<Document> collection = database.getCollection("books");
				
				for (UserBookList userBook : userBookList) {
					Bson filter = Filters.and(Filters.eq( "id", userBook.getBookID()), Filters.eq("bookOwnerInformation.userID", currentUser.get_id().toString()));
					Bson setUpdate = Updates.set("bookOwnerInformation.$.location", locationDoc);
					collection.updateOne(filter, setUpdate);
				}
			}
		}
		MongoCollection<Document> collection = database.getCollection("users");
		
		collection.updateOne(
    			eq("_id", currentUser.get_id()),
    			new Document("$set", updateUser));
		

		//get the old session and invalidate
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
		
        //generate a new session
        HttpSession newSession = request.getSession(true);
        
        newSession.setAttribute("username", username);
        newSession.setAttribute("_id", currentUser.get_id());
        
        //setting session to expire
        newSession.setMaxInactiveInterval(15*60);

        Cookie cookie = new Cookie("username", username);
        response.addCookie(cookie);	
        
        response.sendRedirect(request.getContextPath() + "/app/profile");
	}
}
