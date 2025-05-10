package com.lynas.redcare.service;

import com.lynas.redcare.component.ScoreCalculator;
import com.lynas.redcare.dto.RepositoryScoreDto;
import com.lynas.redcare.dto.RepositoryScoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.lynas.redcare.config.CacheConfig.CACHE_REPOSITORY_SCORE;

@RequiredArgsConstructor
@Service
public class GithubRepositoryScoringService implements ScoringService {
    private final GithubClientService githubClientService;
    private final ScoreCalculator scoreCalculator;

    @Cacheable(cacheNames = CACHE_REPOSITORY_SCORE)
    @Override
    public RepositoryScoreResponse getRepositoryScore(String language, LocalDate lastUpdatedAt) {
        var result = githubClientService.getRepositoryInfo(language, lastUpdatedAt);
        var scoreList = result.items().stream()
                .map(it -> new RepositoryScoreDto(
                        it.name(),
                        scoreCalculator.calculateScore(it),
                        it.url()
                )).toList();
        return new RepositoryScoreResponse(scoreList);
    }
}
