package com.bookexchange.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bookexchange.mongodb.model.Book;
import com.bookexchange.mongodb.model.ImageLinks;
import com.bookexchange.mongodb.model.IndustryIdentifiers;
import com.bookexchange.mongodb.model.VolumeInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class JsonParser {
	
    // Covert json string to ArrayList of Book objects
	// (Gson doesn't work with ArrayList)
    public static ArrayList<Book> toBooks(String jsonString) {
        try {   	
	    	// Parse the JSON response.
	    	JSONObject jsonResponse = new JSONObject(jsonString);
	    	
	    	if (jsonResponse.has("error") && !jsonResponse.isNull("error")) {
	    		//TODO: IMPORTANT: grab error message and translate to a user-friendly 404 response
	    		return null;
	    	}
	    	JSONArray jsonArray = jsonResponse.getJSONArray("items");
	    	ArrayList<Book> books = new ArrayList<Book>();
	    	
	    	for (int i = 0; i < jsonArray.length(); i++) {
	    		JSONObject jsonObject = jsonArray.getJSONObject(i);
	    		
	    		Gson gson = new GsonBuilder().create();
	    		Book book = gson.fromJson(jsonObject.toString(), Book.class);
	    		books.add(book);
	    	}
	    	return books; 
        }
        catch(JsonSyntaxException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * 
     * Parser html forms -> book. //TODO: handle null values and exceptions
     * 
     * @param id
     * @param title
     * @param authors: authors separated by comma, ignores leading spaces
     * @param description
     * @param categories: categories separated by comma, ignores leading spaces
     * @param language
     * @param thumbnail
     * @param identifiers <type0>:<identifier0>,<type1>:<identifier1>...
     * @return
     * @throws MalformedURLException
     */
    public static Book stringsToBook (
    		String id, 
    		String title, 
    		String authors, 
    		String description, 
    		String categories, 
    		String language, 
    		String thumbnail,
    		String identifiers) throws MalformedURLException {
    	
		Book book = new Book ();
		VolumeInfo volumeInfo = new VolumeInfo ();
		ImageLinks imageLinks = new ImageLinks ();
		
		book.setId(id);
		
		volumeInfo.setTitle(title);
		String[] authorsParse = authors.split(",\\s*");
		volumeInfo.setAuthors(authorsParse);
		volumeInfo.setDescription(description);
		String[] categoriesParse = categories.split(",\\s*");
		volumeInfo.setCategories(categoriesParse);
		volumeInfo.setLanguage(language);
		URL url = new URL(thumbnail);
		
		imageLinks.setThumbnail(url.toString());
		volumeInfo.setImageLinks(imageLinks);
		
		String[] identifiersParse = identifiers.split(",\\s*");

		IndustryIdentifiers[] industryIdentifiers = new IndustryIdentifiers[identifiersParse.length];
		
		for (int i = 0; i < identifiersParse.length; i++) {
			industryIdentifiers[i] = new IndustryIdentifiers();
			industryIdentifiers[i].setType(identifiersParse[i].split(":")[0]);
			industryIdentifiers[i].setIdentifier(identifiersParse[i].split(":")[1]);
		}
		
		volumeInfo.setIndustryIdentifiers(industryIdentifiers);
		book.setVolumeInfo(volumeInfo);
		
		return book;
    }
}
