package dev.bracers.rickandmorty.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Episode {
	@JsonProperty("id")
	private long id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("air_date")
	private String airDate;
	
	public Episode(Long id, String name, String airDate) {
		this.id = id;
		this.name = name;
		this.airDate = airDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAirDate() {
		return airDate;
	}

	public void setAirDate(String airDate) {
		this.airDate = airDate;
	}
	
	
}
