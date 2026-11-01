package com.linkpulse.bookmark;

import com.linkpulse.utils.ValidationUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

@Schema(description = "Fields to partially update on a bookmark")
public record UpdateBookmarkRequest(
        @URL(message = "Must be a valid URL")
        @Size(max = 2048, message = "URL must be under 2048 characters")
        String url,
        @Size(max = 500, message = "Title must be under 500 characters")
        String title,
        @Size(max = 5000, message = "Description must be under 5000 characters")
        String description

) {
    public UpdateBookmarkRequest {
        url = ValidationUtils.normalizeUrl(url);
        title = ValidationUtils.normalizeText(title);
        description = ValidationUtils.normalizeText(description);
    }
}
