package com.qr_audio.backend.dto;

import java.time.LocalDateTime;

public record ArtifactResponse(
        Long id,
        String name,
        String description,
        String imageUrl,
        String languageCode,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
