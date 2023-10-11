package dev.bracers.rickandmorty.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import dev.bracers.rickandmorty.model.Character;
import dev.bracers.rickandmorty.model.Episode;
import dev.bracers.rickandmorty.model.consumer.CharactersInfoJSON;
import dev.bracers.rickandmorty.model.consumer.CharactersJSON;
import dev.bracers.rickandmorty.model.consumer.CharactersResultJSON;

@ExtendWith(MockitoExtension.class)
class CharacterServiceTest {
	
	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private EpisodeService episodeService;
	
	private CharacterService underTest;

	@BeforeEach
	void setUp() throws Exception {
		underTest = new CharacterServiceImpl(restTemplate, episodeService);
	}

	@Test
	void testGetCharacterRickIsPresent() throws Exception {
		Mockito
			.when(restTemplate.getForObject("https://rickandmortyapi.com/api/character/?name=Rick Sanchez", CharactersJSON.class))
			.thenReturn(
					new CharactersJSON(new CharactersInfoJSON(1, 1, null, null),
					Arrays.asList(
							new CharactersResultJSON(
									1L,
									"Rick Sanchez",
									Arrays.asList(
											"https://rickandmortyapi.com/api/episode/1",
											"https://rickandmortyapi.com/api/episode/2")
									)
							)
					));
		
		Mockito
			.when(episodeService.getEpisodes(Arrays.asList("1", "2")))
			.thenReturn(Arrays.asList(
					new Episode(1L, "Episode 1", "September 4, 2013"),
					new Episode(2L, "Episode 2", "September 14, 2013")
			));
		
		List<Character> characters = underTest.getCharacter("Rick Sanchez");
		
		assertTrue(characters.size() == 1);
		
		assertTrue(characters.get(0).getName() == "Rick Sanchez");
		assertTrue(characters.get(0).getEpisodes().size() == 2);
		assertTrue(characters.get(0).getEpisodes().get(0) == "Episode 1");
		assertTrue(characters.get(0).getFirstAppearance() == "September 4, 2013");
	}
	
	@Test
	void testGetCharacterRaulIsNotPresent() throws Exception {
		Mockito
			.when(restTemplate.getForObject("https://rickandmortyapi.com/api/character/?name=Raul Garcia", CharactersJSON.class))
			.thenThrow(new RestClientException("RestClientException"));
		
		List<Character> characters = underTest.getCharacter("Raul Garcia");
		
		assertTrue(characters.size() == 0);
	}

}
