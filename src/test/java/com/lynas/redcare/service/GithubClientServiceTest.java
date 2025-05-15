package com.lynas.redcare.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lynas.redcare.dto.GithubRepositoryApiResponseDto;
import com.lynas.redcare.dto.RepositoryDetailsDto;
import com.lynas.redcare.exception.APICallException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@RestClientTest(GithubClientService.class)
class GithubClientServiceTest {

    @Autowired
    private GithubClientService client;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public RestClient restClient(RestClient.Builder builder) {
            return builder.baseUrl("https://api.github.com").build();
        }
    }

    @Test
    void apiShouldReturnData() throws JsonProcessingException {
        // given
        var language = "Java";
        var lastUpdatedAt = LocalDate.now().minusDays(10);
        var baseUrl = "https://api.github.com";
        var repo = new RepositoryDetailsDto(
                "repo1",
                language,
                100,
                50,
                LocalDate.now().minusDays(10) + "T01:10:17Z",
                LocalDate.now().minusDays(5) + "2024-11-19T01:10:17Z",
                baseUrl + "/user/repo1"
        );
        var mockResponse = new GithubRepositoryApiResponseDto(List.of(repo));
        var uri = baseUrl + "/search/repositories?q=language:" + language + "%20created:%3E" + lastUpdatedAt;
        server.expect(requestTo(uri))
                .andRespond(withSuccess(objectMapper.writeValueAsString(mockResponse), MediaType.APPLICATION_JSON));
        // when
        var response = client.getRepositoryInfo(language, lastUpdatedAt);
        // then
        Assertions.assertNotNull(response);
    }

    @Test
    void shouldThrowClientError() {
        // given
        var language = "Java";
        var lastUpdatedAt = LocalDate.now().minusDays(10);
        var baseUrl = "https://api.github.com";
        var uri = baseUrl + "/search/repositories?q=language:" + language + "%20created:%3E" + lastUpdatedAt;
        server.expect(requestTo(uri)).andRespond(MockRestResponseCreators.withResourceNotFound());
        // when
        // then
        Assertions.assertThrows(APICallException.class, () -> client.getRepositoryInfo(language, lastUpdatedAt));
    }

    @Test
    void shouldThrowClientErrorForBadRequest() {
        // given
        var language = "Java";
        var lastUpdatedAt = LocalDate.now().minusDays(10);
        var baseUrl = "https://api.github.com";
        var uri = baseUrl + "/search/repositories?q=language:" + language + "%20created:%3E" + lastUpdatedAt;
        server.expect(requestTo(uri)).andRespond(MockRestResponseCreators.withBadRequest());
        // when
        // then
        Assertions.assertThrows(APICallException.class, () -> client.getRepositoryInfo(language, lastUpdatedAt));
    }

    @Test
    void shouldThrowServerError() {
        // given
        var language = "Java";
        var lastUpdatedAt = LocalDate.now().minusDays(10);
        var baseUrl = "https://api.github.com";
        var uri = baseUrl + "/search/repositories?q=language:" + language + "%20created:%3E" + lastUpdatedAt;
        server.expect(requestTo(uri)).andRespond(MockRestResponseCreators.withServerError());
        // when
        // then
        Assertions.assertThrows(RuntimeException.class, () -> client.getRepositoryInfo(language, lastUpdatedAt));
    }

}