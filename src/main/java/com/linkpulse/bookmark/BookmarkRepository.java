package com.linkpulse.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findById(long id);

    Optional<List<Bookmark>> findByUserId(Long userId);

    // Return List<Bookmark> directly, ordered by newest first:
    List<Bookmark> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
