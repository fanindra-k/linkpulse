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

    @PostMapping
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

    @GetMapping
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
        var response = bookmarkService.getBookmarkById(authUser.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a bookmark by ID",
            description = "Returns a specific bookmark owned by the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bookmark retrieved"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid bearer token"),
            @ApiResponse(responseCode = "404", description = "Bookmark not found")
    })
    public ResponseEntity<BookmarkResponse> getBookmarkById(
            @PathVariable long id,
            @AuthenticationPrincipal AuthUser authUser
    ){
        log.info("GET /bookmarks/{id}, userId: {}", authUser.getId());
        var response = bookmarkService.getBookmarkById(id, authUser.getId());
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Update a Bookmark by Id",
            description = "Updates a specific bookmark owned by the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bookmark retrieved"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid bearer token"),
            @ApiResponse(responseCode = "404", description = "Bookmark not found")
    })

    @PutMapping("/{id}")
    public ResponseEntity<BookmarkResponse> updateBookmark(
            @PathVariable long id,
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody UpdateBookmarkRequest request
    ) {
        log.info("PUT /bookmarks/{id}, userId: {}", authUser.getId());
        var response = bookmarkService.updateBookmarkById(id, authUser.getId(), request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
