package com.linkpulse.bookmark;

import com.linkpulse.utils.ValidationUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record BookmarkRequest(
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
    public BookmarkRequest {
        url = ValidationUtils.normalizeUrl(url);
        title = ValidationUtils.normalizeText(title);
        description = ValidationUtils.normalizeText(description);
    }
}
