package org.denvot.news.data.services;

import org.denvot.news.data.entities.ArticleId;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HashSetArticleRepositoryTest {
  @Test
  void getAllArticlesShouldBeEmpty() {
    var repo = new HashSetArticleRepository();
    assertEquals(0, repo.getAllArticles().size());
  }

  @Test
  void getAllArticlesShouldNotBeEmpty() {
    var repo = new HashSetArticleRepository();

    repo.createArticle("Test", new HashSet<>());

    assertEquals(1, repo.getAllArticles().size());
  }

  @Test
  void getArticleShouldGet() {
    var repo = new HashSetArticleRepository();

    repo.createArticle("Test", new HashSet<>());
    var article = repo.getArticle(new ArticleId(0));

    assertTrue(article.isPresent());
    assertEquals("Test", article.get().getName());
  }

  @Test
  void getArticleShouldNotGet() {
    var repo = new HashSetArticleRepository();

    var article = repo.getArticle(new ArticleId(0));

    assertTrue(article.isEmpty());
  }

  @Test
  void editName() {
    var repo = new HashSetArticleRepository();

    repo.createArticle("Test", new HashSet<>());
    var article = repo.editName(new ArticleId(0), "Test1");

    assertEquals("Test1", article.getName());

    var searchResult = repo.getArticle(new ArticleId(0));

    assertTrue(searchResult.isPresent());
    assertEquals("Test1", searchResult.get().getName());
  }

  @Test
  void editTags() {
    var repo = new HashSetArticleRepository();

    repo.createArticle("Test", new HashSet<>());
    var article = repo.editTags(new ArticleId(0), Set.of("TestTag"));

    assertEquals(1, article.getTags().size());

    var searchResult = repo.getArticle(new ArticleId(0));

    assertTrue(searchResult.isPresent());
    assertEquals(1, searchResult.get().getTags().size());
  }

  @Test
  void deleteArticle() {
    var repo = new HashSetArticleRepository();
    repo.createArticle("Test", new HashSet<>());

    repo.deleteArticle(new ArticleId(0));

    assertTrue(repo.getArticle(new ArticleId(0)).isEmpty());
  }
}
