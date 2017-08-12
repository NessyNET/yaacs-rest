package com.nessynet.yaacs.service.ann;

import com.nessynet.yaacs.model.ann.Anime;
import com.nessynet.yaacs.model.ann.AnimeAltTitle;
import org.joox.Match;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.joox.JOOX.$;
import static org.joox.JOOX.attr;

public class AnnJooxParser {

    public List<Anime> extractAnime(final String xml) {
        List<Anime> animeList = new ArrayList<>();

        $(xml).find("anime").forEach(animeDoc -> {
            Anime anime = new Anime();
            anime.setId(Integer.parseInt($(animeDoc).attr("id")));
            anime.setGid(Integer.parseInt($(animeDoc).attr("gid")));
            anime.setType($(animeDoc).attr("type"));
            anime.setName($(animeDoc).attr("name"));
            anime.setFetchedOn(Instant.parse($(animeDoc).attr("generated-on")));
            parseInfoTags(anime, $(animeDoc).find("info").filter(attr("type","Alternative title")));
            animeList.add(anime);
        });
        return animeList;
    }

    protected void parseInfoTags(final Anime anime, final Match altTitles) {
        $(altTitles).forEach(altTitle -> {
            AnimeAltTitle animeAltTitle = new AnimeAltTitle();
            animeAltTitle.setGid(Long.parseLong($(altTitle).attr("gid")));
            animeAltTitle.setLanguage($(altTitle).attr("lang"));
            animeAltTitle.setTitle($(altTitle).text());
            anime.getAltTitles().add(animeAltTitle);
        });
    }
}
