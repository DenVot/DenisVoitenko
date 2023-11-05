package org.denvot.news.data.services;

import org.denvot.news.data.entities.Article;
import org.denvot.news.data.entities.ArticleId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HashSetArticleRepository implements ArticleRepository {
  private final ConcurrentHashMap<ArticleId, Article> articles = new ConcurrentHashMap<>();
  private long maxId = 0;

  @Override
  public Article createArticle(String name, Set<String> tags) {
    ArticleId id = generateUniqueId();
    Article article = new Article(id, name, tags, new ArrayList<>());
    articles.put(id, article);

    return article;
  }

  @Override
  public Optional<Article> getArticle(ArticleId id) {
    if (articles.containsKey(id)) return Optional.empty();

    return Optional.of(articles.get(id));
  }

  @Override
  public Article editArticle(ArticleId id, ArticleDelta delta) {
    Article original = articles.get(id);

    return new Article(id,
            delta.newText != null ?
                    delta.newText : original.getName(),
            original.getTags(),
            original.getComments());
  }

  @Override
  public void deleteArticle(ArticleId id) {
    articles.remove(id);
  }

  private ArticleId generateUniqueId() {
    return new ArticleId(maxId++);
  }
}
