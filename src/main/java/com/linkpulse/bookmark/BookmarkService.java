package com.linkpulse.bookmark;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    public BookmarkResponse createBookmark(BookmarkRequestDto dto) {
        log.debug("Creating bookmark: {}", dto);
        var bookmark = Bookmark.builder()
                .url(dto.url())
                .title(dto.title())
                .description(dto.description())
                .build();
        var savedBookmark = bookmarkRepository.save(bookmark);
        log.debug("Bookmark created: {}", savedBookmark);
        return BookmarkResponse.from(savedBookmark);
    }
}
