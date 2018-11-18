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
import com.bookexchange.mongodb.util.MongoUtil;
import com.bookexchange.util.SendGridMailer;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class Email
 */
@WebServlet("/app/email")
public class Email extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Email() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// sender is current user
		HttpSession session = request.getSession(false);
		MongoConnection mongo = MongoConnection.getInstance();
		MongoDatabase database = mongo.database;
		User user = MongoUtil.getCurrentUser(session, database);
		request.setAttribute("emailSender", user.getEmail());
		
		// get recipient from id
		String id = request.getParameter("id");
		System.out.println("do get id param is " + id);
		User recipient = MongoUtil.getOneUser(id, database);
		System.out.println("getOneUser name is " + recipient.getUsername());
		System.out.println("getOneUser id is " + recipient.get_id().toString());
		
		request.setAttribute("idRecipient", recipient.get_id().toString());
		request.setAttribute("nameRecipient", recipient.getUsername());
		
		String bookId = request.getParameter("bookId");
		if (bookId != null && bookId.length() > 0) {
			Book book = MongoUtil.getOneBook(bookId, database);
			request.setAttribute("bookId", book.getId());
			request.setAttribute("bookName", book.getTitle());
		}
		request.getRequestDispatcher("/email.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idRecipient = request.getParameter("idRecipient");
		String emailSubject = request.getParameter("emailSubject");
		String emailContent = request.getParameter("emailContent");
		
		System.out.println(idRecipient);
		System.out.println(emailSubject);
		System.out.println(emailContent);
		
		HttpSession session = request.getSession(false);
		MongoConnection mongo = MongoConnection.getInstance();
		MongoDatabase database = mongo.database;
		
		User sender = MongoUtil.getCurrentUser(session, database);
		User recipient = MongoUtil.getOneUser(idRecipient, database);
		
		SendGridMailer.sendEmail(sender.getEmail(), recipient.getEmail(), emailSubject, emailContent);
		response.sendRedirect(request.getContextPath() + "/app/dashboard");
	}

}
