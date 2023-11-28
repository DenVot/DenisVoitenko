package org.denvot.news.data.services;

import org.denvot.news.data.entities.ArticleId;
import org.denvot.news.data.entities.Comment;
import org.denvot.news.data.entities.CommentId;

import java.sql.SQLException;

public class CommentsService implements BaseCommentsService {
  private final ArticlesRepository articlesRepository;
  private final CommentsRepository commentsRepository;

  public CommentsService(ArticlesRepository articlesRepository, CommentsRepository commentsRepository) {
    this.articlesRepository = articlesRepository;
    this.commentsRepository = commentsRepository;
  }
  
  @Override
  public long createComment(ArticleId articleId, String text) throws SQLException {
    var id = commentsRepository.createComment(articleId, text);
    var commentsCount = commentsRepository.getCommentsByArticle(articleId).length;

    articlesRepository.editTrending(articleId, commentsCount > 3);

    return id;
  }

  @Override
  public void deleteComment(CommentId commentId) throws SQLException {
    var articleId = commentsRepository.getComment(commentId).getArticleId();
    commentsRepository.deleteComment(commentId);
    var commentsCount = commentsRepository.getCommentsByArticle(articleId).length;

    articlesRepository.editTrending(articleId, commentsCount > 3);
  }

  @Override
  public Comment[] getCommentsByArticle(ArticleId articleId) {
    return commentsRepository.getCommentsByArticle(articleId);
  }
}
