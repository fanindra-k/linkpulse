package com.linkpulse.bookmark;

import com.linkpulse.user.AuthUser;
import com.linkpulse.user.UserRepository;
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

    public BookmarkResponse createBookmark(BookmarkRequest dto) {
        log.debug("Creating bookmark: {}", dto);
        AuthUser authUser = userRepository.findById(dto.id()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        var bookmark = Bookmark.builder()
                .url(dto.url())
                .title(dto.title())
                .description(dto.description())
                .user(authUser)
                .build();
        var savedBookmark = bookmarkRepository.save(bookmark);
        log.debug("Bookmark created: {}", savedBookmark);
        return BookmarkResponse.from(savedBookmark);
    }
}
