package com.linkpulse.bookmark;

import com.linkpulse.exception.BookmarkNotFoundException;
import com.linkpulse.user.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository, UserRepository userRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public BookmarkResponse createBookmark(BookmarkRequest request, Long userId) {
        log.debug("Creating bookmark for userId {}: {}", userId, request);
        // Zero-query JPA proxy (attaches FK without SELECT)
        var authUserProxy = userRepository.getReferenceById(userId);
        var bookmark = Bookmark.builder()
                .url(request.url())
                .title(request.title())
                .description(request.description())
                .user(authUserProxy)
                .build(); // createdAt/updatedAt handled automatically by @CreationTimestamp
        var savedBookmark = bookmarkRepository.save(bookmark);
        return BookmarkResponse.from(savedBookmark);
    }

    @Transactional(readOnly = true)
    public List<BookmarkResponse> getBookmarkById(Long userId) {
        log.debug("Fetching bookmarks for userId: {}", userId);
        return bookmarkRepository.findAllByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(BookmarkResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookmarkResponse getBookmarkById(Long bookmarkId, Long userId) {
        log.debug("Fetching bookmarks for userId: {} and bookmarkId: {}", userId, bookmarkId);
        var bookmark = bookmarkRepository.findByIdAndUserId(bookmarkId, userId)
                .orElseThrow(() -> new BookmarkNotFoundException(bookmarkId));
        return BookmarkResponse.from(bookmark);
    }

    @Transactional
    public BookmarkResponse updateBookmarkById(long id, Long userId, @Valid UpdateBookmarkRequest request) {
        log.debug("Updating bookmark for userId: {} and bookmarkId: {}", userId, id);
        var bookmark = bookmarkRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BookmarkNotFoundException(id));
        // Update the bookmark fields
        bookmark.setTitle(request.title());
        bookmark.setDescription(request.description());
        bookmark.setUrl(request.url());
        return BookmarkResponse.from(bookmark);
    }
}
