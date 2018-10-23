package com.swat_cat.firstapp.services.rest.dto;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SearchResultDTO{

	@SerializedName("Response")
	private String response;

	@SerializedName("totalResults")
	private Long totalResults;

	@SerializedName("Search")
	private List<SearchItemDTO> search;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setTotalResults(Long totalResults){
		this.totalResults = totalResults;
	}

	public Long getTotalResults(){
		return totalResults;
	}

	public void setSearch(List<SearchItemDTO> search){
		this.search = search;
	}

	public List<SearchItemDTO> getSearch(){
		return search;
	}

	@Override
 	public String toString(){
		return 
			"SearchResultDTO{" + 
			"response = '" + response + '\'' + 
			",totalResults = '" + totalResults + '\'' + 
			",search = '" + search + '\'' + 
			"}";
		}
}