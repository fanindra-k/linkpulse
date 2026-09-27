package com.linkpulse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Smoke test: Just checks that the application context loads.
 *
 * WHAT IS A SMOKE TEST?
 * It's the simplest possible test — "does the app start?"
 * If Spring can't wire all dependencies, this test fails.
 *
 * WHY IT MATTERS:
 * Catches configuration errors early:
 * - Missing beans
 * - Wrong database URL
 * - Invalid YAML syntax
 * - Incompatible dependency versions
 *
 * You'll add real unit tests and integration tests in Weekend 8.
 * For now, this ensures your project is healthy.
 *
 * NOTE: This test needs a running PostgreSQL database
 * (from Docker Compose) because it loads the full application context
 * including database connection. In Weekend 8, you'll learn about
 * Testcontainers which spins up a database automatically for tests.
 */
@SpringBootTest
class LinkPulseApplicationTests {

    @Test
    void contextLoads() {
        // If we get here without exceptions, the app starts successfully.
        // Spring loaded all beans, Flyway ran migrations, JPA validated entities.
    }
}
