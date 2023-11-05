package org.denvot.news.data.entities;

import java.util.List;
import java.util.Set;

public class Article {
  private final ArticleId id;
  private final String name;
  private final Set<String> tags;
  private final List<Comment> comments;

  public Article(ArticleId id, String name, Set<String> tags, List<Comment> comments) {
    this.id = id;
    this.name = name;
    this.tags = tags;
    this.comments = comments;
  }

  public ArticleId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Set<String> getTags() {
    return tags;
  }

  public List<Comment> getComments() {
    return comments;
  }
}
