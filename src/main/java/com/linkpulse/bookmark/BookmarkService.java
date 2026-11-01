package com.linkpulse.bookmark;

import com.linkpulse.user.UserRepository;
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
    public List<BookmarkResponse> getBookmark(Long userId) {
        log.debug("Fetching bookmarks for userId: {}", userId);
        return bookmarkRepository.findAllByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(BookmarkResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookmarkResponse getBookmark(Long bookmarkId,Long userId) {
        log.debug("Fetching bookmarks for userId: {} and bookmarkId: {}", userId, bookmarkId);
        var bookmark = bookmarkRepository.findByUserIdAndBookmarkId(userId, bookmarkId);
        return BookmarkResponse.from(bookmark);
    }
}
