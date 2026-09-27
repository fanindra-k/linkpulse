package com.linkpulse.bookmark;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record BookmarkRequestDto(
        @NotBlank(message = "URL is required")
        @URL(message = "Must be a valid URL")
        @Size(max = 2048, message = "URL must be under 2048 characters")
        String url,

        @NotBlank(message = "Title is required")
        @Size(max = 500, message = "Title must be under 500 characters")
        String title,
        @Size(max = 5000, message = "Description must be under 1000 characters")
        String description

) {
    public BookmarkRequestDto {
        url = normalizeUrl(url);
        title = normalizeText(title);
        description = normalizeText(description);
    }

    private static String normalizeUrl(String value) {
        if (value == null) {
            return null;
        }

        return value
                .trim()
                .replaceAll("[\\p{Cntrl}&&[^\\r\\n\\t]]+", "")
                .replaceAll("\\s+", "");
    }

    private static String normalizeText(String value) {
        if (value == null) {
            return null;
        }

        return value
                .replaceAll("[\\p{Cntrl}&&[^\\r\\n\\t]]+", " ")
                .trim()
                .replaceAll("\\s+", " ");
    }
}
