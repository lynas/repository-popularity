package com.lynas.redcare.service;

import com.lynas.redcare.component.ScoreCalculator;
import com.lynas.redcare.dto.GithubRepositoryApiResponseDto;
import com.lynas.redcare.dto.RepositoryDetailsDto;
import com.lynas.redcare.dto.RepositoryScoreDto;
import com.lynas.redcare.dto.RepositoryScoreResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class GithubRepositoryScoringServiceTest {

    private GithubClientService githubClientService;
    private ScoreCalculator scoreCalculator;
    private GithubRepositoryScoringService scoringService;

    @BeforeEach
    void setUp() {
        githubClientService = mock(GithubClientService.class);
        scoreCalculator = mock(ScoreCalculator.class);
        scoringService = new GithubRepositoryScoringService(githubClientService, scoreCalculator);
    }

    @Test
    void getRepositoryScoreShouldReturnCalculatedScores() {
        // given
        String language = "Java";
        LocalDate lastUpdatedAt = LocalDate.of(2024, 1, 1);

        var repo1 = new RepositoryDetailsDto(
                "repo1",
                language,
                100,
                50,
                LocalDate.now().minusDays(10) + "T01:10:17Z",
                LocalDate.now().minusDays(5) + "2024-11-19T01:10:17Z",
                "https://github.com/repo1"
                );
        var repo2 = new RepositoryDetailsDto(
                "repo2",
                language,
                10,
                20,
                LocalDate.now().minusDays(10) + "T01:10:17Z",
                LocalDate.now().minusDays(5) + "2024-11-19T01:10:17Z",
                "https://github.com/repo2"
        );

        when(githubClientService.getRepositoryInfo(language, lastUpdatedAt)).thenReturn(new GithubRepositoryApiResponseDto(List.of(repo1, repo2)));
        when(scoreCalculator.calculateScore(repo1)).thenReturn(200.0);
        when(scoreCalculator.calculateScore(repo2)).thenReturn(100.0);

        // when
        RepositoryScoreResponse response = scoringService.getRepositoryScore(language, lastUpdatedAt);

        // then
        assertThat(response.data().size()).isEqualTo(2);
        assertThat(response.data().getFirst()).isEqualTo(new RepositoryScoreDto("repo1", 200.0, "https://github.com/repo1"));

        verify(githubClientService).getRepositoryInfo(language, lastUpdatedAt);
        verify(scoreCalculator).calculateScore(repo1);
        verify(scoreCalculator).calculateScore(repo2);
    }

}