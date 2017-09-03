package com.nessynet.yaacs.controller;

import java.util.List;

import com.nessynet.yaacs.model.Anime;
import com.nessynet.yaacs.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YaacsController {

	private AnimeRepository animeRepository;

	@Autowired
	public YaacsController(final AnimeRepository animeRepository) {
		this.animeRepository = animeRepository;
	}

	@RequestMapping("/hello")
	public String hello() {
		return "Hello!";
	}

	@RequestMapping(path = "/search/id/{id}")
	public ResponseEntity<Anime> searchByYaacsId(@PathVariable String id) {
		Anime anime = animeRepository.findAnimeByYaacsId(id);
		if (anime != null) {
			return new ResponseEntity<>(anime, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(path = "/search/title/{id}")
	public List<Anime> searchByYaacsTitle(@PathVariable String title) {
		return animeRepository.findAnimeByYaacsTitleIsLike(title);
	}
}
