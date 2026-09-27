//package com.linkpulse.bookmark_ai;
//
//import jakarta.validation.Valid;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * REST Controller: The entry point for all bookmark-related HTTP requests.
// *
// * HOW REST CONTROLLERS WORK:
// * 1. Client sends an HTTP request (e.g., POST /api/bookmarks)
// * 2. Spring matches the URL + method to a handler method below
// * 3. Spring deserializes the JSON body into a Java object
// * 4. Spring calls the handler method
// * 5. Spring serializes the return value back to JSON
// * 6. Spring sends the HTTP response
// *
// * ANNOTATIONS EXPLAINED:
// * @RestController = @Controller + @ResponseBody
// *   - @Controller: this class handles HTTP requests
// *   - @ResponseBody: return values are serialized to JSON (not HTML)
// *
// * @RequestMapping("/bookmarks"):
// *   All endpoints in this controller start with /bookmarks.
// *   Combined with server.servlet.context-path=/api in application.yml,
// *   the full URL becomes: http://localhost:8080/api/bookmarks
// *
// * REST CONVENTIONS:
// *   POST   /bookmarks      → create a new bookmark (returns 201)
// *   GET    /bookmarks      → list all bookmarks (returns 200)
// *   GET    /bookmarks/{id} → get one bookmark (returns 200 or 404)
// *   PUT    /bookmarks/{id} → update a bookmark (you'll add this)
// *   DELETE /bookmarks/{id} → delete a bookmark (returns 204 or 404)
// */
//@RestController
//@RequestMapping("/bookmarks")
//public class BookmarkController {
//
//    private static final Logger log = LoggerFactory.getLogger(BookmarkController.class);
//
//    private final BookmarkService bookmarkService;
//
//    // Constructor injection — same pattern as the Service class.
//    // Spring creates this controller and injects the BookmarkService.
//    public BookmarkController(BookmarkService bookmarkService) {
//        this.bookmarkService = bookmarkService;
//    }
//
//    /**
//     * POST /api/bookmarks — Create a new bookmark.
//     *
//     * @param request the bookmark data from the client (JSON body)
//     * @return the created bookmark with 201 CREATED status
//     *
//     * ANNOTATIONS:
//     * @PostMapping → handles HTTP POST requests
//     * @RequestBody → tells Spring to deserialize the JSON body
//     *                into a CreateBookmarkRequest object
//     * @Valid       → triggers validation (checks @NotBlank, @URL, etc.)
//     *                If validation fails, Spring returns 400 Bad Request
//     *                BEFORE this method is even called.
//     *
//     * WHY ResponseEntity<> INSTEAD OF JUST RETURNING THE OBJECT?
//     * ResponseEntity lets us control the HTTP status code.
//     * - Just returning an object → always 200 OK
//     * - ResponseEntity → we can return 201 CREATED (correct for POST)
//     *
//     * HTTP STATUS CODES TO KNOW:
//     * 200 OK         → successful GET, PUT
//     * 201 CREATED    → successful POST (new resource created)
//     * 204 NO CONTENT → successful DELETE (nothing to return)
//     * 400 BAD REQUEST → validation failed
//     * 404 NOT FOUND  → resource doesn't exist
//     * 500 INTERNAL SERVER ERROR → something broke (our bug)
//     */
//    @PostMapping
//    public ResponseEntity<BookmarkResponse> createBookmark(
//            @Valid @RequestBody CreateBookmarkRequest request
//    ) {
//        log.info("POST /bookmarks — url={}", request.url());
//
//        BookmarkResponse response = bookmarkService.createBookmark(request);
//
//        // Return 201 Created with the bookmark in the body
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
//
//    /**
//     * GET /api/bookmarks — List all bookmarks.
//     *
//     * WHAT YOU'LL IMPROVE LATER:
//     * Weekend 2: Add pagination → GET /bookmarks?page=0&size=20
//     * Weekend 2: Add sorting   → GET /bookmarks?sort=createdAt,desc
//     * Weekend 2: Add filtering → GET /bookmarks?tag=java
//     */
//    @GetMapping
//    public ResponseEntity<List<BookmarkResponse>> getAllBookmarks() {
//        log.info("GET /bookmarks");
//
//        List<BookmarkResponse> bookmarks = bookmarkService.getAllBookmarks();
//
//        return ResponseEntity.ok(bookmarks);
//    }
//
//    /**
//     * GET /api/bookmarks/{id} — Get a specific bookmark.
//     *
//     * @PathVariable extracts the {id} from the URL.
//     * Example: GET /api/bookmarks/42 → id = 42
//     */
//    @GetMapping("/{id}")
//    public ResponseEntity<BookmarkResponse> getBookmarkById(@PathVariable Long id) {
//        log.info("GET /bookmarks/{}", id);
//
//        BookmarkResponse response = bookmarkService.getBookmarkById(id);
//
//        return ResponseEntity.ok(response);
//    }
//
//    /**
//     * DELETE /api/bookmarks/{id} — Delete a bookmark.
//     *
//     * Returns 204 No Content on success (nothing to send back).
//     * Returns 404 Not Found if the bookmark doesn't exist
//     * (handled by GlobalExceptionHandler).
//     */
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteBookmark(@PathVariable Long id) {
//        log.info("DELETE /bookmarks/{}", id);
//
//        bookmarkService.deleteBookmark(id);
//
//        // 204 No Content — standard for successful DELETE
//        return ResponseEntity.noContent().build();
//    }
//
//    // =================================================================
//    // EXERCISE FOR YOU (Weekend 1, Hour 5):
//    // =================================================================
//    // Add a PUT /api/bookmarks/{id} endpoint to update a bookmark.
//    //
//    // Steps:
//    // 1. Create an UpdateBookmarkRequest record (similar to Create)
//    // 2. Add updateBookmark() method in BookmarkService
//    // 3. Add a @PutMapping("/{id}") method here
//    //
//    // Think about: What fields should be updatable?
//    // Should the URL be changeable, or only title/description?
//    // =================================================================
//}
