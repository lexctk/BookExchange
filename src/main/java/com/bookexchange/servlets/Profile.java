package com.bookexchange.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookexchange.mongodb.model.User;
import com.bookexchange.mongodb.util.MongoConnection;
import com.bookexchange.mongodb.util.MongoUtil;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class Profile
 * 
 * Handles user profile page
 * 
 */
@WebServlet("/app/profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		MongoConnection mongo = MongoConnection.getInstance();
		MongoDatabase database = mongo.database;
		
		if (id == null || id.length() <= 0) {
			HttpSession session = request.getSession(false);
			User user = MongoUtil.getCurrentUser(session, database);
			request.setAttribute("user", user);
			request.setAttribute("visitor", false);
		} else {
			User user = MongoUtil.getOneUser(id, database);
			request.setAttribute("user", user);
			request.setAttribute("visitor", true);
		}
		request.getRequestDispatcher("/profile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
