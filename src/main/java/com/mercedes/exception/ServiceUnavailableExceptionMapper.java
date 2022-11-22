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
import com.mercedes.artists.Artists;
import com.mercedes.artists.model.Error;
import com.mercedes.artists.model.RestError;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ServiceUnavailableException;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ServiceUnavailableExceptionMapper implements ExceptionMapper<ServiceUnavailableException>{

	ObjectMapper mapper = new ObjectMapper();	
    @Context private HttpServletRequest request;
    @Context private HttpServletResponse response;
	@Context private ResourceInfo resourceInfo;	
	@Context private UriInfo uriInfo;
	
	
	@Override
	public Response toResponse(ServiceUnavailableException exception) {
		// TODO Auto-generated method stub
		final Logger logger = LogManager.getLogger(Artists.class);
		Response response = exception.getResponse();
	    Response.StatusType statusType = response.getStatusInfo();
	    String correlationId = "10008";
	    String code = "E-503";
	    String title = exception.getMessage();
	    String message = "The requested ID could not be found on our systems. Please verify its correctness or call service personell.";
	    String docUrl = "https://example.org";
	    Error notFoundError = new Error(code,title,message,docUrl);
	    
	    RestError entity = new RestError(statusType.getStatusCode(), request.getMethod(), correlationId,
	    		request.getPathInfo(), uriInfo.getRequestUri().toString(),notFoundError);
	    try {
	    	logger.error("Service Unavailable :"+exception.getMessage());
			return Response.status(Response.Status.SERVICE_UNAVAILABLE)
			               .entity(mapper.writeValueAsString(entity))
			               .build();
		} catch (JsonProcessingException e) {
			logger.error("INTERNAL_SERVER_ERROR :"+e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

	}
}
