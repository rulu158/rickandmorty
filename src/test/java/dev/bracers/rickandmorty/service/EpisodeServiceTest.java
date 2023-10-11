package dev.bracers.rickandmorty.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

import dev.bracers.rickandmorty.exception.EpisodesNotFoundException;
import dev.bracers.rickandmorty.model.Episode;
import dev.bracers.rickandmorty.model.consumer.EpisodesResultJSON;

@ExtendWith(MockitoExtension.class)
class EpisodeServiceTest {

	@Mock
	private RestTemplate restTemplate;
	
	private EpisodeService underTest;
	
	@BeforeEach
	void setUp() throws Exception {
		underTest = new EpisodeServiceImpl(restTemplate);
	}

	@Test
	void testGetEpisodesTwoEpisodesFound() throws EpisodesNotFoundException {
		Mockito
			.when(restTemplate.getForObject("https://rickandmortyapi.com/api/episode/1,2,", EpisodesResultJSON[].class))
			.thenReturn(new EpisodesResultJSON[] {
					new EpisodesResultJSON(1L, "Episode 1", "September 4, 2013"),
					new EpisodesResultJSON(2L, "Episode 2", "September 14, 2013")
			});
		
		List<String> ids = Arrays.asList("1", "2");
		List<Episode> episodes = underTest.getEpisodes(ids);
		
		assertTrue(episodes.size() == 2);
		
		assertTrue(episodes.get(0).getName() == "Episode 1");
		assertTrue(episodes.get(0).getAirDate() == "September 4, 2013");
		
		assertTrue(episodes.get(1).getName() == "Episode 2");
		assertTrue(episodes.get(1).getAirDate() == "September 14, 2013");
	}
	
	@Test
	void testGetEpisodesNoEpisodesFound() throws EpisodesNotFoundException {
		Mockito
			.when(restTemplate.getForObject("https://rickandmortyapi.com/api/episode/900,", EpisodesResultJSON[].class))
			.thenThrow(new RestClientException("RestClientException"));
		
		List<String> ids = Arrays.asList("900");
		
		assertThatThrownBy(() -> underTest.getEpisodes(ids))
				.hasMessageContaining("No episodes found");
	}

}
