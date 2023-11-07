package org.denvot.news.data.services;

import org.denvot.news.data.entities.Article;
import org.denvot.news.data.entities.ArticleId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class HashSetArticleRepository implements ArticleRepository {
  private final ConcurrentHashMap<ArticleId, Article> articles = new ConcurrentHashMap<>();
  private final AtomicLong maxId = new AtomicLong(0);

  @Override
  public List<Article> getAllArticles() {
    return articles.values().stream().toList();
  }

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
  public Article editName(ArticleId id, String newName) {
    return editArticle(id, article -> article.editName(newName));
  }

  @Override
  public Article editTags(ArticleId id, Set<String> newTags) {
    return editArticle(id, article -> article.editTags(newTags));
  }

  @Override
  public void deleteArticle(ArticleId id) {
    articles.remove(id);
  }

  private ArticleId generateUniqueId() {
    return new ArticleId(maxId.getAndIncrement());
  }

  private Article editArticle(ArticleId id, Function<Article, Article> deltaFunc) {
    Optional<Article> articleOpt = getArticle(id);

    if (articleOpt.isEmpty()) {
      throw new RuntimeException("Article is null");
    }

    Article article = deltaFunc.apply(articleOpt.get());
    articles.put(id, article);

    return article;
  }
}
