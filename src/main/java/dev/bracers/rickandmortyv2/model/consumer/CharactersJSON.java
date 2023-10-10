package dev.bracers.rickandmortyv2.model.consumer;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CharactersJSON(CharactersInfoJSON info, List<CharactersResultJSON> results) {
}
