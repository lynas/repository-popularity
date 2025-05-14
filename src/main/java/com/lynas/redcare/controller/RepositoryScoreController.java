package com.lynas.redcare.controller;

import com.lynas.redcare.dto.RepositoryScoreResponse;
import com.lynas.redcare.service.ScoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/repository-scores", produces = MediaType.APPLICATION_JSON_VALUE)
public class RepositoryScoreController {

    private final ScoringService scoringService;

    @GetMapping("")
    public RepositoryScoreResponse getRepositoryScore(
            @RequestParam("language") Optional<String> language,
            @RequestParam("lastUpdatedAt") Optional<LocalDate> lastUpdatedAt
    ) {
        return scoringService.getRepositoryScore(
                language.orElse(""),
                lastUpdatedAt.orElse(LocalDate.now().minusDays(30))
        );
    }
}
