package com.nessynet.yaacs.service.ann;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nessynet.yaacs.model.ann.AnnAnime;
import com.nessynet.yaacs.model.ann.AnnAnimeAltTitle;
import com.nessynet.yaacs.model.ann.AnnAnimeWebsite;
import org.joox.Match;

import static org.joox.JOOX.$;
import static org.joox.JOOX.attr;

public class AnnJooxParser {
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


	public List<AnnAnime> extractAnime(final String xml) {
		List<AnnAnime> annAnimeList = new ArrayList<>();

		$(xml).find("anime")
			  .forEach(animeDoc -> {
				  AnnAnime annAnime = new AnnAnime();
				  annAnime.setId(Integer.parseInt($(animeDoc).attr("id")));
				  annAnime.setGid(Integer.parseInt($(animeDoc).attr("gid")));
				  annAnime.setType($(animeDoc).attr("type"));
				  annAnime.setName($(animeDoc).attr("name"));
				  annAnime.setFetchedOn(Instant.parse($(animeDoc).attr("generated-on")));
				  annAnime.getAltTitles()
						  .addAll(parseAltTitles($(animeDoc).find("info")
															.filter(attr("type", "Alternative title"))));
				  annAnime.getGeneres()
						  .addAll(parseGenres($(animeDoc).find("info")
														 .filter(attr("type", "Genres"))));
				  annAnime.getGeneres()
						  .addAll(parseGenres($(animeDoc).find("info")
														 .filter(attr("type", "Themes"))));
				  annAnime.setSummary(parseSummary($(animeDoc).find("info")
															  .filter(attr("type", "Plot Summary"))));
				  annAnime.setEpisodes(
						  parseEpisodeCount($(animeDoc).find("info")
													   .filter(attr("type", "Number of episodes"))));

				  String[] vintageDates = $(animeDoc).find("info")
													 .filter(attr("type", "Vintage"))
													 .text()
													 .split(" to ");
				  annAnime.setStartDate(parseStartDate(vintageDates));
				  annAnime.setEndDate(parseEndDate(vintageDates));
				  annAnime.getWebsites()
						  .addAll(parseWebsites($(animeDoc).find("info")
														   .filter(attr("type", "Official website"))));

				  Match ratingsMatch = $(animeDoc).find("ratings");
				  annAnime.setRatingVotes(parseRatingVotes(ratingsMatch));
				  annAnime.setRatingScore(parseRatingScore(ratingsMatch));

				  annAnimeList.add(annAnime);
			  });
		return annAnimeList;
	}

	protected List<AnnAnimeAltTitle> parseAltTitles(final Match altTitleMatch) {
		List<AnnAnimeAltTitle> altTitleList = new ArrayList<>();
		$(altTitleMatch).forEach(altTitle -> {
			AnnAnimeAltTitle annAnimeAltTitle = new AnnAnimeAltTitle();
			annAnimeAltTitle.setLanguage($(altTitle).attr("lang"));
			annAnimeAltTitle.setTitle($(altTitle).text());
			altTitleList.add(annAnimeAltTitle);
		});
		return altTitleList;
	}

	protected List<String> parseGenres(final Match genres) {
		List<String> genreList = new ArrayList<>();
		$(genres).forEach(genre -> {
			genreList.add($(genre).text());
		});
		return genreList;
	}

	protected String parseSummary(final Match plotSummary) {
		return $(plotSummary).text();
	}

	protected Integer parseEpisodeCount(final Match episodes) {
		return Integer.parseInt($(episodes).text());
	}

	protected Date parseStartDate(final String[] vintage) {
		try {
			return sdf.parse(vintage[0]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected Date parseEndDate(final String[] vintage) {
		if (vintage.length > 1) {
			try {
				return sdf.parse(vintage[1]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	protected List<AnnAnimeWebsite> parseWebsites(final Match websiteMatch) {
		List<AnnAnimeWebsite> websiteList = new ArrayList<>();
		$(websiteMatch).forEach(website -> {
			AnnAnimeWebsite annAnimeWebsite = new AnnAnimeWebsite();
			annAnimeWebsite.setType($(website).attr("type"));
			annAnimeWebsite.setLanguage($(website).attr("lang"));
			annAnimeWebsite.setLink($(website).attr("href"));
			annAnimeWebsite.setTitle($(website).text());
			websiteList.add(annAnimeWebsite);
		});
		return websiteList;
	}

	protected Double parseRatingVotes(final Match ratings) {
		return Double.parseDouble($(ratings).attr("nb_votes"));
	}

	protected Double parseRatingScore(final Match ratings) {
		return Double.parseDouble($(ratings).attr("weighted_score"));
	}
}
