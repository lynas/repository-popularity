package com.lynas.redcare.service;

import com.lynas.redcare.dto.RepositoryScoreResponse;

import java.time.LocalDate;

public interface ScoringService {
    RepositoryScoreResponse getRepositoryScore(String language, LocalDate lastUpdatedAt);
}
