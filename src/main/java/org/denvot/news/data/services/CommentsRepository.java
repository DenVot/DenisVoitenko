package org.denvot.news.data.services;

import org.denvot.news.data.entities.ArticleId;
import org.denvot.news.data.entities.Comment;
import org.denvot.news.data.entities.CommentId;

import java.util.List;

public interface CommentsRepository {
  Comment getComment(CommentId id);
  long createComment(ArticleId articleId, String text);
  void deleteComment(CommentId commentId);
  List<Comment> getCommentsByArticle(ArticleId articleId);
}
