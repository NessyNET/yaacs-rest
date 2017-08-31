package com.nessynet.yaacs.bean;

import com.nessynet.yaacs.model.MalAnime;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joox.Match;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.joox.JOOX.$;

public class MalJooxParser {

    private static final String ENTRY = "entry";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String ENGLISH_TITLE = "english";
    private static final String SYNONYMS = "synonyms";
    private static final String EPISODES= "episodes";
    private static final String SCORE = "score";
    private static final String TYPE = "type";
    private static final String STATUS = "status";
    private static final String START_DATE = "start_date";
    private static final String END_DATE = "end_date";
    private static final String SYNOPSIS = "synopsis";
    private static final String IMAGE_URL = "image";

    private static final String SEMI_COLON = ";";

    private static final SimpleDateFormat MAL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private Logger logger = Logger.getLogger(MalJooxParser.class);

    public List<MalAnime> extractAnimeEntries(final String xml){
        List<MalAnime> malAnimeList = new ArrayList<>();

        if(StringUtils.isEmpty(xml)){
            return Collections.emptyList();
        }

        $(xml).find(ENTRY).forEach(animeEntry -> {
            MalAnime malAnime = new MalAnime();

            malAnime.setId(parseIntegers($(animeEntry).find(ID)));
            malAnime.setTitle(parseStrings($(animeEntry).find(TITLE)));
            malAnime.setEnglishTitle(parseStrings($(animeEntry).find(ENGLISH_TITLE)));
            malAnime.setSynonyms(parseSynonyms($(animeEntry).find(SYNONYMS)));
            malAnime.setEpisodes(parseIntegers($(animeEntry).find(EPISODES)));
            malAnime.setScore(parseDoubles($(animeEntry).find(SCORE)));
            malAnime.setType(parseStrings($(animeEntry).find(TYPE)));
            malAnime.setStatus(parseStrings($(animeEntry).find(STATUS)));
            malAnime.setStartDate(parseDates($(animeEntry).find(START_DATE)));
            malAnime.setEndDate(parseDates($(animeEntry).find(END_DATE)));
            malAnime.setFetchedOn(new Date());
            malAnime.setSynopsis(parseStrings($(animeEntry).find(SYNOPSIS)));
            malAnime.setImageUrl(parseStrings($(animeEntry).find(IMAGE_URL)));

            malAnimeList.add(malAnime);
        });
        return malAnimeList;
    }

    private List<String> parseSynonyms(final Match synonym){
        if(StringUtils.isEmpty(synonym.text())){
            return Collections.emptyList();
        }
        return Arrays.stream(synonym.text().split(SEMI_COLON)).collect(Collectors.toList());
    }

    private Date parseDates(Match date) {
        try {
            if(StringUtils.isEmpty(date.text())){
                return null;
            }
            return MAL_DATE_FORMAT.parse(date.text());
        } catch (ParseException e) {
            logger.error(e);
        }
        return null;
    }

    private Integer parseIntegers(Match integer){
        try{
            if(StringUtils.isEmpty(integer.text())){
                return null;
            }
            return Integer.parseInt($(integer).text());
        } catch(NumberFormatException e){
            logger.error(e);
        }
        return null;
    }

    private Double parseDoubles(Match dub){
        try{
            if(StringUtils.isEmpty(dub.text())){
                return null;
            }
            return Double.parseDouble($(dub).text());
        } catch(NumberFormatException e){
            logger.error(e);
        }
        return null;
    }

    private String parseStrings(Match string){
        if(StringUtils.isEmpty(string.text())){
            return "";
        }
        return $(string).text();
    }
}
