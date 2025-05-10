package com.lynas.redcare.controller;

import com.lynas.redcare.dto.RepositoryScoreDto;
import com.lynas.redcare.dto.RepositoryScoreResponse;
import com.lynas.redcare.service.ScoringService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RepositoryScoreControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ScoringService scoringService;

    @Test
    void shouldReturnRepositoryScores() throws Exception {
        var url = "http://localhost";
        List<RepositoryScoreDto> scores = List.of(
                new RepositoryScoreDto("repo1", 100.0, url),
                new RepositoryScoreDto("repo2", 90.0, url)
        );
        when(scoringService.getRepositoryScore("java", LocalDate.of(2024, 4, 1)))
                .thenReturn(new RepositoryScoreResponse(scores));

        mockMvc.perform(get("/repository-scores")
                        .param("language", "java")
                        .param("lastUpdatedAt", "2024-04-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("repo1"))
                .andExpect(jsonPath("$.data[0].score").value(100.0))
                .andExpect(jsonPath("$.data[0].url").value(url))
                .andExpect(jsonPath("$.data[1].name").value("repo2"))
                .andExpect(jsonPath("$.data[1].score").value(90.0))
                .andExpect(jsonPath("$.data[1].url").value(url));
    }


    @Test
    void shouldUseDefaultsWhenParamsNotProvided() throws Exception {
        var url = "http://localhost";
        List<RepositoryScoreDto> scores = List.of(new RepositoryScoreDto("defaultRepo", 50.0, url));
        LocalDate expectedDate = LocalDate.now().minusDays(30);
        when(scoringService.getRepositoryScore("", expectedDate))
                .thenReturn(new RepositoryScoreResponse(scores));

        mockMvc.perform(get("/repository-scores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("defaultRepo"))
                .andExpect(jsonPath("$.data[0].score").value(50.0))
                .andExpect(jsonPath("$.data[0].url").value(url));
    }
}