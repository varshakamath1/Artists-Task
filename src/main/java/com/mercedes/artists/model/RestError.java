/****************************************************/
// Filename: Artist.java
// Created: Varsha Kamath
// Change history:
// 22.11.2022 / Varsha Kamath
/****************************************************/

package com.mercedes.artists.model;

public class RestError {
	
	private int status;
	private String method;
	private String correlationId;
	private String requestQuery;
	private String requestURI;
	private Error errors;
	
	public RestError(int status, String method, String correlationId, 
			String requestQuery, String requestURI, Error errors ) {
	    this.status = status;
	    this.method = method;
	    this.correlationId = correlationId;    
	    this.requestQuery = requestQuery;
	    this.requestURI = requestURI;
	    this.requestQuery = requestQuery;
	    this.errors = errors;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public String getRequestQuery() {
		return requestQuery;
	}
	public String getRequestURI() {
		return requestURI;
	}
	public Error getErrors() {
		return errors;
	}
}
