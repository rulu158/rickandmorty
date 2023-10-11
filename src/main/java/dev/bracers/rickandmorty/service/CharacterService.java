package dev.bracers.rickandmorty.service;

import java.util.List;

import dev.bracers.rickandmorty.model.Character;

public interface CharacterService {
	public List<Character> getCharacter(String name) throws Exception;
}
