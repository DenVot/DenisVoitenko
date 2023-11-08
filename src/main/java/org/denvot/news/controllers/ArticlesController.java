package org.denvot.news.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.denvot.news.controllers.requests.CreateArticleRequest;
import org.denvot.news.controllers.responses.ArticleResponse;
import org.denvot.news.controllers.responses.CommentResponse;
import org.denvot.news.controllers.responses.ErrorResponse;
import org.denvot.news.data.entities.Article;
import org.denvot.news.data.entities.ArticleId;
import org.denvot.news.data.services.ArticleRepository;
import spark.Response;
import spark.Service;

import java.util.Arrays;
import java.util.HashSet;

public class ArticlesController implements ControllerBase {
  private final Service sparkService;
  private final ArticleRepository articleRepository;
  private final ObjectMapper objectMapper;

  public ArticlesController(Service sparkService,
                            ArticleRepository articleRepository,
                            ObjectMapper objectMapper) {
    this.sparkService = sparkService;
    this.articleRepository = articleRepository;
    this.objectMapper = objectMapper;
  }

  @Override
  public void initializeEndpoints() {
    getAllArticles();
    getArticle();
    createArticle();
    deleteArticles();
    editArticle();
  }

  private void getAllArticles() {
    sparkService.get("/api/articles", (request, response) -> {
      response.type("application/json");
      var articles = articleRepository.getAllArticles();

      setupSuccessJsonResponse(response);
      return objectMapper.writeValueAsString(ArticleResponse.fromOriginal(articles));
    });
  }

  private void getArticle() {
    sparkService.get("/api/articles/:articleId", (request, response) -> {
      response.type("application/json");
      try {
        var articleId = new ArticleId(
                Long.parseLong(request.params("articleId")));

        var article = articleRepository.getArticle(articleId);

        if (article.isEmpty()) {
          return error("Article not found");
        }

        setupSuccessJsonResponse(response);
        return objectMapper.writeValueAsString(ArticleResponse.fromOriginal(article.get()));
      } catch (NumberFormatException e) {
        response.status(403);

        return error(e.getMessage());
      }
    });
  }

  private void createArticle() {
    sparkService.post("/api/articles", (request, response) -> {
      response.type("application/json");
      var body = request.body();
      var createArticleRequest = objectMapper.readValue(body, CreateArticleRequest.class);

      var tagsSet = new HashSet<>(Arrays.asList(createArticleRequest.tags()));
      try {
        var article = articleRepository.createArticle(createArticleRequest.name(), tagsSet);

        setupSuccessJsonResponse(response);
        return objectMapper.writeValueAsString(ArticleResponse.fromOriginal(article));
      } catch (Exception e) {
        return error(e.getMessage());
      }
    });
  }

  private void deleteArticles() {
    sparkService.delete("/api/articles/:articleId", (request, response) -> {
      response.type("application/json");
      try {
        var articleId = new ArticleId(
                Long.parseLong(request.params("articleId")));

        articleRepository.deleteArticle(articleId);

        setupSuccessJsonResponse(response);

        return objectMapper.writeValueAsString("OK");
      } catch (NumberFormatException e) {
        response.status(403);

        return error(e.getMessage());
      }
    });
  }

  private void editArticle() {
    sparkService.put("/api/articles/:articleId/edit", (request, response) -> {
      response.type("application/json");
      var id = new ArticleId(Long.parseLong(request.params("articleId")));
      var newName = request.queryParams("newName");
      var newTagsStr = request.queryParams("newTags");
      Article editedArticle = null;

      if (newName != null) {
        editedArticle = articleRepository.editName(id, newName);
      }

      if (newTagsStr != null) {
        var newTags = new HashSet<>(Arrays.asList(newTagsStr.split(",")));
        editedArticle = articleRepository.editTags(id, newTags);
      }

      if (editedArticle == null) {
        return error("No deltas found");
      }

      response.status(200);

      return objectMapper.writeValueAsString(ArticleResponse.fromOriginal(editedArticle));
    });
  }

  private void setupSuccessJsonResponse(Response response) {
    response.status(200);
  }

  private String error(String msg) throws JsonProcessingException {
    return objectMapper.writeValueAsString(new ErrorResponse(msg));
  }
}
