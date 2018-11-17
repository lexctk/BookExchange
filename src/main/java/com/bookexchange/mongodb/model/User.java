package com.bookexchange.mongodb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.types.ObjectId;

import com.bookexchange.util.MiscUtil;

public class User {

	private ObjectId _id;

	private String username;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private String registered;
	private String avatar;
	private Location location;
	private List<UserBookList> userBookList;

	public User() {
	}
	
	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
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
	
	public String getRegistered() {
		return registered;
	}

	public void setRegistered(String registered) {
		this.registered = registered;
	}	

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<UserBookList> getUserBookList() {
		if (userBookList == null) return null;
		return Collections.unmodifiableList(userBookList);
	}

	public void setUserBookList(List<UserBookList> userBookList) {
		this.userBookList = new ArrayList<UserBookList>(userBookList);
	}
	
	/**
	 * Adds a book id to the list of books. 
	 * 
	 * @param bookID id to be added to the list
	 * @return true if book was added, false if book already exists
	 */
	public boolean addBook (String bookID) {
		
		// if list of books doesn't exist, create it
		if (this.userBookList == null) {
			this.userBookList = new ArrayList<UserBookList>();
		}
		
		//book already in list
		if (this.userBookList.stream().filter(o -> o.getBookID().equals(bookID)).findFirst().isPresent()) {
			return false;
		}
		UserBookList userBookList = new UserBookList();
		userBookList.setBookID(bookID);
		userBookList.setDateAdded(MiscUtil.nowToString());
		
		this.userBookList.add(userBookList);
		return true;
	}
	
	
	/**
	 * Get registration month
	 * 
	 * @return month as string, in English (January, February...)
	 */
	public String getRegisteredMonth() {
		return MiscUtil.getRegisteredMonth(this.registered);
	}
	
	/**
	 * Get registration year
	 * 
	 * @return year as 'YYYY' format
	 */
	public String getRegisteredYear() {
		return MiscUtil.getRegisteredYear(this.registered);	
	}
	
	public String getIdString () {
		return this._id.toString();
	}
}
