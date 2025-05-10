package com.lynas.redcare.service;

import com.lynas.redcare.dto.GithubRepositoryApiResponseDto;
import com.lynas.redcare.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class GithubClientService {

    private final RestClient restClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(GithubClientService.class);

    public GithubRepositoryApiResponseDto getRepositoryInfo(String language, LocalDate lastUpdatedAt) {
        var uri = "/search/repositories?q=language:" + language + " created:>" + lastUpdatedAt;
        LOGGER.info(uri);
        return restClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(clientHttpResponse -> {
                    if (clientHttpResponse.getStatusCode().is4xxClientError()) {
                        throw new ClientException(
                                clientHttpResponse.getStatusCode().value(),
                                "Repository not found with language " + language + " and date: " + lastUpdatedAt
                        );
                    } else if (clientHttpResponse.getStatusCode().is5xxServerError()) {
                        throw new RuntimeException("Server error: " + clientHttpResponse.getStatusCode().value());
                    }
                    return true;
                })
                .body(GithubRepositoryApiResponseDto.class);
    }

}
