package com.linkpulse.bookmark;

import com.linkpulse.utils.ValidationUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

@Schema(description = "Bookmark details to save for the authenticated user")
public record BookmarkRequest(
        @NotBlank(message = "URL is required")
        @URL(message = "Must be a valid URL")
        @Size(max = 2048, message = "URL must be under 2048 characters")
        @Schema(description = "Absolute URL to bookmark", example = "https://spring.io/projects/spring-boot", format = "uri", maxLength = 2048)
        String url,
        
        @NotBlank(message = "Title is required")
        @Size(max = 500, message = "Title must be under 500 characters")
        @Schema(description = "Human-readable bookmark title", example = "Spring Boot", maxLength = 500)
        String title,
        @Size(max = 5000, message = "Description must be under 1000 characters")
        @Schema(description = "Optional notes or summary", example = "Documentation for building Spring applications.", maxLength = 5000)
        String description

) {
    public BookmarkRequest {
        url = ValidationUtils.normalizeUrl(url);
        title = ValidationUtils.normalizeText(title);
        description = ValidationUtils.normalizeText(description);
    }
}
