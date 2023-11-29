package org.denvot.news.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.denvot.Application;
import org.denvot.news.controllers.responses.ArticleResponse;
import org.denvot.news.data.services.*;
import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import spark.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class ApiPipelineTest {
  @Container
  public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13");

  private static Jdbi jdbi;

  @BeforeAll
  static void beforeAll() {
    String postgresJdbcUrl = POSTGRES.getJdbcUrl();
    Flyway flyway =
            Flyway.configure()
                    .outOfOrder(true)
                    .locations("classpath:db/migrations")
                    .dataSource(postgresJdbcUrl, POSTGRES.getUsername(), POSTGRES.getPassword())
                    .load();
    flyway.migrate();
    jdbi = Jdbi.create(postgresJdbcUrl, POSTGRES.getUsername(), POSTGRES.getPassword());
  }

  @BeforeEach
  void beforeEach() {
    service = Service.ignite();
    jdbi.useTransaction(handle -> handle.createUpdate("DELETE FROM article").execute());
    jdbi.useTransaction(handle -> handle.createUpdate("DELETE FROM comment").execute());
  }

  private Service service;

  @BeforeEach
  void afterEach() {
    service.stop();
    service.awaitStop();
  }

  @Test
  public void test() throws IOException, InterruptedException {
    ArticlesRepository articleRepository = new MySqlArticleRepository(jdbi);
    CommentsRepository commentsRepository = new MySqlCommentsRepository(jdbi);
    BaseArticleService articleService = new ArticleService(articleRepository);
    CommentsService commentsService = new CommentsService(articleRepository, commentsRepository);

    var http = HttpClient.newHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();
    Application application = new Application(
        List.of(
            new ArticlesController(service, articleService, objectMapper),
            new CommentsController(service, objectMapper, commentsService)));

    application.start();
    service.awaitInitialization();

    HttpResponse<String> response = http.send(
            HttpRequest.newBuilder()
                .POST(
                    HttpRequest.BodyPublishers.ofString(
                        """
                            { "name": "Test", "tags": ["Tag1", "Tag2"] }"""
                    )
                )
                .uri(URI.create("http://localhost:%d/api/articles".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );

    assertEquals(200, response.statusCode());

    response = http.send(
            HttpRequest.newBuilder()
                .POST(
                    HttpRequest.BodyPublishers.ofString(
                        """
                            { "text": "Test"}"""
                    )
                )
                .uri(URI.create("http://localhost:%d/api/articles/1/comments".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );

    assertEquals(200, response.statusCode());

    response = http.send(
        HttpRequest.newBuilder()
            .PUT(
                HttpRequest.BodyPublishers.ofString("")
            )
            .uri(URI.create("http://localhost:%d/api/articles/1/edit?newName=TestNew".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(UTF_8)
    );

    assertEquals(200, response.statusCode());

    response = http.send(
        HttpRequest.newBuilder()
            .DELETE()
            .uri(URI.create("http://localhost:%d/api/comments/1".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(UTF_8)
    );

    assertEquals(200, response.statusCode());

    response = http.send(
            HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://localhost:%d/api/articles/1".formatted(service.port())))
                    .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
    );

    assertEquals(200, response.statusCode());

    var articleResponse = objectMapper.readValue(response.body(), ArticleResponse.class);

    assertEquals(0, articleResponse.commentsCount());
    assertEquals("TestNew", articleResponse.name());
  }
}