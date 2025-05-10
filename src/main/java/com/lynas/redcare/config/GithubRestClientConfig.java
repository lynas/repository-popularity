package com.lynas.redcare.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

@Configuration
public class GithubRestClientConfig {
    @Value("${app.score.github-api-base-url}")
    private String apiBaseUrl;

    @Bean
    public RestClient githubRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl(apiBaseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .build();
    }
}
