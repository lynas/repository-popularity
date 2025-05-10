package com.lynas.redcare.component;

import com.lynas.redcare.config.ScoreConfigProperties;
import com.lynas.redcare.dto.RepositoryDetailsDto;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Component
public class ScoreCalculator {

    private final ScoreConfigProperties scoreConfigProp;

    public ScoreCalculator(ScoreConfigProperties scoreConfigProp) {
        this.scoreConfigProp = scoreConfigProp;
    }

    public double calculateScore(RepositoryDetailsDto repo) {
        long daysSinceLastUpdate = ChronoUnit.DAYS.between(
                Instant.parse(repo.updatedAt()).atZone(ZoneOffset.UTC).toLocalDate(),
                LocalDate.now()
        );

        return repo.starCount() * scoreConfigProp.starWeight()
               + repo.forkCount() * scoreConfigProp.forkWeight()
               - daysSinceLastUpdate * scoreConfigProp.lastUpdateWeight();
    }
}
