package com.nessynet.yaacs.model.mal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MalAnime implements Serializable{

    Integer id;
    String title;
    String englishTitle;
    List<String> synonyms;
    Integer episodes;
    Double score;
    String type;
    String status;
    Date startDate;
    Date endDate;
    Date fetchedOn;
    String synopsis;
    String imageUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEnglishTitle() {
        return englishTitle;
    }

    public void setEnglishTitle(String englishTitle) {
        this.englishTitle = englishTitle;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getFetchedOn() {
        return fetchedOn;
    }

    public void setFetchedOn(Date fetchedOn) {
        this.fetchedOn = fetchedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof MalAnime)) return false;

        MalAnime malAnime = (MalAnime) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(id, malAnime.getId())
                .append(title, malAnime.getTitle())
                .append(englishTitle, malAnime.getEnglishTitle())
                .append(synonyms, malAnime.getSynonyms())
                .append(episodes, malAnime.getEpisodes())
                .append(score, malAnime.getScore())
                .append(type, malAnime.getType())
                .append(status, malAnime.getStatus())
                .append(startDate, malAnime.getStartDate())
                .append(endDate, malAnime.getEndDate())
                .append(synopsis, malAnime.getSynopsis())
                .append(imageUrl, malAnime.getImageUrl())
                .isEquals();
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", this.getId())
                .append("title", this.getTitle())
                .append("englishTitle", this.getEnglishTitle())
                .append("synonyms", this.getSynonyms())
                .append("episodes", this.getEpisodes())
                .append("score", this.getScore())
                .append("type", this.getType())
                .append("status", this.getStatus())
                .append("startDate", this.getStartDate())
                .append("endDate", this.getEndDate())
                .append("fetchedOn", this.getFetchedOn())
                .append("synopsis", this.getSynopsis())
                .append("imageUrl", this.getImageUrl())
                .build();
    }
}
