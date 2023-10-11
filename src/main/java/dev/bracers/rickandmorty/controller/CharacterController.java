package dev.bracers.rickandmorty.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.bracers.rickandmorty.model.Character;
import dev.bracers.rickandmorty.service.CharacterServiceImpl;

@RestController
@RequestMapping(path = "api/v2/search-character-appearance", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CharacterController {
	
	private final CharacterServiceImpl characterService;
	
	@Autowired
	public CharacterController(CharacterServiceImpl characterService) {
		this.characterService = characterService;
	}
	
	@GetMapping
	public ResponseEntity<List<Character>> findCharacter(@RequestParam("name") String name) {
		List<Character> characters;
		try {
			characters = characterService.getCharacter(name);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new ArrayList<Character>());
		}
		
		if (characters.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<Character>());
		}
		
		return ResponseEntity.ok().body(characters);
	}
}
