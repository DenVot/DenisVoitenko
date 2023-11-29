package org.denvot.news.data.services;

import org.denvot.news.data.entities.ArticleId;
import org.denvot.news.data.entities.Comment;
import org.denvot.news.data.entities.CommentId;

import java.sql.SQLException;
import java.util.List;

public interface BaseCommentsService {
  long createComment(ArticleId articleId, String text) throws SQLException;
  void deleteComment(CommentId commentId) throws SQLException;
  List<Comment> getCommentsByArticle(ArticleId articleId);
}
