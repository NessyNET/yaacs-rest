package com.nessynet.yaacs.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.nessynet.yaacs.model.ann.AnnAnime;

@DynamoDBTable(tableName = "Anime")
public class Anime {
	private int yaacsId;
	private String yaacsTitle;
	private AnnAnime annAnime;

	@DynamoDBHashKey(attributeName = "Id")
	public int getYaacsId() {
		return yaacsId;
	}

	public void setYaacsId(int yaacsId) {
		this.yaacsId = yaacsId;
	}

	@DynamoDBRangeKey(attributeName = "Title")
	public String getYaacsTitle() {
		return yaacsTitle;
	}

	public void setYaacsTitle(String yaacsTitle) {
		this.yaacsTitle = yaacsTitle;
	}

	public AnnAnime getAnnAnime() {
		return annAnime;
	}

	public void setAnnAnime(AnnAnime annAnime) {
		this.annAnime = annAnime;
	}
}
