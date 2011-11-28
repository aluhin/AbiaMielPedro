package com.abiamiel.model;

public class InfoBean {
	private String title;
	private String description;
	
	public InfoBean() {		
	}

	public InfoBean(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
