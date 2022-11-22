/****************************************************/
// Filename: Artist.java
// Created: Varsha Kamath
// Change history:
// 22.11.2022 / Varsha Kamath
/****************************************************/

package com.mercedes.artists.model;

public class Error {
	
	private String code;
	private String title;
	private String message;
	private String docUrl;
	
	public Error(String code, String title, String message, String docUrl) {
	    this.code = code;
	    this.title = title;
	    this.message = message;    
	    this.docUrl = docUrl;
	}

	public String getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

	public String getDocUrl() {
		return docUrl;
	}

	
}
