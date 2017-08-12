package com.nessynet.yaacs.service.ann;

import com.nessynet.yaacs.model.ann.AnnAnime;
import com.nessynet.yaacs.model.ann.AnnAnimeAltTitle;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnJooxParserTest {

    String responseAnimeById;

    @Before
    public void setUp() throws URISyntaxException, IOException, ParserConfigurationException, SAXException {
        responseAnimeById = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource("ann/animebyid-response.xml").toURI())));
    }

    @Test
    public void extractAnime() throws Exception {
        AnnJooxParser parser = new AnnJooxParser();
        List<AnnAnime> annAnimeList = parser.extractAnime(responseAnimeById);
        assertThat(annAnimeList).hasSize(1);

        AnnAnime annAnime = annAnimeList.get(0);
        assertThat(annAnime.getId()).isEqualTo(8537);
        assertThat(annAnime.getGid()).isEqualTo(2053454777);
        assertThat(annAnime.getType()).isEqualTo("TV");
        assertThat(annAnime.getName()).isEqualTo("Spice and Wolf");
        assertThat(annAnime.getFetchedOn()).isEqualTo(Instant.parse("2017-08-03T13:16:10Z"));
        assertThat(annAnime.getAltTitles()).hasSize(8);

        List<AnnAnimeAltTitle> altTitles = annAnime.getAltTitles();
        AnnAnimeAltTitle altTitle = altTitles.get(0);
        assertThat(altTitle.getGid()).isEqualTo(3050426145L);
        assertThat(altTitle.getLanguage()).isEqualTo("JA");
        assertThat(altTitle.getTitle()).isEqualTo("Ōkami to Kōshinryō");
    }

}