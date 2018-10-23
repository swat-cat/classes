package com.swat_cat.firstapp.services.rest.dto;

import com.google.gson.annotations.SerializedName;

public class SearchItemDTO {

	@SerializedName("Type")
	private String type;

	@SerializedName("Year")
	private Integer year;

	@SerializedName("imdbID")
	private String imdbID;

	@SerializedName("Poster")
	private String poster;

	@SerializedName("Title")
	private String title;

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setYear(Integer year){
		this.year = year;
	}

	public Integer getYear(){
		return year;
	}

	public void setImdbID(String imdbID){
		this.imdbID = imdbID;
	}

	public String getImdbID(){
		return imdbID;
	}

	public void setPoster(String poster){
		this.poster = poster;
	}

	public String getPoster(){
		return poster;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	@Override
 	public String toString(){
		return 
			"SearchItemDTO{" +
			"type = '" + type + '\'' + 
			",year = '" + year + '\'' + 
			",imdbID = '" + imdbID + '\'' + 
			",poster = '" + poster + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}