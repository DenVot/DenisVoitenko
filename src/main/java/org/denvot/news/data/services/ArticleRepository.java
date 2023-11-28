package org.denvot.news.data.services;

import org.denvot.news.data.entities.Article;
import org.denvot.news.data.entities.ArticleId;

import java.sql.SQLException;
import java.util.List;

public interface ArticleRepository {
  List<Article> getAllArticles() throws SQLException;
  long createArticle(String name, String[] tags);
  Article getArticle(ArticleId id) throws SQLException;
  Article editName(ArticleId id, String newName) throws SQLException;
  Article editTags(ArticleId id, String[] newTags) throws SQLException;
  void deleteArticle(ArticleId id);
}
