package com.bookexchange.mongodb.model;

public class VolumeInfo {
	private String title;
	private String [] authors;
	private String description;
	private String [] categories;
	private String language;
	private ImageLinks imageLinks;
	private IndustryIdentifiers [] industryIdentifiers;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String [] getAuthors() {
		return authors;
	}
	public void setAuthors(String [] authors) {
		this.authors = authors;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String [] getCategories() {
		return categories;
	}
	public void setCategories(String [] categories) {
		this.categories = categories;
	}
	public ImageLinks getImageLinks() {
		return imageLinks;
	}
	public void setImageLinks(ImageLinks imageLinks) {
		this.imageLinks = imageLinks;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public IndustryIdentifiers [] getIndustryIdentifiers() {
		return industryIdentifiers;
	}
	public void setIndustryIdentifiers(IndustryIdentifiers [] industryIdentifiers) {
		this.industryIdentifiers = industryIdentifiers;
	}
}
