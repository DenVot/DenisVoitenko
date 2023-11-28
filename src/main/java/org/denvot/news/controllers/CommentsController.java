package org.denvot.news.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.denvot.news.controllers.requests.CreateCommentRequest;
import org.denvot.news.controllers.responses.CommentResponse;
import org.denvot.news.controllers.responses.ErrorResponse;
import org.denvot.news.data.entities.ArticleId;
import org.denvot.news.data.entities.CommentId;
import org.denvot.news.data.services.ArticleRepository;
import spark.Response;
import spark.Service;

public class CommentsController implements ControllerBase {
  private final Service sparkService;
  private final ObjectMapper objectMapper;
  private final ArticleRepository articleRepository;

  public CommentsController(Service sparkService,
                            ObjectMapper objectMapper,
                            ArticleRepository articleRepository) {
    this.sparkService = sparkService;
    this.objectMapper = objectMapper;
    this.articleRepository = articleRepository;
  }

  @Override
  public void initializeEndpoints() {
    /*createComment();
    deleteComment();
    getComments();*/
  }
  /*
  private void createComment() {
    sparkService.post("/api/articles/:articleId/comments", (request, response) -> {
      response.type("application/json");
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
      response.type("application/json");
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

  private void getComments() {
    sparkService.get("/api/articles/:articleId/comments", (request, response) -> {
      response.type("application/json");
      var id = new ArticleId(Long.parseLong(request.params("articleId")));
      var article = articleRepository.getArticle(id);

      if (article.isEmpty()) {
        response.status(403);
        return error("Article not found");
      }

      setupSuccessJsonResponse(response);
      return objectMapper.writeValueAsString(CommentResponse.fromOriginal(article.get().getComments()));
    });
  }

  private void setupSuccessJsonResponse(Response response) {
    response.status(200);
  }

  private String error(String msg) throws JsonProcessingException {
    return objectMapper.writeValueAsString(new ErrorResponse(msg));
  }*/
}
