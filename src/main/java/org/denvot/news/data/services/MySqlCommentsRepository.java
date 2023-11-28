package org.denvot.news.data.services;

import org.denvot.news.data.entities.ArticleId;
import org.denvot.news.data.entities.Comment;
import org.denvot.news.data.entities.CommentId;
import org.jdbi.v3.core.Jdbi;

import java.util.Map;

public class MySqlCommentsRepository implements CommentsRepository {
  private final Jdbi jdbi;

  public MySqlCommentsRepository(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  @Override
  public Comment getComment(CommentId id) {
    return parseCommentFromMap(
            jdbi.inTransaction(handle -> handle.select("SELECT * FROM comment WHERE id = :id")
                    .bind("id", id.getValue())
                    .mapToMap()
                    .first()));
  }

  @Override
  public long createComment(ArticleId articleId, String text) {
    return jdbi.inTransaction(handle -> (Long) handle.createUpdate(
              "INSERT INTO comment VALUES (:text)")
            .executeAndReturnGeneratedKeys()
            .mapToMap()
            .first().get("id"));
  }

  @Override
  public void deleteComment(CommentId commentId) {
    jdbi.inTransaction(handle -> {
      handle.createUpdate("DELETE FROM article WHERE id = :id")
              .bind("id", commentId.getValue())
              .executeAndReturnGeneratedKeys();

      return null;
    });
  }

  @Override
  public Comment[] getCommentsByArticle(ArticleId articleId) {
    return jdbi.inTransaction(handle -> (Comment[]) handle.select(
              "SELECT * FROM comment WHERE \"articleId\" = :articleId")
            .bind("articleId", articleId.getValue())
            .mapToMap()
            .stream()
            .map(MySqlCommentsRepository::parseCommentFromMap)
            .toArray());
  }

  private static Comment parseCommentFromMap(Map<String, Object> map) {
    return new Comment(new CommentId((Long) map.get("id")),
            new ArticleId((Long) map.get("articleId")),
            (String) map.get("text"));
  }
}
