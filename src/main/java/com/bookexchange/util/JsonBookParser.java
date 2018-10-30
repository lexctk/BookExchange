package com.bookexchange.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bookexchange.mongodb.model.Book;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class JsonBookParser {
	
    //Covert json string to ArrayList of Book objects
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
}
