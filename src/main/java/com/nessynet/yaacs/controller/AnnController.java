package com.nessynet.yaacs.controller;

import com.nessynet.yaacs.model.ann.AnnAnime;
import com.nessynet.yaacs.service.ann.AnnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ann")
public class AnnController {
	public static final Logger logger = LoggerFactory.getLogger(AnnController.class);

	private AnnService annService;

	@Autowired
	public AnnController(final AnnService annService) {
		this.annService = annService;
	}

	@RequestMapping(value = "/search/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<AnnAnime> searchById(@PathVariable("id") int id) {
		logger.info("Searching ANN by ID [{}]", id);
		AnnAnime annAnime = annService.searchById(id);
		return new ResponseEntity<>(annAnime, HttpStatus.OK);
	}
}
