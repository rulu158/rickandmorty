package dev.bracers.rickandmorty.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import dev.bracers.rickandmorty.exception.EpisodesNotFoundException;
import dev.bracers.rickandmorty.model.Character;
import dev.bracers.rickandmorty.model.Episode;
import dev.bracers.rickandmorty.model.consumer.CharactersJSON;

@Service
public class CharacterServiceImpl implements CharacterService {
	
	private final RestTemplate restTemplate;
	
	private final EpisodeService episodeService;
	
	private final String charactersBaseAPIEndpoint = "https://rickandmortyapi.com/api/character/?name=";

	@Autowired
	public CharacterServiceImpl(RestTemplate restTemplate, EpisodeService episodeService) {
		this.restTemplate = restTemplate;
		this.episodeService = episodeService;
	}
	
	@Override
	public List<Character> getCharacter(String name) throws Exception {
		List<Character> responseCharacters = new ArrayList<Character>();

		CharactersJSON apiCharacters;
		String nextPage = charactersBaseAPIEndpoint + name;
		while (nextPage != null) {
			try {
				apiCharacters = restTemplate.getForObject(nextPage, CharactersJSON.class);
			} catch (RestClientException e) {
				return new ArrayList<Character>();
			}
			
			for (var characterResult : apiCharacters.results()) {
				if (!name.equals(characterResult.name())) {
					continue;
				}

				Long characterId = characterResult.id();
				String characterName = characterResult.name();
				List<String> characterEpisodesUrl = characterResult.episodes();
				
				List<String> episodesIds = new ArrayList<String>();
				for (String episodeUrl : characterEpisodesUrl) {
					URI uri = new URI(episodeUrl);
					String path = uri.getPath();
					String idStr = path.substring(path.lastIndexOf('/') + 1);
					episodesIds.add(idStr);
				}
				
				List<Episode> characterEpisodes;
				try {
					characterEpisodes = episodeService.getEpisodes(episodesIds);
				} catch (EpisodesNotFoundException e) {
					e.printStackTrace();
					throw new Exception(e.getMessage());
				}
				
				List<String> characterEpisodesName = new ArrayList<String>();
				for (Episode characterEpisode : characterEpisodes) {
					characterEpisodesName.add(characterEpisode.getName());
				}

				String characterFirstAppearance = characterEpisodes.get(0).getAirDate();

				responseCharacters.add(
						new Character(characterId,
								characterName,
								characterEpisodesName,
								characterFirstAppearance));
			}
			
			nextPage = apiCharacters.info().next();
		}
		
		return responseCharacters;
	}

}
