package com.bookexchange.mongodb.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.bson.types.ObjectId;

public class User {

	private ObjectId _id;

	private String username;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private List<String> bookIDs;
	private String registered;

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	
	public List<String> getBookIDs() {
		if (bookIDs == null) return null;
		return Collections.unmodifiableList(bookIDs);
	}

	public void setBookIDs(List<String> bookIDs) {
		this.bookIDs = new ArrayList<String>(bookIDs);
	}
	
	public String getRegistered() {
		return registered;
	}

	public void setRegistered(String registered) {
		this.registered = registered;
	}	
	
	/**
	 * Adds a book id to the list of books. 
	 * 
	 * @param bookID id to be added to the list
	 * @return true if book was added, false if book already exists
	 */
	public boolean addBook (String bookID) {
		
		if (this.bookIDs == null) {
			this.bookIDs = new ArrayList<String>();
		}

		if (this.bookIDs.contains(bookID)) {
			System.out.println("contains");
			return false;
		}
		this.bookIDs.add(bookID);
		return true;
	}
	
	public String getRegisteredMonth() {
		LocalDate dateTime = LocalDate.parse(this.registered, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		DateTimeFormatter df_en = DateTimeFormatter.ofPattern("MMMM").withLocale(Locale.ENGLISH);
		return dateTime.format(df_en);
	}
	
	public String getRegisteredYear() {
		LocalDate dateTime = LocalDate.parse(this.registered, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		DateTimeFormatter df_en = DateTimeFormatter.ofPattern("yyyy").withLocale(Locale.ENGLISH);
		return dateTime.format(df_en);		
	}
}
