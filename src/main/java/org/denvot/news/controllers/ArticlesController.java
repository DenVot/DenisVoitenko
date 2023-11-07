package org.denvot.news.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.denvot.news.controllers.requests.CreateArticleRequest;
import org.denvot.news.controllers.requests.CreateCommentRequest;
import org.denvot.news.controllers.responses.ArticleResponse;
import org.denvot.news.controllers.responses.CommentResponse;
import org.denvot.news.controllers.responses.ErrorResponse;
import org.denvot.news.data.entities.Article;
import org.denvot.news.data.entities.ArticleId;
import org.denvot.news.data.entities.CommentId;
import org.denvot.news.data.services.ArticleRepository;
import spark.Response;
import spark.Route;
import spark.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ArticlesController implements Controller {
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
    getComments();
    createComment();
    deleteComment();
  }

  private void getAllArticles() {
    sparkService.get("/articles", (request, response) -> {
      var articles = articleRepository.getAllArticles();

      setupSuccessJsonResponse(response);
      return objectMapper.writeValueAsString(articles);
    });
  }

  private void getArticle() {
    sparkService.get("/api/articles/:articleId", (request, response) -> {
      try {
        var articleId = new ArticleId(
                Long.parseLong(request.params("articleId")));

        var article = articleRepository.getArticle(articleId);

        if (article.isEmpty()) {
          return error("Article not found");
        }

        setupSuccessJsonResponse(response);
        return objectMapper.writeValueAsString(article);
      } catch (NumberFormatException e) {
        response.status(401);

        return error(e.getMessage());
      }
    });
  }

  private void createArticle() {
    sparkService.post("/api/articles", (request, response) -> {
      var body = request.body();
      var createArticleRequest = objectMapper.readValue(body, CreateArticleRequest.class);

      var tagsSet = new HashSet<>(Arrays.asList(createArticleRequest.tags()));
      try {
        var article = articleRepository.createArticle(createArticleRequest.name(), tagsSet);

        setupSuccessJsonResponse(response);
        return objectMapper.writeValueAsString(article);
      } catch (Exception e) {
        return error(e.getMessage());
      }
    });
  }

  private void deleteArticles() {
    sparkService.delete("/api/articles/:articleId", (request, response) -> {
      try {
        var articleId = new ArticleId(
                Long.parseLong(request.params("articleId")));

        articleRepository.deleteArticle(articleId);

        setupSuccessJsonResponse(response);

        return objectMapper.writeValueAsString("OK");
      } catch (NumberFormatException e) {
        response.status(401);

        return error(e.getMessage());
      }
    });
  }

  private void editArticle() {
    sparkService.put("/api/articles/:articleId/edit", (request, response) -> {
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

  private void getComments() {
    sparkService.get("/api/articles/:articleId/comments/", (request, response) -> {
      var id = new ArticleId(Long.parseLong(request.params("articleId")));
      var article = articleRepository.getArticle(id);

      if (article.isEmpty()) {
        response.status(401);
        return error("Article not found");
      }

      setupSuccessJsonResponse(response);
      return objectMapper.writeValueAsString(CommentResponse.fromOriginal(article.get().getComments()));
    });
  }

  private void createComment() {
    sparkService.post("/api/articles/:articleId/comments", (request, response) -> {
      var body = request.body();

      try {
        var createCommentRequest = objectMapper.readValue(body, CreateCommentRequest.class);
        var id = new ArticleId(Long.parseLong(request.params("articleId")));
        var article = articleRepository.getArticle(id);

        if (article.isEmpty()) {
          return error("Article not found");
        }

        setupSuccessJsonResponse(response);

        var comment = article.get().createComment(createCommentRequest.text());
        return objectMapper.writeValueAsString(CommentResponse.fromOriginal(comment));
      } catch (Exception e) {
        return error(e.getMessage());
      }
    });
  }

  private void deleteComment() {
    sparkService.delete("/api/articles/:articleId/comments/:commentId", (request, response) -> {
      var id = new ArticleId(Long.parseLong(request.params("articleId")));
      var article = articleRepository.getArticle(id);

      if (article.isEmpty()) {
        return error("Article not found");
      }

      setupSuccessJsonResponse(response);

      article.get().deleteComment(new CommentId(Long.parseLong(request.params("commentId"))));

      return "OK";
    });
  }

  private void setupSuccessJsonResponse(Response response) {
    response.status(200);
    response.type("application/json");
  }

  private String error(String msg) throws JsonProcessingException {
    return objectMapper.writeValueAsString(new ErrorResponse(msg));
  }
}
