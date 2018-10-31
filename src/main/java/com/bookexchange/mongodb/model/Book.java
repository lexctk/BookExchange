package com.bookexchange.mongodb.model;

import java.util.Objects;

/**
 * 
 * Book model is split up to match API format, in order to be compatible with 
 * GSON functionality (makes parsing easier)
 *
 */
public class Book {

	private String id;
	private VolumeInfo volumeInfo;
	
	public Book () {
	}

	public String getId() {
		return Objects.toString(id, "");
	}

	public void setIdAPI(String id) {
		this.id = id;
	}

	public VolumeInfo getVolumeInfo() {
		return volumeInfo;
	}

	public void setVolumeInfo(VolumeInfo volumeInfo) {
		this.volumeInfo = volumeInfo;
	}
	
	// Simplify rest of get methods:
	
	public String getTitle () {
		return Objects.toString(this.volumeInfo.getTitle(), "");
	}
	
	public String getAllAuthors () {
		StringBuilder allAuthors = new StringBuilder();
		
		String [] authors = this.volumeInfo.getAuthors();
		if (authors != null) {
			for (int i = 0; i < authors.length; i++) {
				allAuthors.append(authors[i]);
				if (i < authors.length - 1) {
					allAuthors.append(", ");
				}
			}
		}
		return allAuthors.toString();
	}
	
	public String getDescription() {
		return Objects.toString(this.volumeInfo.getDescription(), "");
	}
	
	public String getAllCategories () {
		StringBuilder allCategories = new StringBuilder();
		
		String [] categories = this.volumeInfo.getCategories();
		if (categories != null) {
			for (int i = 0; i < categories.length; i++) {
				allCategories.append(categories[i]);
				if (i < categories.length - 1) {
					allCategories.append(", ");
				}
			}
		}
		return allCategories.toString();
	}
	
	public String getLanguage () {
		return Objects.toString(this.volumeInfo.getLanguage(), "");
	}
	
	public String getThumbnail () {
		ImageLinks imageLinks = this.volumeInfo.getImageLinks();
		if (imageLinks != null) {
			return Objects.toString(imageLinks.getThumbnail(), "");
		}
		return "";
	}
	
	public String getAllIndustryIdentifiers () {
		StringBuilder allIndustryIdentifiers = new StringBuilder();
		
		IndustryIdentifiers [] industryIdentifiers = this.volumeInfo.getIndustryIdentifiers();
		if (industryIdentifiers != null) {
			for (int i = 0; i < industryIdentifiers.length; i++) {
				allIndustryIdentifiers.append(industryIdentifiers[i].getType());
				allIndustryIdentifiers.append(":");
				allIndustryIdentifiers.append(industryIdentifiers[i].getIdentifier());
				if (i < industryIdentifiers.length - 1) {
					allIndustryIdentifiers.append(", ");
				}
			}
		}
		return allIndustryIdentifiers.toString();		
	}
	
}
