package com.nessynet.yaacs.model;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

public class AnimeId implements Serializable {
	private static final long serialVersionUID = 1L;

	private String yaacsId;
	private String yaacsTitle;

	@DynamoDBHashKey
	public String getYaacsUuid() {
		return yaacsId;
	}

	public void setYaacsId(String yaacsId) {
		this.yaacsId = yaacsId;
	}

	@DynamoDBRangeKey
	public String getYaacsTitle() {
		return yaacsTitle;
	}

	public void setYaacsTitle(String yaacsTitle) {
		this.yaacsTitle = yaacsTitle;
	}
}
