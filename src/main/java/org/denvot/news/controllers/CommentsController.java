package org.denvot.news.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.denvot.news.controllers.requests.CreateCommentRequest;
import org.denvot.news.controllers.responses.CommentResponse;
import org.denvot.news.controllers.responses.ErrorResponse;
import org.denvot.news.data.entities.ArticleId;
import org.denvot.news.data.entities.CommentId;
import org.denvot.news.data.services.BaseCommentsService;
import spark.Response;
import spark.Service;

public class CommentsController implements ControllerBase {
  private final Service sparkService;
  private final ObjectMapper objectMapper;
  private final BaseCommentsService commentsService;

  public CommentsController(Service sparkService,
                            ObjectMapper objectMapper,
                            BaseCommentsService commentsService) {
    this.sparkService = sparkService;
    this.objectMapper = objectMapper;
    this.commentsService = commentsService;
  }

  @Override
  public void initializeEndpoints() {
    createComment();
    deleteComment();
    getComments();
  }

  private void createComment() {
    sparkService.post("/api/articles/:articleId/comments", (request, response) -> {
      response.type("application/json");
      var body = request.body();

      try {
        var createCommentRequest = objectMapper.readValue(body, CreateCommentRequest.class);
        var id = new ArticleId(Long.parseLong(request.params("articleId")));

        var commentId = commentsService.createComment(id, createCommentRequest.text());

        setupSuccessJsonResponse(response);

        return objectMapper.writeValueAsString(commentId);
      } catch (Exception e) {
        return error(e.getMessage());
      }
    });
  }

  private void deleteComment() {
    sparkService.delete("/api/comments/:commentId", (request, response) -> {
      response.type("application/json");
      var id = new CommentId(Long.parseLong(request.params("commentId")));

      commentsService.deleteComment(id);

      setupSuccessJsonResponse(response);

      return "OK";
    });
  }

  private void getComments() {
    sparkService.get("/api/articles/:articleId/comments", (request, response) -> {
      response.type("application/json");
      var id = new ArticleId(Long.parseLong(request.params("articleId")));
      var comments = commentsService.getCommentsByArticle(id);

      setupSuccessJsonResponse(response);
      return objectMapper.writeValueAsString(CommentResponse.fromOriginal(comments));
    });
  }

  private void setupSuccessJsonResponse(Response response) {
    response.status(200);
  }

  private String error(String msg) throws JsonProcessingException {
    return objectMapper.writeValueAsString(new ErrorResponse(msg));
  }
}
