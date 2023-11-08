package org.denvot.news.controllers.responses;

import org.denvot.news.data.entities.Article;
import org.denvot.news.data.entities.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record ArticleResponse(long id, String name, Set<String> tags,
                              List<CommentResponse> comments) {
  public static ArticleResponse fromOriginal(Article article) {
    var comments = new ArrayList<CommentResponse>();

    for (Comment comment : article.getComments()) {
      comments.add(CommentResponse.fromOriginal(comment));
    }

    return new ArticleResponse(article.getId().getValue(), article.getName(), article.getTags(), comments);
  }
}