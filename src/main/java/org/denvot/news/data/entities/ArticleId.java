package org.denvot.news.data.entities;

public class ArticleId {
  private final long value;

  public ArticleId(long value) {
    this.value = value;
  }

  public long getValue() {
    return value;
  }
}
