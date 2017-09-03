package com.nessynet.yaacs.repository;

import java.util.List;

import com.nessynet.yaacs.model.Anime;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBPagingAndSortingRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

@EnableScan
public interface AnimeRepository extends DynamoDBPagingAndSortingRepository<Anime, String> {
	Anime findAnimeByYaacsId(String yaacsId);
	List<Anime> findAnimeByYaacsTitleIsLike(String yaacsTitle);

	Anime findAnimeByAnnId(Integer annId);
}
