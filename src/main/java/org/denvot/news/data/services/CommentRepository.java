package org.denvot.news.data.services;

import org.denvot.news.data.entities.Article;
import org.denvot.news.data.entities.ArticleId;
import org.denvot.news.data.entities.Comment;
import org.denvot.news.data.entities.CommentId;

import java.util.Optional;

public interface CommentRepository {
  Comment createComment(String text, Article writtenTo);

  Optional<Comment> getComment(CommentId id);

  void deleteComment(CommentId id);
}
