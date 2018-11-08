package com.bookexchange.mongodb.model;

public class BookIdsDates {
	
	private String bookID;
	private String dateAdded;
	
	public BookIdsDates () {
	}
	
	public String getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}
	
}
