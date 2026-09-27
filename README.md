# 🔗 LinkPulse

> AI-powered bookmark & knowledge manager — save links, get auto-summaries, search your knowledge.

🚧 **Status:** Phase 1 — Foundation (Weekend 1)

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 3.3 |
| Database | PostgreSQL 16 |
| Migrations | Flyway |
| Containers | Docker Compose |

## Quick Start

### Prerequisites
- Java 21+
- Maven 3.9+
- Docker Desktop

### Run Locally

```bash
# 1. Start PostgreSQL
docker compose up -d

# 2. Run the application
mvn spring-boot:run

# 3. Test it
curl -X POST http://localhost:8080/api/bookmarks \
  -H "Content-Type: application/json" \
  -d '{"url": "https://spring.io", "title": "Spring Framework", "description": "Official Spring website"}'

# 4. Fetch all bookmarks
curl http://localhost:8080/api/bookmarks
```

### API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/bookmarks` | Create a bookmark |
| GET | `/api/bookmarks` | List all bookmarks |
| GET | `/api/bookmarks/{id}` | Get bookmark by ID |
| DELETE | `/api/bookmarks/{id}` | Delete a bookmark |

## Project Structure

```
src/main/java/com/linkpulse/
├── LinkPulseApplication.java      ← Entry point
├── bookmark/                      ← Feature package
│   ├── Bookmark.java              ← JPA Entity
│   ├── BookmarkRepository.java    ← Data access layer
│   ├── BookmarkService.java       ← Business logic
│   ├── BookmarkController.java    ← REST API endpoints
│   ├── CreateBookmarkRequest.java ← Request DTO
│   ├── BookmarkResponse.java      ← Response DTO
│   └── BookmarkNotFoundException.java
└── exception/
    └── GlobalExceptionHandler.java ← Centralized error handling
```

## Roadmap

- [x] Phase 1: REST API + PostgreSQL + Docker
- [ ] Phase 2: AI summaries + Message queue + Caching
- [ ] Phase 3: Elasticsearch + Monitoring + CI/CD
- [ ] Phase 4: React frontend + Browser extension

---

*Built as a learning project to master distributed systems, event-driven architecture, and modern Java.*
