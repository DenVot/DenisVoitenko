package org.denvot.news.data.services;

import org.denvot.news.data.entities.ArticleId;
import org.denvot.news.data.entities.Comment;
import org.denvot.news.data.entities.CommentId;

public interface CommentsRepository {
  long createComment(ArticleId articleId, String text);
  void deleteComment(CommentId commentId);
  Comment[] getCommentsByArticle(ArticleId articleId);
}
