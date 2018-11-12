package com.bookexchange.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bookexchange.mongodb.model.Book;
import com.bookexchange.mongodb.model.Event;
import com.bookexchange.mongodb.model.ImageLinks;
import com.bookexchange.mongodb.model.IndustryIdentifiers;
import com.bookexchange.mongodb.model.Location;
import com.bookexchange.mongodb.model.VolumeInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class JsonParser {
	
    /**
     * Covert a json string to ArrayList of Book objects using Gson
     * 
     * @param jsonString
     * @return ArrayList<Book>
     */
    public static ArrayList<Book> toBooks(String jsonString) {
        try {   	
	    	// Parse the JSON response.
	    	JSONObject jsonResponse = new JSONObject(jsonString);
	    	
	    	if (jsonResponse.has("error") && !jsonResponse.isNull("error")) {
	    		return null;
	    	}
	    	if (!jsonResponse.has("items")) {
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
     * Covert a json string to ArrayList of Event objects using Gson
     * 
     * @param jsonString
     * @return ArrayList<Event>
     */
    public static ArrayList<Event> toEvents(String jsonString) {
        try {   	
	    	// Parse the JSON response.
	    	JSONObject jsonResponse = new JSONObject(jsonString);
	    	
	    	if (jsonResponse.has("error") && !jsonResponse.isNull("error")) {
	    		return null;
	    	}
	    	if (!jsonResponse.has("records")) {
	    		return null;
	    	}
	    	JSONArray jsonArray = jsonResponse.getJSONArray("records");
	    	ArrayList<Event> events = new ArrayList<Event>();
	    	
	    	for (int i = 0; i < jsonArray.length(); i++) {
	    		JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject("fields");
	    		
	    		Gson gson = new GsonBuilder().create();
	    		Event event = gson.fromJson(jsonObject.toString(), Event.class);
	    		
	    		Location location = new Location ();
	    		JSONArray latlon = jsonObject.getJSONArray("latlon");
	    		location.setLat(latlon.getDouble(0));
	    		location.setLng(latlon.getDouble(1));
	    		event.setLocation(location);
	    		
	    		events.add(event);
	    	}
	    	return events; 
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
    		String identifiers) {
    	
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
		
		try {
			URL url;
			url = new URL(thumbnail);
			imageLinks.setThumbnail(url.toString());
			volumeInfo.setImageLinks(imageLinks);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
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
