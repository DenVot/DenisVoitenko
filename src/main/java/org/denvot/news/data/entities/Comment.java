package org.denvot.news.data.entities;

public class Comment {
  private final CommentId id;
  private final ArticleId articleId;
  private final String text;

  public Comment(CommentId id, ArticleId articleId, String text) {
    this.id = id;
    this.articleId = articleId;
    this.text = text;
  }
}
