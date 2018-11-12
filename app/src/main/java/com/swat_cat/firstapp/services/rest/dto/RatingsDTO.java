package com.swat_cat.firstapp.services.rest.dto;
import com.google.gson.annotations.SerializedName;

public class RatingsDTO {

	@SerializedName("Value")
	private String value;

	@SerializedName("Source")
	private String source;

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return value;
	}

	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return source;
	}

	@Override
 	public String toString(){
		return 
			"RatingsDTO{" +
			"value = '" + value + '\'' + 
			",source = '" + source + '\'' + 
			"}";
		}
}