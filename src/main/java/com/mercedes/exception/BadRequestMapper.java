/****************************************************/
// Filename: Artist.java
// Created: Varsha Kamath
// Change history:
// 22.11.2022 / Varsha Kamath
/****************************************************/

package com.mercedes.exception;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.mercedes.artists.Artists;
import com.mercedes.artists.model.Error;
import com.mercedes.artists.model.RestError;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;

@Provider
public class BadRequestMapper implements ExceptionMapper<BadRequestException>{

	ObjectMapper mapper = new ObjectMapper();
	
    @Context private HttpServletRequest request;

    @Context private HttpServletResponse response;
    
	@Context private ResourceInfo resourceInfo;
	
	@Context private UriInfo uriInfo;
	
	@Override	
	public Response toResponse(BadRequestException exception) {
        
		final Logger logger = LogManager.getLogger(Artists.class);
		Response response = exception.getResponse();
		Response.StatusType statusType = response.getStatusInfo();
		String correlationId = "10000";        
		String code = "E-400";
		String title = exception.getMessage();
		String message = "HTTP 400 Not found - We could not locate the resource based on the specified URL.";
		String docUrl = "https://example.org";
		Error badRequestError = new Error(code,title,message,docUrl);
		    
		RestError entity = new RestError(statusType.getStatusCode(), resourceInfo.getResourceMethod().toString(), correlationId,
				request.getPathInfo(), uriInfo.getRequestUri().toString(),badRequestError);
		try {
			logger.error("Bad request :"+exception.getMessage());
			return Response.status(Response.Status.BAD_REQUEST)
				          .entity(mapper.writeValueAsString(entity))
				          .build();
		} catch (JsonProcessingException e) {
			logger.error("INTERNAL_SERVER_ERROR :"+e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
}
