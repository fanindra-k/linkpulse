//package com.linkpulse.bookmark_ai;
//
//import java.time.Instant;
//
///**
// * DTO for returning bookmark data in API responses.
// *
// * WHY A SEPARATE RESPONSE DTO?
// * The response shape might differ from both the request and the entity:
// * - Request doesn't have id or timestamps (client doesn't set these)
// * - Entity might have internal fields we don't want to expose
// * - Response is our API "contract" — it stays stable even if we
// *   refactor the database schema
// *
// * INTERVIEW TIP:
// * When asked "How do you design APIs?", mention the DTO pattern.
// * It shows you understand separation of concerns:
// *   Controller ↔ DTO ↔ Service ↔ Entity ↔ Repository ↔ Database
// */
//public record BookmarkResponse(
//        Long id,
//        String url,
//        String title,
//        String description,
//        Instant createdAt,
//        Instant updatedAt
//) {
//    /**
//     * Factory method: converts an Entity to a Response DTO.
//     *
//     * WHY A STATIC FACTORY METHOD (not a constructor)?
//     * 1. The name "from" clearly shows it's a conversion
//     * 2. It keeps conversion logic in one place
//     * 3. It reads well: BookmarkResponse.from(entity)
//     *
//     * In larger projects, you might use a library like MapStruct
//     * for automatic Entity ↔ DTO mapping. But for learning,
//     * manual mapping helps you understand what's happening.
//     */
//    public static BookmarkResponse from(Bookmark bookmark) {
//        return new BookmarkResponse(
//                bookmark.getId(),
//                bookmark.getUrl(),
//                bookmark.getTitle(),
//                bookmark.getDescription(),
//                bookmark.getCreatedAt(),
//                bookmark.getUpdatedAt()
//        );
//    }
//}
