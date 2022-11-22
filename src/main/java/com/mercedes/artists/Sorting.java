/****************************************************/
// Filename: Artist.java
// Created: Varsha Kamath
// Change history:
// 22.11.2022 / Varsha Kamath
/****************************************************/

package com.mercedes.artists;

public enum Sorting {
	ASC("asc"),
	DESC("desc");


	  private final String sortType; // stores the data

	  private Sorting(String sortType) {  // <== private constuctor
	      this.sortType = sortType;
	  }

	  public String getSortType() {  // <== allow access to the data
	      return sortType;
	  }
	  
	  public static Sorting getEnum(String value) {
	        for(Sorting v : values())
	            if(v.getSortType().equalsIgnoreCase(value)) return v;
	        throw new IllegalArgumentException();
	    }

}


