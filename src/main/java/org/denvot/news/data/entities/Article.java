package org.denvot.news.data.entities;

public class Article {
  private final ArticleId id;
  private final String name;
  private final String[] tags;
  private final boolean trending;
  private final int commentsCount;

  public Article(ArticleId id, String name, String[] tags, boolean trending, int commentsCount) {
    this.id = id;
    this.name = name;
    this.tags = tags;
    this.trending = trending;
    this.commentsCount = commentsCount;
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

  public boolean isTrending() {
    return trending;
  }

  public int getCommentsCount() {
    return commentsCount;
  }
}
