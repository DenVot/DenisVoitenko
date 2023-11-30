package org.denvot.news.data.services;

import org.denvot.news.data.entities.Article;
import org.denvot.news.data.entities.ArticleId;
import org.denvot.news.data.services.entities.ArticleData;

import java.sql.SQLException;
import java.util.List;

public interface ArticlesRepository {
  List<Article> getAllArticles() throws SQLException;
  long createArticle(ArticleData articleData);
  Article getArticle(ArticleId id) throws SQLException;
  Article editName(ArticleId id, String newName) throws SQLException;
  Article editTags(ArticleId id, String[] newTags) throws SQLException;
  Article editTrending(ArticleId id, boolean isTrending) throws SQLException;
  Article editCommentCount(ArticleId id, int count) throws SQLException;
  void deleteArticle(ArticleId id);
}
