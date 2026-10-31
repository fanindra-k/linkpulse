package com.linkpulse.bookmark;

import com.linkpulse.user.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmarks")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Bookmarks", description = "Create and retrieve bookmarks belonging to the authenticated user")
@Slf4j
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Autowired
    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @PostMapping("/create-bookmark")
    @Operation(
            summary = "Create a bookmark",
            description = "Creates a bookmark for the authenticated user. The owner is determined from the bearer token."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Bookmark created"),
            @ApiResponse(responseCode = "400", description = "Request validation failed"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid bearer token")
    })
    public ResponseEntity<BookmarkResponse> createBookmark(
            @Valid @RequestBody BookmarkRequest dto,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        log.info("POST /bookmarks, {}", dto);
        BookmarkResponse response = bookmarkService.createBookmark(dto, authUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-bookmarks")
    @Operation(
            summary = "List my bookmarks",
            description = "Returns bookmarks owned by the authenticated user. No user ID is required in the request."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bookmarks retrieved"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid bearer token")
    })
    public ResponseEntity<List<BookmarkResponse>> getBookmark(@AuthenticationPrincipal AuthUser authUser) {
        log.info("GET /bookmarks for user, {}", authUser.getId());
        var response = bookmarkService.getBookmark(authUser.getId());
        return ResponseEntity.ok(response);
    }
}
