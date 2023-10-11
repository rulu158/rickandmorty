package dev.bracers.rickandmorty.service;

import java.util.List;

import dev.bracers.rickandmorty.exception.EpisodesNotFoundException;
import dev.bracers.rickandmorty.model.Episode;

public interface EpisodeService {
	List<Episode> getEpisodes(List<String> ids) throws EpisodesNotFoundException;
}
