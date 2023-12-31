package dev.bracers.rickandmorty.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import dev.bracers.rickandmorty.exception.EpisodesNotFoundException;
import dev.bracers.rickandmorty.model.Episode;
import dev.bracers.rickandmorty.model.consumer.EpisodesResultJSON;

@Service
public class EpisodeServiceImpl implements EpisodeService {

	private final RestTemplate restTemplate;
	
	private final String episodesBaseAPIEndpoint = "https://rickandmortyapi.com/api/episode/";

	@Autowired
	public EpisodeServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Override
	public List<Episode> getEpisodes(List<String> ids) throws EpisodesNotFoundException {
		List<Episode> responseEpisodes = new ArrayList<Episode>();

		EpisodesResultJSON[] apiEpisodes;
		String episodesAPIEndpoint = episodesBaseAPIEndpoint + String.join(",", ids) + ",";
		try {
			apiEpisodes = restTemplate.getForObject(episodesAPIEndpoint, EpisodesResultJSON[].class);
		} catch (RestClientException e) {
			throw new EpisodesNotFoundException("No episodes found");
		}
			
		for (EpisodesResultJSON episodeResult : Arrays.asList(apiEpisodes)) {
			Long id = episodeResult.id();
			String name = episodeResult.name();
			String airDate = episodeResult.airDate();
			
			responseEpisodes.add(new Episode(id, name, airDate));
		}
		
		return responseEpisodes;
	}
}
