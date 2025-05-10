package com.lynas.redcare.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RepositoryDetailsDto(
        String name,
        String language,
        @JsonProperty("stargazers_count")
        Integer starCount,
        @JsonProperty("forks_count")
        Integer forkCount,
        @JsonProperty("created_at")
        String createdAt,
        @JsonProperty("updated_at")
        String updatedAt,
        @JsonProperty("html_url")
        String url
) {
}
