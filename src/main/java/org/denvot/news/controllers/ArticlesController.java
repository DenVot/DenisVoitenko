package org.denvot.news.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.denvot.news.controllers.requests.CreateArticleRequest;
import org.denvot.news.controllers.responses.ArticleResponse;
import org.denvot.news.controllers.responses.ErrorResponse;
import org.denvot.news.data.entities.Article;
import org.denvot.news.data.entities.ArticleId;
import org.denvot.news.data.services.BaseArticleService;
import org.denvot.news.data.services.entities.ArticleData;
import org.denvot.news.exceptions.ArticleNotFoundException;
import org.eclipse.jetty.http.HttpStatus;
import spark.Response;
import spark.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArticlesController implements ControllerBase {
  private final Service sparkService;
  private final BaseArticleService articleService;
  private final ObjectMapper objectMapper;

  public ArticlesController(Service sparkService,
                            BaseArticleService articleService,
                            ObjectMapper objectMapper) {
    this.sparkService = sparkService;
    this.articleService = articleService;
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
      var articles = articleService.getAllArticles();

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

        var article = articleService.getArticle(articleId);

        setupSuccessJsonResponse(response);
        return objectMapper.writeValueAsString(ArticleResponse.fromOriginal(article));
      } catch (NumberFormatException | ArticleNotFoundException e) {
        response.status(HttpStatus.BAD_REQUEST_400);

        return error(e.getMessage());
      }
    });
  }

  private void createArticle() {
    sparkService.post("/api/articles", (request, response) -> {
      response.type("application/json");
      var body = request.body();
      var createArticleRequest = objectMapper.readValue(body, CreateArticleRequest.class);
      try {
        var articleId = articleService.createArticle(new ArticleData(createArticleRequest.name(), createArticleRequest.tags()));

        setupSuccessJsonResponse(response);
        return objectMapper.writeValueAsString(articleId);
      } catch (Exception e) {
        return error(e.getMessage());
      }
    });
  }

  private void createArticles() {
    sparkService.post("/api/articles", (request, response) -> {
      response.type("application/json");
      var body = request.body();
      var createArticleRequest = objectMapper.readValue(body, CreateArticleRequest[].class);
      try {
        List<ArticleData> datas = Arrays.stream(createArticleRequest).map(req -> new ArticleData(req.name(), req.tags())).toList();
        var articleId = articleService.createArticles(datas);

        setupSuccessJsonResponse(response);
        return objectMapper.writeValueAsString(articleId);
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

        articleService.deleteArticle(articleId);

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
        editedArticle = articleService.editName(id, newName);
      }

      if (newTagsStr != null) {
        editedArticle = articleService.editTags(id, newTagsStr.split(","));
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
