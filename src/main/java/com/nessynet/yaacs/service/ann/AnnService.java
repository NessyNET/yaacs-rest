package com.nessynet.yaacs.service.ann;

import java.net.URI;

import com.nessynet.yaacs.model.ann.AnnAnime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AnnService {

	private AnnJooxParser annJooxParser;
	private RestTemplate annRestTemplate;

	@Autowired
	public AnnService(final AnnJooxParser annJooxParser, final RestTemplate annRestTemplate) {
		this.annJooxParser = annJooxParser;
		this.annRestTemplate = annRestTemplate;
	}

	public void setAnnJooxParser(AnnJooxParser annJooxParser) {
		this.annJooxParser = annJooxParser;
	}

	public void setAnnRestTemplate(RestTemplate annRestTemplate) {
		this.annRestTemplate = annRestTemplate;
	}

	public AnnAnime searchById(final Integer id) {
		URI queryUri = UriComponentsBuilder.newInstance()
										   .queryParam("anime", id)
										   .build()
										   .toUri();

		ResponseEntity<String> annResponse = this.annRestTemplate.getForEntity(queryUri, String.class);
		return annJooxParser.extractAnime(annResponse.getBody())
							.get(0);
	}
}
