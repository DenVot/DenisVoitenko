package org.denvot.news.data.services;

import org.denvot.news.data.entities.Article;
import org.denvot.news.data.entities.ArticleId;
import org.denvot.news.data.services.entities.ArticleData;
import org.denvot.news.exceptions.ArticleNotFoundException;

import java.sql.SQLException;
import java.util.List;

public class ArticleService implements BaseArticleService {
  private final ArticlesRepository articleRepository;

  public ArticleService(ArticlesRepository articleRepository) {

    this.articleRepository = articleRepository;
  }

  @Override
  public Article getArticle(ArticleId id) throws ArticleNotFoundException {
    try {
      return articleRepository.getArticle(id);
    } catch (IllegalStateException | SQLException e) {
      throw new ArticleNotFoundException();
    }
  }

  @Override
  public List<Article> getAllArticles() throws SQLException {
    return articleRepository.getAllArticles();
  }

  @Override
  public long createArticle(ArticleData articleData) {
    return articleRepository.createArticle(articleData);
  }

  @Override
  public long[] createArticles(List<ArticleData> articleData) {
    long[] res = new long[articleData.size()];

    for (int i = 0; i < articleData.size(); i++) {
      res[i] = createArticle(articleData.get(i));
    }

    return res;
  }


  @Override
  public void deleteArticle(ArticleId articleId) {
    articleRepository.deleteArticle(articleId);
  }

  @Override
  public Article editName(ArticleId id, String newName) throws ArticleNotFoundException {
    try {
      return articleRepository.editName(id, newName);
    } catch (IllegalStateException | SQLException e) {
      throw new ArticleNotFoundException();
    }
  }

  @Override
  public Article editTags(ArticleId id, String[] newTags) throws ArticleNotFoundException {
    try {
      return articleRepository.editTags(id, newTags);
    } catch (IllegalStateException | SQLException e) {
      throw new ArticleNotFoundException();
    }
  }

  @Override
  public Article editTrending(ArticleId id, boolean isTrending) throws ArticleNotFoundException {
    try {
      return articleRepository.editTrending(id, isTrending);
    } catch (IllegalStateException | SQLException e) {
      throw new ArticleNotFoundException();
    }
  }
}
