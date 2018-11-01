package com.bookexchange.mongodb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
	private List<String> userIDs;

	public Book () {
	}

	public String getId() {
		return Objects.toString(id, "");
	}

	public void setId(String id) {
		this.id = id;
	}

	public VolumeInfo getVolumeInfo() {
		return volumeInfo;
	}

	public void setVolumeInfo(VolumeInfo volumeInfo) {
		this.volumeInfo = volumeInfo;
	}

	public List<String> getUserIDs() {
		return Collections.unmodifiableList(userIDs);
	}

	public void setUserIDs(List<String> userIDs) {
		this.userIDs = new ArrayList<String>(userIDs);
	}
	
	/**
	 * Adds a user id to the list of users. 
	 * 
	 * @param userID id to be added to the list
	 * @return true if user was added, false if user already exists
	 */
	public boolean addUser (String userID) {
		
		if (this.userIDs == null) {
			this.userIDs = new ArrayList<String>();
		}
		
		if (this.userIDs.contains(userID)) {
			return false;
		}
		
		this.userIDs.add(userID);
		return true;
	}
	
	/**
	 * Returns title of the book as string or empty string if title is null
	 * 
	 * @return title of the book as string
	 */
	public String getTitle () {
		return Objects.toString(this.volumeInfo.getTitle(), "");
	}
	
	/**
	 * Returns authors of the book, as a string, separated by ", "
	 * (used in forms for readability)
	 * TODO: bad practice!! replace with form of multiple fields!
	 * 
	 * @return string with all authors of the book
	 */
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
	
	/**
	 * Returns description of the book as string or empty string if description is null
	 * 
	 * @return description of the book as string
	 */
	public String getDescription() {
		return Objects.toString(this.volumeInfo.getDescription(), "");
	}
	
	/**
	 * Returns categories of the book, as a string, separated by ", "
	 * (used in forms for readability)
	 * TODO: bad practice!! replace with form of multiple fields!
	 * 
	 * @return string with all categories of the book
	 */	
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
	
	/**
	 * Returns language of the book as string or empty string if language is null
	 * 
	 * @return language of the book as string
	 */	
	public String getLanguage () {
		return Objects.toString(this.volumeInfo.getLanguage(), "");
	}

	/**
	 * Returns thumbnail of the book as string or empty string if thumbnail is null
	 * 
	 * @return thumbnail of the book as string
	 */
	public String getThumbnail () {
		ImageLinks imageLinks = this.volumeInfo.getImageLinks();
		if (imageLinks != null) {
			return Objects.toString(imageLinks.getThumbnail(), "");
		}
		return "";
	}

	/**
	 * Returns identifiers of the book, as a string, separated by ", "
	 * under the format <IDENTIFIER_TYPE>:<IDENTIFIER>
	 * (used in forms for readability)
	 * TODO: bad practice!! replace with form of multiple fields!
	 * 
	 * @return string with all identifiers of the book
	 */	
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
