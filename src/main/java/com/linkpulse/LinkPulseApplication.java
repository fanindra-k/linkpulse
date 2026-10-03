package com.linkpulse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * The entry point of the LinkPulse application.
 *
 * WHAT DOES @SpringBootApplication DO?
 * It's a shortcut that combines 3 annotations:
 *
 * 1. @Configuration    → This class can define beans (objects managed by Spring)
 * 2. @EnableAutoConfiguration → Spring looks at your dependencies (pom.xml)
 *    and automatically configures them. For example, since we have
 *    spring-boot-starter-web, it sets up an embedded Tomcat server.
 * 3. @ComponentScan    → Spring scans all packages under com.linkpulse
 *    for classes annotated with @Component, @Service, @Repository,
 *    @Controller, etc., and manages their lifecycle.
 *
 * HOW DOES SPRING MANAGE OBJECTS?
 * Instead of creating objects yourself (new BookmarkService()),
 * Spring creates them and "injects" them where needed. This is called
 * Dependency Injection (DI). It makes code easier to test and loosely coupled.
 *
 * You'll see this pattern throughout the project:
 *   - Controller needs Service → Spring injects it via constructor
 *   - Service needs Repository → Spring injects it via constructor
 */
@SpringBootApplication
@EnableJpaAuditing
public class LinkPulseApplication {

    public static void main(String[] args) {
        // This single line:
        // 1. Creates the Spring application context (the "container" for all beans)
        // 2. Runs Flyway migrations (updates database schema)
        // 3. Starts the embedded Tomcat server on port 8080
        // 4. Scans for controllers and registers API routes
        SpringApplication.run(LinkPulseApplication.class, args);
    }
}
