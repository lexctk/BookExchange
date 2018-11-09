package com.bookexchange.mongodb.model;

public class BookOwnerInformation {
	
	private String userID;
	private String dateAdded;
	private Location location;
	
	public BookOwnerInformation () {
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
}
