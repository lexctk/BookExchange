package com.bookexchange.servlets.ajax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookexchange.mongodb.model.Book;
import com.bookexchange.mongodb.util.MongoConnection;
import com.bookexchange.mongodb.util.MongoUtil;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class BookList
 */
@WebServlet("/app/booklist")
public class BookList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookList() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder buildHTML = new StringBuilder();
		StringBuilder wrapperHTML = new StringBuilder();
		
		String listType = "";
		String urlType = "";
		String visitorId = "";
		
		boolean filter = false;
		
		// get ajax query parameter
		String classes = request.getParameter("classes");
		if (classes != null) {
			List<String> options = new ArrayList<String>(Arrays.asList(classes.split(" ")));
			if (options.contains("list-user")) listType = "user"; //display books belonging to current user
			if (options.contains("list-visitor")) {
				//display books belonging to another user
				listType = "visitor"; 
				for (String option : options) {
					if (option.startsWith("id-")) visitorId = option.substring(3);
				}
			}
			if (options.contains("list-all")) listType = "all"; //display all books in collection
			if (options.contains("list-recent")) listType = "recent";
			if (options.contains("list-filter")) filter = true;
		}
		
		// build book list based on query
		MongoConnection mongo = MongoConnection.getInstance();
		MongoDatabase database = mongo.database;
		ArrayList<Book> books = new ArrayList<Book>();
		
		HttpSession session = request.getSession(false);
		
		switch (listType) {
			case "user":  
				books = MongoUtil.buildUserBooks(session, database);
				urlType = "";  //urlType = "/edit";
				break;
			case "visitor":  
				books = MongoUtil.buildUserIdBooks(visitorId, database);
				urlType = "";  //urlType = "/edit";
				break;				
			case "all":
				books = MongoUtil.buildAllBooks(session, database);
				urlType = "";
				break;
			case "recent":
				books = MongoUtil.buildRecentBooks(session, database);
				urlType = "";
				break;				
		}

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse (response);
		request.setAttribute("books", books);
		request.setAttribute("linkBase", "/app/books");
		request.setAttribute("link", urlType);
		request.getRequestDispatcher("/books/book.jsp").forward(request, mockHttpServletResponse);
		buildHTML.append(mockHttpServletResponse.getOutput());
		
		if (filter) {
			ArrayList<String> categories = new ArrayList<String>();
			ArrayList<String> languages = new ArrayList<String>();
			
			for (int i = 0; i < books.size(); i++) {
				String[] categoryList = books.get(i).getVolumeInfo().getCategories();
				for (int j = 0; j< categoryList.length; j++) {
					if (categoryList[j].length() > 0) {
						String cat=categoryList[j].substring(0,1).toUpperCase() + categoryList[j].substring(1).toLowerCase();
						if (!categories.contains(cat)) categories.add(cat);
					}
				}
				String language = books.get(i).getLanguageName();
				if (!languages.contains(language)) languages.add(language);
			}
			
			mockHttpServletResponse = new MockHttpServletResponse (response);
			request.setAttribute("categories", categories);
			request.setAttribute("languages", languages);
			request.setAttribute("booklist", buildHTML.toString());
			request.getRequestDispatcher("/books/filters.jsp").forward(request, mockHttpServletResponse);
			wrapperHTML.append(mockHttpServletResponse.getOutput());
		} else {
			wrapperHTML.append(buildHTML.toString());
		}
				
		response.getWriter().write(wrapperHTML.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
