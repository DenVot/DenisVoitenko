package org.denvot.news.data.entities;

import java.util.Objects;

public class CommentId {
  private final long value;

  public CommentId(long value) {
    this.value = value;
  }

  public long getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CommentId commentId = (CommentId) o;
    return value == commentId.value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
