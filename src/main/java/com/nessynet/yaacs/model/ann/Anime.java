package com.nessynet.yaacs.model.ann;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public class Anime implements Serializable {
    private static final long serialVersionUID = 5235431395737686598L;
    private Integer id;
    private Integer gid;
    private String type;
    private String name;
    private Instant fetchedOn;
    private List<AnimeAltTitle> altTitles;
    private List<String> generes;
    private String summary;
    private Integer episodes;
    private Instant startDate;
    private Instant endDate;
    private List<AnimeWebsite> websites;
    private Integer ratingVotes;
    private Integer ratingScore;
    private List<String> episodeTitles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getFetchedOn() {
        return fetchedOn;
    }

    public void setFetchedOn(Instant fetchedOn) {
        this.fetchedOn = fetchedOn;
    }

    public List<AnimeAltTitle> getAltTitles() {
        return altTitles;
    }

    public void setAltTitles(List<AnimeAltTitle> altTitles) {
        this.altTitles = altTitles;
    }

    public List<String> getGeneres() {
        return generes;
    }

    public void setGeneres(List<String> generes) {
        this.generes = generes;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public List<AnimeWebsite> getWebsites() {
        return websites;
    }

    public void setWebsites(List<AnimeWebsite> websites) {
        this.websites = websites;
    }

    public Integer getRatingVotes() {
        return ratingVotes;
    }

    public void setRatingVotes(Integer ratingVotes) {
        this.ratingVotes = ratingVotes;
    }

    public Integer getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(Integer ratingScore) {
        this.ratingScore = ratingScore;
    }

    public List<String> getEpisodeTitles() {
        return episodeTitles;
    }

    public void setEpisodeTitles(List<String> episodeTitles) {
        this.episodeTitles = episodeTitles;
    }
}
