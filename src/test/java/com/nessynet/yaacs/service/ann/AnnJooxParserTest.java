package com.nessynet.yaacs.service.ann;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

import com.nessynet.yaacs.model.ann.AnnAnime;
import com.nessynet.yaacs.model.ann.AnnAnimeAltTitle;
import com.nessynet.yaacs.model.ann.AnnAnimeWebsite;
import org.joox.Match;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.joox.JOOX.$;
import static org.joox.JOOX.attr;

public class AnnJooxParserTest {

	String responseAnimeById;

	@Before
	public void setUp() throws URISyntaxException, IOException, ParserConfigurationException, SAXException {
		responseAnimeById = new String(Files.readAllBytes(
				Paths.get(getClass().getClassLoader()
									.getResource("ann/animebyid-response.xml")
									.toURI())));
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
	public void parseAltTitles_English() throws Exception {
		Match testDoc = $("ann",
				$("anime",
						$("info", "Spice + Wolf")
								.attr("type", "Alternative title")
								.attr("lang", "EN")));

		AnnJooxParser parser = new AnnJooxParser();
		Match altTitleMatch = $(testDoc).find("anime")
										.find("info")
										.filter(attr("type", "Alternative title"));
		List<AnnAnimeAltTitle> match = parser.parseAltTitles(altTitleMatch);

		assertThat(match).hasSize(1);
		AnnAnimeAltTitle altTitle = match.get(0);
		assertThat(altTitle.getLanguage()).isEqualTo("EN");
		assertThat(altTitle.getTitle()).isEqualTo("Spice + Wolf");
	}

	@Test
	public void parseAltTitles_KanaExtended() throws Exception {
		Match testDoc = $("ann",
				$("anime",
						$("info", "Ōkami to Kōshinryō")
								.attr("type", "Alternative title")
								.attr("lang", "JA")));

		AnnJooxParser parser = new AnnJooxParser();
		Match altTitleMatch = $(testDoc).find("anime")
										.find("info")
										.filter(attr("type", "Alternative title"));
		List<AnnAnimeAltTitle> match = parser.parseAltTitles(altTitleMatch);

		assertThat(match).hasSize(1);
		AnnAnimeAltTitle altTitle = match.get(0);
		assertThat(altTitle.getLanguage()).isEqualTo("JA");
		assertThat(altTitle.getTitle()).isEqualTo("Ōkami to Kōshinryō");
	}

	@Test
	public void parseAltTitles_UTF8() throws Exception {
		Match testDoc = $("ann",
				$("anime",
						$("info", "狼と香辛料")
								.attr("type", "Alternative title")
								.attr("lang", "JA")));

		AnnJooxParser parser = new AnnJooxParser();
		Match altTitleMatch = $(testDoc).find("anime")
										.find("info")
										.filter(attr("type", "Alternative title"));
		List<AnnAnimeAltTitle> match = parser.parseAltTitles(altTitleMatch);

		assertThat(match).hasSize(1);
		AnnAnimeAltTitle altTitle = match.get(0);
		assertThat(altTitle.getLanguage()).isEqualTo("JA");
		assertThat(altTitle.getTitle()).isEqualTo("狼と香辛料");
	}

	@Test
	public void parseGenres() throws Exception {
		Match testDoc = $("ann",
				$("anime",
						$("info", "adventure")
								.attr("type", "Genres")));

		AnnJooxParser parser = new AnnJooxParser();
		Match match = $(testDoc).find("anime")
								.find("info")
								.filter(attr("type", "Genres"));
		List<String> genres = parser.parseGenres(match);

		assertThat(genres).hasSize(1);
		assertThat(genres).containsOnly("adventure");
	}

	@Test
	public void parseGenres_Themes() throws Exception {
		Match testDoc = $("ann",
				$("anime",
						$("info", "wolf girls")
								.attr("type", "Themes")));

		AnnJooxParser parser = new AnnJooxParser();
		Match match = $(testDoc).find("anime")
								.find("info")
								.filter(attr("type", "Themes"));
		List<String> genres = parser.parseGenres(match);

		assertThat(genres).hasSize(1);
		assertThat(genres).containsOnly("wolf girls");
	}

	@Test
	public void parseSummary() throws Exception {
		Match testDoc = $("ann",
				$("anime",
						$("info", "This is some summary.")
								.attr("type", "Plot Summary")));

		AnnJooxParser parser = new AnnJooxParser();
		Match match = $(testDoc).find("anime")
								.find("info")
								.filter(attr("type", "Plot Summary"));
		String summary = parser.parseSummary(match);

		assertThat(summary).isEqualTo("This is some summary.");
	}

	@Test
	public void parseEpisodeCount() throws Exception {
		Match testDoc = $("ann",
				$("anime",
						$("info", "12")
								.attr("type", "Number of episodes")));

		AnnJooxParser parser = new AnnJooxParser();
		Match match = $(testDoc).find("anime")
								.find("info")
								.filter(attr("type", "Number of episodes"));
		Integer episodeCount = parser.parseEpisodeCount(match);

		assertThat(episodeCount).isEqualTo(12);
	}

	@Test
	public void parseStartDate_OnlyStartDate() throws Exception {
		Match testDoc = $("ann",
				$("anime",
						$("info", "2011-01-24")
								.attr("type", "Vintage")));


		AnnJooxParser parser = new AnnJooxParser();
		String[] vintage = $(testDoc).find("anime")
									 .find("info")
									 .filter(attr("type", "Vintage"))
									 .text()
									 .split(" to ");

		Date startDate = parser.parseStartDate(vintage);
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);

		Calendar cal = Calendar.getInstance();
		cal.set(2011, 0, 24);

		assertThat(startCal.get(Calendar.YEAR)).isEqualTo(cal.get(Calendar.YEAR));
		assertThat(startCal.get(Calendar.MONTH)).isEqualTo(cal.get(Calendar.MONTH));
		assertThat(startCal.get(Calendar.DAY_OF_MONTH)).isEqualTo(cal.get(Calendar.DAY_OF_MONTH));
	}

	@Test
	public void parseStartDate_StartAndEndDate() throws Exception {
		Match testDoc = $("ann",
				$("anime",
						$("info", "2011-01-24 to 2011-04-12")
								.attr("type", "Vintage")));


		AnnJooxParser parser = new AnnJooxParser();
		String[] vintage = $(testDoc).find("anime")
									 .find("info")
									 .filter(attr("type", "Vintage"))
									 .text()
									 .split(" to ");

		Date startDate = parser.parseStartDate(vintage);
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);

		Calendar cal = Calendar.getInstance();
		cal.set(2011, 0, 24);

		assertThat(startCal.get(Calendar.YEAR)).isEqualTo(cal.get(Calendar.YEAR));
		assertThat(startCal.get(Calendar.MONTH)).isEqualTo(cal.get(Calendar.MONTH));
		assertThat(startCal.get(Calendar.DAY_OF_MONTH)).isEqualTo(cal.get(Calendar.DAY_OF_MONTH));
	}

	@Test
	public void parseEndDate() throws Exception {
		Match testDoc = $("ann",
				$("anime",
						$("info", "2011-01-24 to 2011-04-12")
								.attr("type", "Vintage")));

		AnnJooxParser parser = new AnnJooxParser();
		String[] vintage = $(testDoc).find("anime")
									 .find("info")
									 .filter(attr("type", "Vintage"))
									 .text()
									 .split(" to ");

		Date endDate = parser.parseEndDate(vintage);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);

		Calendar cal = Calendar.getInstance();
		cal.set(2011, 3, 12);

		assertThat(endCal.get(Calendar.YEAR)).isEqualTo(cal.get(Calendar.YEAR));
		assertThat(endCal.get(Calendar.MONTH)).isEqualTo(cal.get(Calendar.MONTH));
		assertThat(endCal.get(Calendar.DAY_OF_MONTH)).isEqualTo(cal.get(Calendar.DAY_OF_MONTH));
	}

	@Test
	public void parseEndDate_NoEndDate() throws Exception {
		Match testDoc = $("ann",
				$("anime",
						$("info", "2008-01-08").attr("type", "Vintage")));

		AnnJooxParser parser = new AnnJooxParser();
		String[] vintage = $(testDoc).find("anime")
									 .find("info")
									 .filter(attr("type", "Vintage"))
									 .text()
									 .split(" to ");

		Date endDate = parser.parseEndDate(vintage);
		assertThat(endDate).isNull();
	}

	@Test
	public void parseWebsites() throws Exception {
		Match testDoc = $("info", "Website 1").attr("type", "Official website")
											  .attr("lang", "EN")
											  .attr("href", "http://test.com");

		AnnJooxParser parser = new AnnJooxParser();

		List<AnnAnimeWebsite> websiteList = parser.parseWebsites(testDoc);
		assertThat(websiteList).hasSize(1);

		AnnAnimeWebsite website = websiteList.get(0);
		assertThat(website.getType()).isEqualTo("Official website");
		assertThat(website.getLanguage()).isEqualTo("EN");
		assertThat(website.getLink()).isEqualTo("http://test.com");
		assertThat(website.getTitle()).isEqualTo("Website 1");
	}

}