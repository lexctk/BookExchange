package com.bookexchange.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
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
 * Servlet implementation class SearchAPI
 */
@WebServlet("/app/searchapi")
public class SearchAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String baseAPI = "https://www.googleapis.com/books/v1/volumes?q=";
	private static final String fieldsAPI = "&fields=items(id,volumeInfo/title,volumeInfo/authors,volumeInfo/description,volumeInfo/industryIdentifiers,volumeInfo/categories,volumeInfo/language,volumeInfo/imageLinks)";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String query = request.getParameter("query");
		if (query == null || query.length() <= 0) {
			return;
		}
		String json = getJSONString (query);
		
		ArrayList<Book> books = JsonBookParser.toBooks(json);
		
		StringBuilder buildHTML = new StringBuilder();
		buildHTML.append("<div class='row'>");
		
		for (int i = 0; i < books.size(); i++) {
			Book oneBook = books.get(i);
			MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse (response);
			request.setAttribute("title", oneBook.getTitle());
			request.setAttribute("authors", oneBook.getAllAuthors());
			request.setAttribute("thumbnail", oneBook.getThumbnail());
			request.setAttribute("link", request.getContextPath() + "/app/books/new?id=" + oneBook.getId());
			request.getRequestDispatcher("/books/book.jsp").forward(request, mockHttpServletResponse);
			
			buildHTML.append(mockHttpServletResponse.getOutput());
		}
		
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("books", json);
		
		buildHTML.append("</div>");
		//TODO: Google only returns 10 results but with indexing, it's possible to get more. 
		buildHTML.append("<div class=\"spacer d-flex justify-content-center\">");
		buildHTML.append("<button class=\"btn btn-light btn-lg btn-load-more\">Load More</button>");
		buildHTML.append("</div>");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(buildHTML.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private static String getJSONString (String query) throws RuntimeException, UnsupportedEncodingException {
		
		StringBuilder json = new StringBuilder(); 

		query = URLEncoder.encode(query, "utf-8");
		query = baseAPI + query + fieldsAPI;
		
		HttpURLConnection conn = null;
        BufferedReader reader = null;
        
        try {  
            URL url = new URL(query);  
            
            conn = (HttpURLConnection)url.openConnection();  
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("key","AIzaSyCJqEl8gyfcPFsK5Lk2_o6Zvqcun5ERGcQ");
            
            if (conn.getResponseCode() != 200) {
                return ("{\"error\": \"Google Books API doesn't respond\"");
            }
            
	        reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
	        
            String output = null;  
            while ((output = reader.readLine()) != null)  
            	json.append(output);  
        } catch(MalformedURLException e) {
            e.printStackTrace();
            return ("{\"error\": \"Try again\"");
        } catch(IOException e) {  
            e.printStackTrace();
            return ("{\"error\": \"Something went wrong\"");
        }
        finally {
            if(reader!=null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null) {
                conn.disconnect();
            }
        }        
        		
		return json.toString();
	}
}
