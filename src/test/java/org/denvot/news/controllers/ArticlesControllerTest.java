package org.denvot.news.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.denvot.Application;
import org.denvot.news.data.services.ArticleRepository;
import org.denvot.news.data.services.HashSetArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spark.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

class ApiPipelineTest {
  private Service service;

  @BeforeEach
  void beforeEach() {
    service = Service.ignite();
  }

  @BeforeEach
  void afterEach() {
    service.stop();
    service.awaitStop();
  }

  @Test
  public void test() throws IOException, InterruptedException {
    var articlesRepo = new HashSetArticleRepository();
    var http = HttpClient.newHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();
    Application application = new Application(
        List.of(
            new ArticlesController(service, articlesRepo, objectMapper),
            new CommentsController(service, objectMapper, articlesRepo)));

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
                .uri(URI.create("http://localhost:%d/api/articles/0/comments".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );

    assertEquals(200, response.statusCode());

    response = http.send(
        HttpRequest.newBuilder()
            .PUT(
                HttpRequest.BodyPublishers.ofString("")
            )
            .uri(URI.create("http://localhost:%d/api/articles/0?newName=TestNew".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(UTF_8)
    );

    assertEquals(200, response.statusCode());

    response = http.send(
        HttpRequest.newBuilder()
            .DELETE()
            .uri(URI.create("http://localhost:%d/api/articles/0/comments/0".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(UTF_8)
    );

    assertEquals(200, response.statusCode());
  }
}