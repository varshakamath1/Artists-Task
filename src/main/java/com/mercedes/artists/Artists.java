/****************************************************/
// Filename: Artists.java
// Created: Varsha Kamath
// Change history:
// 22.11.2022 / Varsha Kamath
/****************************************************/


package com.mercedes.artists;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercedes.artists.model.Artist;
import jakarta.ws.rs.POST;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("artists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Artists{
	
	static Logger logger = LogManager.getLogger(Artists.class);
	ObjectMapper mapper = new ObjectMapper();
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getArtists(@NotNull @QueryParam("limit") int limit, @NotNull @QueryParam("sort") Sorting sortType) {

		try {
			//Convert Json string into list of Artist objects
			List<Artist> artistsVO = mapper.readValue(extractJson(), new TypeReference<List<Artist>>(){});			
			
			if(limit >= 1 && sortType!=null)			
			{
				if(sortType.getSortType().equalsIgnoreCase("asc"))
				{
					Collections.sort(artistsVO);
					logger.info("Artists sorted in ascending order");
				}
				else
				{
					Collections.sort(artistsVO, Collections.reverseOrder());
					logger.info("Artists sorted in descending order");
				}			
				int totalSize = (limit <= artistsVO.size())?limit:artistsVO.size();
				return Response.ok().entity(mapper.writeValueAsString(artistsVO.subList(0, totalSize))).build();
			}
			else {
				
				throw new BadRequestException("limit should be greater than or equal to 1 or sort should not be empty");
			}
					
		} catch (NullPointerException | ParseException | URISyntaxException |IOException e) {
			logger.error("NTERNAL_SERVER_ERROR :"+e.getMessage());
			throw new InternalServerErrorException(e);
		} 		
	}
	
	@Path("{username}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getArtistsByArtistName(@PathParam("username") String username) {

		try {
			//Convert Json string into list of Artist objects
			List<Artist> artistsVO = mapper.readValue(extractJson(), new TypeReference<List<Artist>>(){});
			for(Artist arr: artistsVO)
			{
				if(arr.getUsername().equalsIgnoreCase(username))
				{
					return Response.ok().entity(mapper.writeValueAsString(arr)).build();					
				}			
			}
			{
				throw new BadRequestException("username not found");
			}
		} catch (IOException | ParseException | URISyntaxException e) {
			logger.error("INTERNAL_SERVER_ERROR :"+e.getMessage());
			throw new InternalServerErrorException(e);
		}		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createArtist(@HeaderParam("ApiKeyAuth") String apiKey, String artistRequest )
	{
		try {
			//Convert Json string into list of Artist objects
			List<Artist> input = mapper.readValue(artistRequest, new TypeReference<List<Artist>>(){}); 
			
			//Access and read the properties file
			String resourceName = "data.properties"; // could also be a constant
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			Properties props = new Properties();
			InputStream resourceStream = loader.getResourceAsStream(resourceName);
			props.load(resourceStream);
			String key = props.getProperty("apiKey");			
			if (apiKey.equals(key)) 
			{
				logger.info("API-key is correct");
				HashSet<Artist> artistsVO = mapper.readValue(extractJson(), new TypeReference<HashSet<Artist>>(){});
				
				for(Artist vo: input)
				{
					if(!artistsVO.contains(vo))
					{
						if(!vo.getArtistGenre().equalsIgnoreCase("folk"))
						{
							artistsVO.add(vo);
							logger.info("Successfully created a new artist");
							return Response.status(Response.Status.NO_CONTENT)
							          .entity("Successfully created a new artist")
							          .build();
						}
						else
						{
							throw new BadRequestException("artist_genre cannot be folk");
						}
					}
					else
					{
						throw new BadRequestException("Artists with same username not allowed");
					}
				}			
			}
			else 
			{
				throw new AuthenticationException("API-KEY invalid");
			}
		} catch (IOException | ParseException | URISyntaxException | AuthenticationException e) {
			logger.error("INTERNAL_SERVER_ERROR :"+e.getMessage());
			throw new InternalServerErrorException(e); 
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Error").build();
		   
	}
	
	public String extractJson () throws IOException, ParseException, URISyntaxException
	{
		File file = new File(
		        this.getClass().getClassLoader().getResource("artistsInit.json").getFile()
		    );
	     List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
         StringBuilder str = new StringBuilder();
         for(String line:lines)
         {
        	 str.append(line);
         }
		return str.toString();
         
	}

}
