package dev.bracers.rickandmorty.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Character {
	@JsonIgnore
	private long id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("episodes")
	private List<String> episodes;
	
	@JsonProperty("first_appearance")
	private String firstAppearance;
	
	public Character() {
		
	}
	
	public Character(Long id, String name, List<String> episodes, String firstAppearance) {
		this.id = id;
		this.name = name;
		this.episodes = episodes;
		this.firstAppearance = firstAppearance;
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

	public List<String> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<String> episodes) {
		this.episodes = episodes;
	}

	public String getFirstAppearance() {
		return firstAppearance;
	}

	public void setFirstAppearance(String firstAppearance) {
		this.firstAppearance = firstAppearance;
	}
	
	
}
