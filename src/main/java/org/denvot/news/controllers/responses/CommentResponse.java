package org.denvot.news.controllers.responses;

import org.denvot.news.data.entities.Comment;

import java.util.ArrayList;
import java.util.List;

public record CommentResponse (long id, String text) {
  public static CommentResponse fromOriginal(Comment comment) {
    return new CommentResponse(comment.getId().getValue(), comment.getText());
  }

  public static List<CommentResponse> fromOriginal(List<Comment> comments) {
    var commentsResponses = new ArrayList<CommentResponse>();

    for (Comment comment : comments) {
      commentsResponses.add(fromOriginal(comment));
    }

    return commentsResponses;
  }
}
