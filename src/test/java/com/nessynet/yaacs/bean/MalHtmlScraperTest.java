package com.nessynet.yaacs.bean;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class MalHtmlScraperTest {

    private MalHtmlScraper malHtmlScraper;

    @Before
    public void setUp() throws Exception {
        malHtmlScraper = new MalHtmlScraper();
    }

    @Test
    public void testScrape() throws Exception {
        //Prepare

        //Execute
        malHtmlScraper.scrap("https://myanimelist.net/anime/269");

        //Assert
        fail();
    }
}
