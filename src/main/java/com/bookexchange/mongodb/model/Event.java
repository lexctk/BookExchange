package com.bookexchange.mongodb.model;

public class Event {
	
	private String lang;
	private String title;
	private String uid;
	private String placename;
	private String pricing_info;
	private String image;
	private String date_start;
	private String department;
	private String city;
	private String link;
	private String free_text;
	private String address;
	private String region;
	private String date_end;
	private String description;
	private Location location;
	
	
	public String getLang() {
		return lang;
	}
	
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getPlacename() {
		return placename;
	}
	
	public void setPlacename(String placename) {
		this.placename = placename;
	}
	
	public String getPricing_info() {
		return pricing_info;
	}
	
	public void setPricing_info(String pricing_info) {
		this.pricing_info = pricing_info;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getDate_start() {
		return date_start;
	}
	
	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getFree_text() {
		return free_text;
	}
	
	public void setFree_text(String free_text) {
		this.free_text = free_text;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getRegion() {
		return region;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}
	
	public String getDate_end() {
		return date_end;
	}
	
	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
}
