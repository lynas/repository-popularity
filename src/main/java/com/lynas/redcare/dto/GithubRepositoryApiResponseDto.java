package com.lynas.redcare.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubRepositoryApiResponseDto(
        List<RepositoryDetailsDto> items
) {
}

