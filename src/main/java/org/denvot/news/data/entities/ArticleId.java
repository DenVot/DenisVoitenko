package org.denvot.news.data.entities;

import java.util.Objects;

public class ArticleId {
  private final long value;

  public ArticleId(long value) {
    this.value = value;
  }

  public long getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ArticleId articleId = (ArticleId) o;
    return value == articleId.value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
