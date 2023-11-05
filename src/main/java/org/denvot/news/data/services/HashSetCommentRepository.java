package org.denvot.news.data.services;

import org.denvot.news.data.entities.Article;
import org.denvot.news.data.entities.Comment;
import org.denvot.news.data.entities.CommentId;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class HashSetCommentRepository implements CommentRepository {
  private final ConcurrentHashMap<CommentId, Comment> comments = new ConcurrentHashMap<>();
  private long nextId = 0;

  @Override
  public Comment createComment(String text, Article writtenTo) {
    CommentId id = generateUniqueId();
    Comment comment = new Comment(id, writtenTo.getId(), text);

    comments.put(id, comment);

    return comment;
  }

  @Override
  public Optional<Comment> getComment(CommentId id) {
    if (!comments.containsKey(id)) return Optional.empty();

    return Optional.of(comments.get(id));
  }

  @Override
  public void deleteComment(CommentId id) {
    comments.remove(id);
  }

  private CommentId generateUniqueId() {
    return new CommentId(nextId++);
  }
}
