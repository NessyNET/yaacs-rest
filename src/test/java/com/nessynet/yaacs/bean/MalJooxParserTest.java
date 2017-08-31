package com.nessynet.yaacs.bean;

import com.nessynet.yaacs.model.MalAnime;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MalJooxParserTest {

    private static final SimpleDateFormat MAL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private MalJooxParser malJooxParser;

    @Before
    public void setUp() throws Exception {
        malJooxParser = new MalJooxParser();
    }

    @Test
    public void testExtractAnimeEntries_MultipleEntries() throws Exception {
        //Prepare
        String xml = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader()
                        .getResource("mal/mal-anime-search-response.xml")
                        .toURI())));

        final int expectedSize = 8;


        //Execute
        List<MalAnime> malAnimeList = malJooxParser.extractAnimeEntries(xml);

        //Assert
        assertThat(malAnimeList).size().isEqualTo(expectedSize);
    }

    @Test
    public void testExtractAnimeEntries_SingleEntry() throws Exception {
        //Prepare
        String xml = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader()
                        .getResource("mal/mal-anime-search-response-2.xml")
                        .toURI())));

        final int expectedSize = 1;
        final Integer expectedId = 269;
        final String expectedTitle = "Bleach";
        final String expectedEnglishTitle = "Bleach";
        final List<String> expectedSynonyms = Collections.emptyList();
        final Integer expectedEpisodes = 366;
        final Double expectedScore = 7.92;
        final String expectedType = "TV";
        final String expectedStatus = "Finished Airing";
        final Date expectedStartDate = MAL_DATE_FORMAT.parse("2004-10-05");
        final Date expectedEndDate = MAL_DATE_FORMAT.parse("2012-03-27");
        final String expectedSynopsis = "Ichigo Kurosaki is an ordinary high schooler";
        final String expectedImageUrl = "https://myanimelist.cdn-dena.com/images/anime/3/40451.jpg";

        //Execute
        List<MalAnime> malAnimeList = malJooxParser.extractAnimeEntries(xml);

        //Assert
        assertThat(malAnimeList).size().isEqualTo(expectedSize);
        assertThat(malAnimeList.get(0)).isNotNull();
        assertThat(malAnimeList.get(0).getId()).isEqualTo(expectedId);
        assertThat(malAnimeList.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(malAnimeList.get(0).getEnglishTitle()).isEqualTo(expectedEnglishTitle);
        assertThat(malAnimeList.get(0).getSynonyms()).isEqualTo(expectedSynonyms);
        assertThat(malAnimeList.get(0).getEpisodes()).isEqualTo(expectedEpisodes);
        assertThat(malAnimeList.get(0).getScore()).isEqualTo(expectedScore);
        assertThat(malAnimeList.get(0).getType()).isEqualTo(expectedType);
        assertThat(malAnimeList.get(0).getStatus()).isEqualTo(expectedStatus);
        assertThat(malAnimeList.get(0).getStartDate()).isEqualTo(expectedStartDate);
        assertThat(malAnimeList.get(0).getEndDate()).isEqualTo(expectedEndDate);
        assertThat(malAnimeList.get(0).getSynopsis()).startsWith(expectedSynopsis);
        assertThat(malAnimeList.get(0).getImageUrl()).isEqualTo(expectedImageUrl);
    }

    @Test
    public void testExtractAnimeEntries_MissingDate() throws Exception {
        //Prepare
        String xml = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader()
                        .getResource("mal/mal-anime-search-response-3.xml")
                        .toURI())));

        final int expectedSize = 1;
        final Date expectedStartDate = null;

        //Execute
        List<MalAnime> malAnimeList = malJooxParser.extractAnimeEntries(xml);

        //Assert
        assertThat(malAnimeList).size().isEqualTo(expectedSize);
        assertThat(malAnimeList.get(0).getStartDate()).isNull();
    }

    @Test
    public void testExtractAnimeEntries_MissingFields() throws Exception {
        //Prepare
        String xml = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader()
                        .getResource("mal/mal-anime-search-response-4.xml")
                        .toURI())));

        final int expectedSize = 1;
        final Integer expectedId = 269;
        final String expectedTitle = "Bleach";
        final String expectedEnglishTitle = "";
        final List<String> expectedSynonyms = Collections.emptyList();
        final Integer expectedEpisodes = 366;
        final Double expectedScore = null;
        final String expectedType = "TV";
        final String expectedStatus = "";
        final Date expectedStartDate = MAL_DATE_FORMAT.parse("2004-10-05");
        final Date expectedEndDate = MAL_DATE_FORMAT.parse("2012-03-27");
        final String expectedSynopsis = "";
        final String expectedImageUrl = "";

        //Execute
        List<MalAnime> malAnimeList = malJooxParser.extractAnimeEntries(xml);

        //Assert
        assertThat(malAnimeList).size().isEqualTo(expectedSize);
        assertThat(malAnimeList.get(0)).isNotNull();
        assertThat(malAnimeList.get(0).getId()).isEqualTo(expectedId);
        assertThat(malAnimeList.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(malAnimeList.get(0).getEnglishTitle()).isEqualTo(expectedEnglishTitle);
        assertThat(malAnimeList.get(0).getSynonyms()).isEqualTo(expectedSynonyms);
        assertThat(malAnimeList.get(0).getEpisodes()).isEqualTo(expectedEpisodes);
        assertThat(malAnimeList.get(0).getScore()).isEqualTo(expectedScore);
        assertThat(malAnimeList.get(0).getType()).isEqualTo(expectedType);
        assertThat(malAnimeList.get(0).getStatus()).isEqualTo(expectedStatus);
        assertThat(malAnimeList.get(0).getStartDate()).isEqualTo(expectedStartDate);
        assertThat(malAnimeList.get(0).getEndDate()).isEqualTo(expectedEndDate);
        assertThat(malAnimeList.get(0).getSynopsis()).startsWith(expectedSynopsis);
        assertThat(malAnimeList.get(0).getImageUrl()).isEqualTo(expectedImageUrl);
    }

    @Test
    public void testExtractAnimeEntries_EmptyEntry() throws Exception {
        //Prepare
        String xml = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader()
                        .getResource("mal/mal-anime-search-response-5.xml")
                        .toURI())));

        final int expectedSize = 1;
        final Integer expectedId = null;
        final String expectedTitle = "";
        final String expectedEnglishTitle = "";
        final List<String> expectedSynonyms = Collections.emptyList();
        final Integer expectedEpisodes = null;
        final Double expectedScore = null;
        final String expectedType = "";
        final String expectedStatus = "";
        final Date expectedStartDate = null;
        final Date expectedEndDate = null;
        final String expectedSynopsis = "";
        final String expectedImageUrl = "";

        //Execute
        List<MalAnime> malAnimeList = malJooxParser.extractAnimeEntries(xml);

        //Assert
        assertThat(malAnimeList).size().isEqualTo(expectedSize);
        assertThat(malAnimeList.get(0)).isNotNull();
        assertThat(malAnimeList.get(0).getId()).isEqualTo(expectedId);
        assertThat(malAnimeList.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(malAnimeList.get(0).getEnglishTitle()).isEqualTo(expectedEnglishTitle);
        assertThat(malAnimeList.get(0).getSynonyms()).isEqualTo(expectedSynonyms);
        assertThat(malAnimeList.get(0).getEpisodes()).isEqualTo(expectedEpisodes);
        assertThat(malAnimeList.get(0).getScore()).isEqualTo(expectedScore);
        assertThat(malAnimeList.get(0).getType()).isEqualTo(expectedType);
        assertThat(malAnimeList.get(0).getStatus()).isEqualTo(expectedStatus);
        assertThat(malAnimeList.get(0).getStartDate()).isEqualTo(expectedStartDate);
        assertThat(malAnimeList.get(0).getEndDate()).isEqualTo(expectedEndDate);
        assertThat(malAnimeList.get(0).getSynopsis()).startsWith(expectedSynopsis);
        assertThat(malAnimeList.get(0).getImageUrl()).isEqualTo(expectedImageUrl);
    }

    @Test
    public void testExtractAnimeEntries_MissingEntry() throws Exception {
        //Prepare
        String xml = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader()
                        .getResource("mal/mal-anime-search-response-6.xml")
                        .toURI())));

        final int expectedSize = 0;

        //Execute
        List<MalAnime> malAnimeList = malJooxParser.extractAnimeEntries(xml);

        //Assert
        assertThat(malAnimeList).size().isEqualTo(expectedSize);
    }

    @Test
    public void testExtractAnimeEntries_EmptyXML() throws Exception {
        //Prepare
        String xml = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader()
                        .getResource("mal/mal-anime-search-response-7.xml")
                        .toURI())));

        final int expectedSize = 0;

        //Execute
        List<MalAnime> malAnimeList = malJooxParser.extractAnimeEntries(xml);

        //Assert
        assertThat(malAnimeList).size().isEqualTo(expectedSize);
    }
}
