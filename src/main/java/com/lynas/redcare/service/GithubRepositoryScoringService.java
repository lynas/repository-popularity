package com.lynas.redcare.service;

import com.lynas.redcare.dto.RepositoryScoreDto;
import com.lynas.redcare.dto.RepositoryScoreResponse;
import com.lynas.redcare.validator.InputValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.lynas.redcare.config.CacheConfig.CACHE_REPOSITORY_SCORE;

@RequiredArgsConstructor
@Service
public class GithubRepositoryScoringService implements ScoringService {
    private final GithubClientService githubClientService;
    private final ScoreCalculatorService scoreCalculatorService;

    @Cacheable(cacheNames = CACHE_REPOSITORY_SCORE)
    @Override
    public RepositoryScoreResponse getRepositoryScore(String language, LocalDate lastUpdatedAt) {
        InputValidator.validateLanguage(language);
        var result = githubClientService.getRepositoryInfo(language, lastUpdatedAt);
        var scoreList = result.items().stream()
                .map(it -> new RepositoryScoreDto(
                        it.name(),
                        scoreCalculatorService.calculateScore(it),
                        it.url()
                )).toList();
        return new RepositoryScoreResponse(scoreList);
    }
}
