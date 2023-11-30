package org.denvot.news.data.services;

import org.denvot.news.data.entities.Article;
import org.denvot.news.data.entities.ArticleId;
import org.denvot.news.data.services.entities.ArticleData;
import org.denvot.news.exceptions.ArticleNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface BaseArticleService {
  Article getArticle(ArticleId id) throws ArticleNotFoundException;
  List<Article> getAllArticles() throws SQLException;
  long createArticle(ArticleData articleData);
  long[] createArticles(List<ArticleData> articleData);
  void deleteArticle(ArticleId articleId);
  Article editName(ArticleId id, String newName) throws ArticleNotFoundException;
  Article editTags(ArticleId id, String[] newTags) throws ArticleNotFoundException;
  Article editTrending(ArticleId id, boolean isTrending) throws ArticleNotFoundException;
}
