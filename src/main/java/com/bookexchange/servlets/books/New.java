package com.bookexchange.servlets.books;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookexchange.mongodb.model.Book;
import com.bookexchange.util.JsonBookParser;

/**
 * Servlet implementation class New
 */
@WebServlet("/app/books/new")
public class New extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public New() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		if (id != null) {
			HttpSession httpSession = request.getSession();
			String booksString = (String)httpSession.getAttribute("books");
			
			ArrayList<Book> books = JsonBookParser.toBooks(booksString);
			
			for (int i = 0; i < books.size(); i++) {
				Book oneBook = books.get(i);
				if (oneBook.getId().equals(id)) {
					request.setAttribute("id", oneBook.getId());
					request.setAttribute("title", oneBook.getTitle());
					request.setAttribute("authors", oneBook.getAllAuthors());
					request.setAttribute("description", oneBook.getDescription());
					request.setAttribute("categories", oneBook.getAllCategories());
					request.setAttribute("language", oneBook.getLanguage());
					request.setAttribute("thumbnail", oneBook.getThumbnail());
					request.setAttribute("identifiers", oneBook.getAllIndustryIdentifiers());
					request.setAttribute("book",oneBook);
					break;
				}
			}
		}
		request.setCharacterEncoding("UTF-8");
		request.getRequestDispatcher("/books/new.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
