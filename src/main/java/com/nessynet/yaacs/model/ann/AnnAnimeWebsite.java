package com.nessynet.yaacs.model.ann;

import java.io.Serializable;

public class AnnAnimeWebsite implements Serializable {
    private static final long serialVersionUID = 4740771480041264199L;
    private String type;
    private String language;
    private String link;
    private String title;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
