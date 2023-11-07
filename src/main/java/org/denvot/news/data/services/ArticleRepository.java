package org.denvot.news.data.services;

import org.denvot.news.data.entities.Article;
import org.denvot.news.data.entities.ArticleId;

import java.util.Optional;
import java.util.Set;

public interface ArticleRepository {
  Article createArticle(String name, Set<String> tags);
  Optional<Article> getArticle(ArticleId id);
  Article editName(ArticleId id, String newName);
  Article editTags(ArticleId id, Set<String> newTags);
  void deleteArticle(ArticleId id);
}
