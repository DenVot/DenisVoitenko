package org.denvot.news.data.services;

import org.denvot.news.data.entities.ArticleId;
import org.denvot.news.data.services.entities.ArticleData;
import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
class MySqlArticleRepositoryTest {
  @Container
  public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13");

  private static Jdbi jdbi;

  @BeforeAll
  static void beforeAll() {
    String postgresJdbcUrl = POSTGRES.getJdbcUrl();
    Flyway flyway =
            Flyway.configure()
                    .outOfOrder(true)
                    .locations("classpath:db/migrations")
                    .dataSource(postgresJdbcUrl, POSTGRES.getUsername(), POSTGRES.getPassword())
                    .load();
    flyway.migrate();
    jdbi = Jdbi.create(postgresJdbcUrl, POSTGRES.getUsername(), POSTGRES.getPassword());
  }

  @BeforeEach
  void beforeEach() {
    jdbi.useTransaction(handle -> handle.createUpdate("DELETE FROM article").execute());
    jdbi.useTransaction(handle -> handle.createUpdate("DELETE FROM comment").execute());
  }

  @Test
  void getAllArticlesShouldBeEmpty() throws SQLException {
    var repo = new MySqlArticleRepository(jdbi);
    assertEquals(0, repo.getAllArticles().size());
  }

  @Test
  void getAllArticlesShouldNotBeEmpty() throws SQLException {
    var repo = new MySqlArticleRepository(jdbi);
    String[] emptyArray = {};

    repo.createArticle(new ArticleData("Test", emptyArray));

    assertEquals(1, repo.getAllArticles().size());
  }

  @Test
  void getArticleShouldGet() throws SQLException {
    var repo = new MySqlArticleRepository(jdbi);
    String[] emptyArray = {};

    var res = repo.createArticle(new ArticleData("Test", emptyArray));
    var article = repo.getArticle(new ArticleId(res));

    assertEquals("Test", article.getName());
  }

  @Test
  void getArticleShouldNotGet() {
    var repo = new MySqlArticleRepository(jdbi);

    assertThrows(IllegalStateException.class, () -> {
      repo.getArticle(new ArticleId(0));
    });
  }

  @Test
  void editName() throws SQLException {
    var repo = new MySqlArticleRepository(jdbi);
    String[] emptyArray = {};

    var res = repo.createArticle(new ArticleData("Test", emptyArray));
    var article = repo.editName(new ArticleId(res), "Test1");

    assertEquals("Test1", article.getName());

    var searchResult = repo.getArticle(new ArticleId(res));

    assertEquals("Test1", searchResult.getName());
  }

  @Test
  void editTags() throws SQLException {
    var repo = new MySqlArticleRepository(jdbi);
    String[] emptyArray = {};
    String[] newArray = {"A"};

    var res = repo.createArticle(new ArticleData("Test", emptyArray));
    var article = repo.editTags(new ArticleId(res), newArray);

    assertEquals(1, article.getTags().length);

    var searchResult = repo.getArticle(new ArticleId(res));

    assertEquals(1, searchResult.getTags().length);
  }

  @Test
  void deleteArticle() {
    var repo = new MySqlArticleRepository(jdbi);
    String[] emptyArray = {};
    var res = repo.createArticle(new ArticleData("Test", emptyArray));

    repo.deleteArticle(new ArticleId(res));

    assertThrows(IllegalStateException.class, () -> repo.getArticle(new ArticleId(res)));
  }
}
