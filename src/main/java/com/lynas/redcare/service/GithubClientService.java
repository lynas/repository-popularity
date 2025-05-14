package com.lynas.redcare.service;

import com.lynas.redcare.dto.GithubRepositoryApiResponseDto;
import com.lynas.redcare.exception.ClientHttpResponseExceptionHandler;
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
        /*var uri = UriComponentsBuilder.newInstance()
                .path("/search/repositories?q=language:")
                .path(language)
                .path(" created:>")
                .path(lastUpdatedAt.toString())
                .toUriString();*/
        // TODO URL encoded value does not work and return bad request from GithubAPI
        // TODO e.g. /search/repositories%3Fq=language:Java%20created:%3E2025-04-01 returns 404 NOT_FOUND
        var uri = "/search/repositories?q=language:" + language + " created:>" + lastUpdatedAt;
        LOGGER.info(uri);
        return restClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(ClientHttpResponseExceptionHandler::handleException)
                .body(GithubRepositoryApiResponseDto.class);
    }

}
