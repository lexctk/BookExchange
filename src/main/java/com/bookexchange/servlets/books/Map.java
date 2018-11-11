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
import com.bookexchange.mongodb.model.BookOwnerInformation;
import com.bookexchange.mongodb.model.Location;
import com.bookexchange.mongodb.model.User;
import com.bookexchange.mongodb.util.MongoConnection;
import com.bookexchange.mongodb.util.MongoUtil;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class Map
 */
@WebServlet("/app/books/map")
public class Map extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Map() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MongoConnection mongo = MongoConnection.getInstance();
		MongoDatabase database = mongo.database;
		
		ArrayList<Book> books = new ArrayList<Book>();
		HttpSession session = request.getSession(false);
		User currentUser = MongoUtil.getCurrentUser(session, database);
		
		books = MongoUtil.buildAllBooks(session, database); //excluding books owned by current user
		
		ArrayList<Location> locations = new ArrayList<Location>();
		ArrayList<String> titles = new ArrayList<String>();
		ArrayList<String> covers = new ArrayList<String>();
		ArrayList<String> authors = new ArrayList<String>();
		ArrayList<User> owners = new ArrayList<User>();
		
		// will be passed to google maps
		for (Book book : books) {
			if (book.getBookOwnerInformation() != null && book.getBookOwnerInformation().size() > 0) {
			
				for(BookOwnerInformation bookOwnerInformation : book.getBookOwnerInformation()) {
					Location loc = bookOwnerInformation.getLocation();
					boolean contains = false;
					for (Location location : locations) {
						if (location.getLat() == loc.getLat() && location.getLng() == loc.getLng()) {
							contains = true;
							break;
						}
					}
					if (contains) {
						loc.setLat(loc.getLat() + ((0.5-Math.random())/100));
						loc.setLng(loc.getLng() + ((0.5-Math.random())/100));
						locations.add(loc);
					} else locations.add(loc);
					
					titles.add(book.getTitle());
					owners.add(MongoUtil.getOneUser(bookOwnerInformation.getUserID(), database));
					covers.add(book.getThumbnail());
					authors.add(book.getAllAuthors());
				}
			}
		}
		
		request.setAttribute("userLat", currentUser.getLocation().getLat());
		request.setAttribute("userLng", currentUser.getLocation().getLng());
		
		request.setAttribute("locations", locations);
		request.setAttribute("owners", owners);
		request.setAttribute("titles", titles);
		request.setAttribute("covers", covers);
		request.setAttribute("authors", authors);
		
		request.getRequestDispatcher("/books/map.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
