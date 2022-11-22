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

import jakarta.security.enterprise.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException>
{
	ObjectMapper mapper = new ObjectMapper();
	@Context private HttpServletRequest request;
    @Context private HttpServletResponse response;   
	@Context private ResourceInfo resourceInfo;	
	@Context private UriInfo uriInfo;
	
	@Override
	public Response toResponse(AuthenticationException exception) {
		// TODO Auto-generated method stub

		final Logger logger = LogManager.getLogger(Artists.class);
		String correlationId = "10004";
		String code = "E-401";
		String title = exception.getMessage();
		String message = "HTTP 401 Unauthorized - Authentication credentials are required to the resource.";
		String docUrl = "https://example.org";
		Error authError = new Error(code,title,message,docUrl);
		RestError entity = new RestError(401, resourceInfo.getResourceMethod().toString(), correlationId,
				request.getPathInfo(), uriInfo.getRequestUri().toString(),authError);
		
		try {
			logger.error("Authentication :"+exception.getMessage());
			return Response.status(Response.Status.UNAUTHORIZED)
				           .entity(mapper.writeValueAsString(entity))
				           .build();
		} catch (JsonProcessingException e) {
			logger.error("INTERNAL_SERVER_ERROR :"+e.getMessage());
			return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
		}
	
	}

}
