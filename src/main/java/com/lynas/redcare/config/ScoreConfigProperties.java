package com.lynas.redcare.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.score.repo")
public record ScoreConfigProperties(
        Integer starWeight,
        Integer forkWeight,
        Double lastUpdateWeight
) {
}
