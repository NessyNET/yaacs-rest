package com.nessynet.yaacs.service.ann;

import com.nessynet.yaacs.model.ann.AnnAnime;
import com.nessynet.yaacs.model.ann.AnnAnimeAltTitle;
import com.nessynet.yaacs.model.ann.AnnAnimeWebsite;
import org.joox.Match;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.joox.JOOX.$;
import static org.joox.JOOX.attr;

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
    }

    @Test
    public void parseAltTitles() throws Exception {
        AnnJooxParser parser = new AnnJooxParser();
        Match altTitleMatch = $(responseAnimeById).find("anime").find("info").filter(attr("type", "Alternative title"));
        List<AnnAnimeAltTitle> match = parser.parseAltTitles(altTitleMatch);

        assertThat(match).hasSize(8);
        AnnAnimeAltTitle altTitle = match.get(0);
        assertThat(altTitle.getGid()).isEqualTo(3050426145L);
        assertThat(altTitle.getLanguage()).isEqualTo("JA");
        assertThat(altTitle.getTitle()).isEqualTo("Ōkami to Kōshinryō");
    }

    @Test
    public void parseGenres() throws Exception {
        AnnJooxParser parser = new AnnJooxParser();
        Match match = $(responseAnimeById).find("anime").find("info").filter(attr("type", "Genres"));
        List<String> genres = parser.parseGenres(match);

        assertThat(genres).hasSize(4);
        assertThat(genres).containsOnly("romance", "fantasy", "drama", "adventure");
    }

    @Test
    public void parseGenres_Themes() throws Exception {
        AnnJooxParser parser = new AnnJooxParser();
        Match match = $(responseAnimeById).find("anime").find("info").filter(attr("type", "Themes"));
        List<String> genres = parser.parseGenres(match);

        assertThat(genres).hasSize(3);
        assertThat(genres).containsOnly("trading", "economics", "wolf girls");
    }

    @Test
    public void parseSummary() throws Exception {
        AnnJooxParser parser = new AnnJooxParser();
        Match match = $(responseAnimeById).find("anime").find("info").filter(attr("type", "Plot Summary"));
        String summary = parser.parseSummary(match);

        assertThat(summary).isNotEmpty();
    }

    @Test
    public void parseEpisodeCount() throws Exception {
        AnnJooxParser parser = new AnnJooxParser();
        Match match = $(responseAnimeById).find("anime").find("info").filter(attr("type", "Number of episodes"));
        Integer episodeCount = parser.parseEpisodeCount(match);

        assertThat(episodeCount).isEqualTo(24);
    }

    @Test
    public void parseStartDate() throws Exception {
        AnnJooxParser parser = new AnnJooxParser();
        String[] vintage = $(responseAnimeById).find("anime")
                                               .find("info")
                                               .filter(attr("type", "Vintage"))
                                               .text()
                                               .split(" to ");

        Date startDate = parser.parseStartDate(vintage);
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar cal = Calendar.getInstance();
        cal.set(2008, 0, 8);

        assertThat(startCal.get(Calendar.YEAR)).isEqualTo(cal.get(Calendar.YEAR));
        assertThat(startCal.get(Calendar.MONTH)).isEqualTo(cal.get(Calendar.MONTH));
        assertThat(startCal.get(Calendar.DAY_OF_MONTH)).isEqualTo(cal.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void parseEndDate() throws Exception {
        AnnJooxParser parser = new AnnJooxParser();
        String[] vintage = $(responseAnimeById).find("anime")
                                               .find("info")
                                               .filter(attr("type", "Vintage"))
                                               .text()
                                               .split(" to ");

        Date endDate = parser.parseEndDate(vintage);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        Calendar cal = Calendar.getInstance();
        cal.set(2008, 2, 25);

        assertThat(endCal.get(Calendar.YEAR)).isEqualTo(cal.get(Calendar.YEAR));
        assertThat(endCal.get(Calendar.MONTH)).isEqualTo(cal.get(Calendar.MONTH));
        assertThat(endCal.get(Calendar.DAY_OF_MONTH)).isEqualTo(cal.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void parseEndDate_NoEndDate() throws Exception {
        Match matcher = $("ann",
                          $("anime",
                            $("info", "2008-01-08").attr("type", "Vintage")));

        AnnJooxParser parser = new AnnJooxParser();
        String[] vintage = $(matcher).find("anime")
                                     .find("info")
                                     .filter(attr("type", "Vintage"))
                                     .text().split(" to ");

        Date endDate = parser.parseEndDate(vintage);
        assertThat(endDate).isNull();
    }

    @Test
    public void parseWebsites() throws Exception {
        Match matcher = $("info", "Website 1").attr("type", "Official website")
                                              .attr("lang", "EN")
                                              .attr("href", "http://test.com");

        AnnJooxParser parser = new AnnJooxParser();

        List<AnnAnimeWebsite> websiteList = parser.parseWebsites(matcher);
        assertThat(websiteList).hasSize(1);

        AnnAnimeWebsite website = websiteList.get(0);
        assertThat(website.getType()).isEqualTo("Official website");
        assertThat(website.getLanguage()).isEqualTo("EN");
        assertThat(website.getLink()).isEqualTo("http://test.com");
        assertThat(website.getTitle()).isEqualTo("Website 1");
    }

}