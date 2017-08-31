package com.nessynet.yaacs.bean;

import com.nessynet.yaacs.model.MalAnimeEnrichment;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class MalHtmlScraper {

    Logger logger = Logger.getLogger(MalHtmlScraper.class);

    public MalAnimeEnrichment scrap(String url) throws IOException {
        MalAnimeEnrichment malAnimeEnrichment = new MalAnimeEnrichment();

        Document doc = Jsoup.connect(url).get();
        logger.info(doc.toString());

        return malAnimeEnrichment;
    }
}
