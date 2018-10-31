package com.bookexchange.mongodb.model;

public class IndustryIdentifiers {
	private String type;
	private String identifier;
	
	public IndustryIdentifiers () {
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

}
