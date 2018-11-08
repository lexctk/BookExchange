package com.bookexchange.mongodb.model;

public class UserIdsDates {
	
	private String userID;
	private String dateAdded;
	
	public UserIdsDates () {
	}
	
	public String getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
}
