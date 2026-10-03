package com.qr_audio.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ArtifactRequest(
        @NotBlank(message = "Tên hiện vật không được để trống")
        @Size(max = 255, message = "Tên hiện vật không được dài quá 255 ký tự")
        String name,

        String description,

        @Size(max = 255, message = "URL ảnh không được dài quá 255 ký tự")
        String imageUrl,

        @Size(max = 10, message = "Mã ngôn ngữ không được dài quá 10 ký tự")
        String languageCode
) {
}
