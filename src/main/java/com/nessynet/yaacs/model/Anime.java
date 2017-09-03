package com.nessynet.yaacs.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.nessynet.yaacs.model.ann.AnnAnime;
import org.springframework.data.annotation.Id;

@DynamoDBTable(tableName = "Anime")
public class Anime {
	@Id
	private AnimeId animeId;

	private Integer annId;

	private AnnAnime annAnime;

	@DynamoDBHashKey(attributeName = "Id")
	@DynamoDBAutoGeneratedKey
	public String getYaacsId() {
		return animeId != null ? animeId.getYaacsUuid() : null;
	}

	public void setYaacsId(String yaacsId) {
		if (animeId == null) {
			animeId = new AnimeId();
		}
		animeId.setYaacsId(yaacsId);
	}

	@DynamoDBRangeKey(attributeName = "Title")
	public String getYaacsTitle() {
		return animeId != null ? animeId.getYaacsTitle() : null;
	}

	public void setYaacsTitle(String yaacsTitle) {
		if (animeId == null) {
			animeId = new AnimeId();
		}
		animeId.setYaacsTitle(yaacsTitle);
	}

	@DynamoDBAttribute(attributeName = "AnnId")
	public Integer getAnnId() {
		return annId;
	}

	public void setAnnId(final Integer annId) {
		this.annId = annId;
	}

	@DynamoDBAttribute(attributeName = "AnnMetadata")
	public AnnAnime getAnnAnime() {
		return annAnime;
	}

	public void setAnnAnime(AnnAnime annAnime) {
		this.annAnime = annAnime;
	}
}
