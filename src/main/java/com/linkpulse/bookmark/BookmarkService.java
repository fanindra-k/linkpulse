package com.linkpulse.bookmark;

import com.linkpulse.user.AuthUser;
import com.linkpulse.user.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.stereotype.Service;

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
    public BookmarkResponse createBookmark(BookmarkRequest request, Long id) {
        return null;
    }

    public List<BookmarkResponse> getBookmark(Long userId) {
        var bookmarks = bookmarkRepository.findByUserId(userId).orElseThrow( () -> new OpenApiResourceNotFoundException("Bookmarks not found for user: " + userId));
         return bookmarks.stream()
                .map(BookmarkResponse::from)
                .toList();
    }
}
