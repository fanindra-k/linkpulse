package com.linkpulse.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    // Return List<Bookmark> directly, ordered by newest first:
    List<Bookmark> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<Bookmark> findByIdAndUserId(Long id, Long userId);
}
