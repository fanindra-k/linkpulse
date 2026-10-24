package com.linkpulse.bookmark;

import com.linkpulse.user.AuthUser;
import com.linkpulse.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


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
    public BookmarkResponse createBookmark(BookmarkRequest request, Long id) {
        log.debug("Creating bookmark: {}", request);
//        AuthUser authUser = userRepository.findById(dto.id()).orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 1. Get proxy without hitting DB
        var authUserProxy = userRepository.getReferenceById(id);

        // 2. Build bookmark
        var bookmark = Bookmark.builder()

                .url(request.url())
                .title(request.title())
                .description(request.description())
                .user(authUserProxy)
                .build();

        // 3. Single INSERT into DB
        var savedBookmark = bookmarkRepository.save(bookmark);
        log.debug("Bookmark created: {}", savedBookmark);
        return BookmarkResponse.from(savedBookmark);
    }
}
