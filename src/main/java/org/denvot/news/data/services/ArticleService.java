package org.denvot.news.data.services;

import org.denvot.news.data.entities.Article;
import org.denvot.news.data.entities.ArticleId;
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
  public long createArticle(String name, String[] tags) {
    return articleRepository.createArticle(name, tags);
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
