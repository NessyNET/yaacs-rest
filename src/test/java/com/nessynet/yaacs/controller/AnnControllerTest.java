package com.nessynet.yaacs.controller;

import com.nessynet.yaacs.model.Anime;
import com.nessynet.yaacs.model.ann.AnnAnime;
import com.nessynet.yaacs.repository.AnimeRepository;
import com.nessynet.yaacs.service.ann.AnnService;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class AnnControllerTest {


	@Test
	public void searchById_Found_ReturnsObject() throws Exception {
		AnimeRepository animeRepository = mock(AnimeRepository.class);
		AnnService annService = mock(AnnService.class);

		Anime expectedAnime = new Anime();
		when(animeRepository.findAnimeByAnnId(anyInt())).thenReturn(expectedAnime);
		AnnController annController = new AnnController(annService,animeRepository);
		ResponseEntity<Anime> returnedAnime = annController.searchById(1);
		verify(annService,never()).searchById(anyInt());
		assertThat(returnedAnime.getBody()).isEqualTo(expectedAnime);
		assertThat(returnedAnime.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void searchById_Missing_InsertAndReturnsObject() throws Exception {
		AnimeRepository animeRepository = mock(AnimeRepository.class);
		AnnService annService = mock(AnnService.class);

		AnnAnime expectedAnnAnime = new AnnAnime();
		Anime expectedAnime = new Anime();
		expectedAnime.setAnnAnime(expectedAnnAnime);
		when(animeRepository.findAnimeByAnnId(anyInt())).thenReturn(null);
		when(annService.searchById(1)).thenReturn(expectedAnnAnime);
		AnnController annController = new AnnController(annService,animeRepository);
		ResponseEntity<Anime> returnedAnime = annController.searchById(1);
		verify(annService,times(1)).searchById(anyInt());
		assertThat(returnedAnime.getBody().getAnnAnime()).isEqualTo(expectedAnnAnime);
		assertThat(returnedAnime.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}