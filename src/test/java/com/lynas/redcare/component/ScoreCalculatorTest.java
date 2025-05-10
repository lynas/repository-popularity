package com.lynas.redcare.component;

import com.lynas.redcare.config.ScoreConfigProperties;
import com.lynas.redcare.dto.RepositoryDetailsDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ScoreCalculatorTest {

    @Test
    void testCalculateScore() {
        //given
        ScoreConfigProperties config = mock(ScoreConfigProperties.class);
        when(config.starWeight()).thenReturn(1);
        when(config.forkWeight()).thenReturn(2);
        when(config.lastUpdateWeight()).thenReturn(0.1);

        RepositoryDetailsDto repo = mock(RepositoryDetailsDto.class);
        when(repo.starCount()).thenReturn(10);
        when(repo.forkCount()).thenReturn(4);
        when(repo.updatedAt()).thenReturn(
                LocalDate.now().minusDays(3)
                        .atStartOfDay(ZoneOffset.UTC)
                        .toInstant()
                        .toString()
        );

        ScoreCalculator calculator = new ScoreCalculator(config);

        //when
        double score = calculator.calculateScore(repo);

        //then
        // Expected = 10*1 + 4*2 - 3*0.1 = 17.7
        assertEquals(17.7, score, 0.0001);
    }
}