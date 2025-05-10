package com.lynas.redcare.dto;

import java.util.List;

public record RepositoryScoreResponse(
        List<RepositoryScoreDto> data
) {
}
