package com.nessynet.yaacs.model.ann;

import java.io.Serializable;

public class AnnAnimeAltTitle implements Serializable {
    private static final long serialVersionUID = 1284207134736542599L;

    private Long gid;
    private String language;
    private String title;

    public AnnAnimeAltTitle() {
    }

    public AnnAnimeAltTitle(Long gid, String language, String title) {
        this.gid = gid;
        this.language = language;
        this.title = title;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
