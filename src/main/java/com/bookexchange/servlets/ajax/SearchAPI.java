package com.bookexchange.servlets.ajax;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookexchange.mongodb.model.Book;
import com.bookexchange.util.JsonParser;
import com.bookexchange.util.MiscUtil;

/**
 * Servlet implementation class SearchAPI
 */
@WebServlet("/app/searchapi")
public class SearchAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String baseAPI = "https://www.googleapis.com/books/v1/volumes?q=";
	private static final String fieldsAPI = "&fields=items(id,volumeInfo/title,volumeInfo/authors,volumeInfo/description,volumeInfo/industryIdentifiers,volumeInfo/categories,volumeInfo/language,volumeInfo/imageLinks)&maxResults=12";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchAPI() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String query = request.getParameter("query");
		String index = request.getParameter("index");

		if (query == null || query.length() <= 0) {
			return;
		}
		String json = MiscUtil.getJSONString (query, index, baseAPI, fieldsAPI, "key", "GoogleBooksAPIKey");
		
		ArrayList<Book> books = JsonParser.toBooks(json);
		
		if (books == null) {
			StringBuilder buildHTML = new StringBuilder();
			MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse (response);
			request.setAttribute("message", "No results found");
			request.setAttribute("origin", "google");
			request.getRequestDispatcher("/404.jsp").forward(request, mockHttpServletResponse);
			buildHTML.append(mockHttpServletResponse.getOutput());
			
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(buildHTML.toString());
			return;
		}
		
		StringBuilder buildHTML = new StringBuilder();
		buildHTML.append("<div class='row'>");
		
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse (response);
		request.setAttribute("books", books);
		request.setAttribute("linkBase", "/app/books");
		request.setAttribute("link", "/new");
		request.getRequestDispatcher("/books/book.jsp").forward(request, mockHttpServletResponse);
		buildHTML.append(mockHttpServletResponse.getOutput());
		
		// store json in session to retrieve in /app/books/new
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("books", json);
		
		buildHTML.append("</div>");
		
		if (Integer.parseInt(index) < 108) { 
			buildHTML.append("<div class=\"spacer d-flex justify-content-center\">");
			buildHTML.append("<button id=\"searchAPILoadNext\" class=\"btn btn-light btn-lg btn-gradient btn-load-more\" onclick=\"loadNextBooks()\">Load Next</button>");
			buildHTML.append("</div>");
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(buildHTML.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
