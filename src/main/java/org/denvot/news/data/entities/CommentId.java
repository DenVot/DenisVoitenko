package org.denvot.news.data.entities;

public class CommentId {
  private final long value;

  public CommentId(long value) {
    this.value = value;
  }

  public long getValue() {
    return value;
  }
}
