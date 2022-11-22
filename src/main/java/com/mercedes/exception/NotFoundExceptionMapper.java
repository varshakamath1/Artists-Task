/****************************************************/
// Filename: Artist.java
// Created: Varsha Kamath
// Change history:
// 22.11.2022 / Varsha Kamath
/****************************************************/

package com.mercedes.exception;

import com.mercedes.artists.model.RestError;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercedes.artists.Artists;
import com.mercedes.artists.model.Error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException>{

	ObjectMapper mapper = new ObjectMapper();
	
    @Context private HttpServletRequest request;

    @Context private HttpServletResponse response;
    
	@Context private ResourceInfo resourceInfo;
	
	@Context private UriInfo uriInfo;
	@Override
	public Response toResponse(NotFoundException exception) {
		// TODO Auto-generated method stub
		final Logger logger = LogManager.getLogger(Artists.class);
		Response response = exception.getResponse();
	    Response.StatusType statusType = response.getStatusInfo();
	    String correlationId = "10001";
	    String code = "E-404";
	    String title = exception.getMessage();
	    String message = "HTTP 404 Not found - We could not locate the resource based on the specified URL.";
	    String docUrl = "https://example.org";
	    Error notFoundError = new Error(code,title,message,docUrl);
	    
	    RestError entity = new RestError(statusType.getStatusCode(), request.getMethod(), correlationId,
	    		request.getPathInfo(), uriInfo.getRequestUri().toString(),notFoundError);
	    try {
	    	logger.error("Not Found :"+exception.getMessage());
			return Response.status(Response.Status.NOT_FOUND)
			               .entity(mapper.writeValueAsString(entity))
			               .build();
		} catch (JsonProcessingException e) {
			logger.error("INTERNAL_SERVER_ERROR :"+e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

	}

}
