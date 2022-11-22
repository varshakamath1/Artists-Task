/****************************************************/
// Filename: Artist.java
// Created: Varsha Kamath
// Change history:
// 22.11.2022 / Varsha Kamath
/****************************************************/

package com.mercedes.artists.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Artist  implements Comparable<Artist>{
	
	@JsonProperty(value = "artist_name", required = true)
	private String artistName;
	
	@JsonProperty("artist_genre")
	private String artistGenre;
	
	@JsonProperty("albums_recorded")
	private int albumsRecorded;
	
	@JsonProperty("username")
	private String username;
	
	
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getArtistGenre() {
		return artistGenre;
	}
	public void setArtistGenre(String artistGenre) {
		this.artistGenre = artistGenre;
	}
	public int getAlbumsRecorded() {
		return albumsRecorded;
	}
	public void setAlbumsRecorded(int albumsRecorded) {
		this.albumsRecorded = albumsRecorded;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public int compareTo(Artist e) {
		return this.getArtistName().compareTo(e.getArtistName());
	}
	
	
	//overrides the hashCode() method
	@Override
    public int hashCode() {
  
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

	//overrides the equals() method
	@Override
    public boolean equals(Object obj) {
      
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Artist other = (Artist) obj;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

}
