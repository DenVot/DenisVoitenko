package org.denvot.news.data.entities;

public class Article {
  private final ArticleId id;
  private final String name;
  private final String[] tags;

  public Article(ArticleId id, String name, String[] tags) {
    this.id = id;
    this.name = name;
    this.tags = tags;
  }

  public ArticleId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String[] getTags() {
    return tags;
  }
}
