package org.denvot.news.data.entities;

import org.denvot.news.controllers.responses.ArticleResponse;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class Article {
  private final ArticleId id;
  private final String name;
  private final Set<String> tags;
  private final List<Comment> comments;
  private final static AtomicLong nextCommentId = new AtomicLong(0);

  public Article(ArticleId id, String name, Set<String> tags, List<Comment> comments) {
    this.id = id;
    this.name = name;
    this.tags = tags;
    this.comments = comments;
  }

  public ArticleId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Set<String> getTags() {
    return tags;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public Comment createComment(String text) {
    Comment comment = new Comment(generateCommentUniqueId(), id, text);

    comments.add(comment);
    return comment;
  }

  public void deleteComment(CommentId commentId) {
    for (Comment comment : comments) {
      if (commentId.equals(comment.getId())) {
        comments.remove(comment);
        return;
      }
    }

    throw new RuntimeException("Comment not found");
  }

  public Article editName(String newName) {
    return new Article(id, newName, tags, comments);
  }

  public Article editTags(Set<String> newTags) {
    return new Article(id, name, newTags, comments);
  }

  private CommentId generateCommentUniqueId() {
    return new CommentId(nextCommentId.getAndIncrement());
  }
}
