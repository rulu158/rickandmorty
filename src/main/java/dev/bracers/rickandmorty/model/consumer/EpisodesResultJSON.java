package dev.bracers.rickandmorty.model.consumer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodesResultJSON(
		@JsonProperty("id") Long id,
		@JsonProperty("name") String name,
		@JsonProperty("air_date") String airDate) {
}
