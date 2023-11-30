package org.denvot.news.data.services;

import org.denvot.news.data.entities.Article;
import org.denvot.news.data.entities.ArticleId;
import org.denvot.news.data.services.entities.ArticleData;
import org.jdbi.v3.core.Jdbi;
import org.postgresql.jdbc.PgArray;

import java.sql.SQLException;
import java.util.*;

public class MySqlArticleRepository implements ArticlesRepository {
  private final Jdbi jdbi;

  public MySqlArticleRepository(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  @Override
  public List<Article> getAllArticles() throws SQLException {
    return jdbi.inTransaction(handle -> {
      var allArticlesDb = handle
              .select("SELECT * FROM article")
              .mapToMap();

      ArrayList<Article> articles = new ArrayList<>();

      for (Map<String, Object> dbArticle : allArticlesDb) {
        articles.add(parseArticleFromMap(dbArticle));
      }

      return articles;
    });
  }

  @Override
  public long createArticle(ArticleData articleData) {
    return jdbi.inTransaction(handle -> {
      var res = handle.createUpdate("INSERT INTO article (name, tags) VALUES (:name, :tags)")
              .bind("name", articleData.name())
              .bind("tags", articleData.tags())
              .executeAndReturnGeneratedKeys("id");

      var mapRes = res.mapToMap().first();

      return (Long) mapRes.get("id");
    });
  }

  @Override
  public Article getArticle(ArticleId id) throws SQLException {
    return jdbi.inTransaction(handle -> {
        var res = handle.select("SELECT * FROM article WHERE id = :id")
                .bind("id", id.getValue())
                .mapToMap()
                .first();

        return parseArticleFromMap(res);
    });
  }

  @Override
  public Article editName(ArticleId id, String newName) throws SQLException {
    return jdbi.inTransaction(handle -> {
      var res = handle.createUpdate("UPDATE article SET name = :name WHERE id = :id")
              .bind("id", id.getValue())
              .bind("name", newName)
              .executeAndReturnGeneratedKeys()
              .mapToMap()
              .first();

      return parseArticleFromMap(res);
    });
  }

  @Override
  public Article editTags(ArticleId id, String[] newTags) throws SQLException {
    return jdbi.inTransaction(handle -> {
      var res = handle.createUpdate("UPDATE article SET tags = :tags WHERE id = :id")
              .bind("id", id.getValue())
              .bind("tags", newTags)
              .executeAndReturnGeneratedKeys()
              .mapToMap()
              .first();

      return parseArticleFromMap(res);
    });
  }

  @Override
  public Article editTrending(ArticleId id, boolean isTrending) throws SQLException {
    return jdbi.inTransaction(handle -> {
      handle.select("SELECT * FROM article WHERE id = :id FOR UPDATE")
              .bind("id", id.getValue());

      var res = handle.createUpdate("UPDATE article SET trending = :trending WHERE id = :id")
              .bind("id", id.getValue())
              .bind("trending", isTrending)
              .executeAndReturnGeneratedKeys()
              .mapToMap()
              .first();

      return parseArticleFromMap(res);
    });
  }

  @Override
  public Article editCommentCount(ArticleId id, int count) throws SQLException {
    return jdbi.inTransaction(handle -> {
      handle.select("SELECT * FROM article WHERE id = :id FOR UPDATE")
              .bind("id", id.getValue());

      var res = handle.createUpdate("UPDATE article SET \"commentsCount\" = :count WHERE id = :id")
              .bind("id", id.getValue())
              .bind("count", count)
              .executeAndReturnGeneratedKeys()
              .mapToMap()
              .first();

      return parseArticleFromMap(res);
    });
  }

  @Override
  public void deleteArticle(ArticleId id) {
    jdbi.inTransaction(handle -> {
      handle.createUpdate("DELETE FROM article WHERE id = :id")
              .bind("id", id.getValue())
              .execute();
      return null;
    });
  }

  private static Article parseArticleFromMap(Map<String, Object> map) throws SQLException {
    var tagsStr = (PgArray) map.get("tags");

    return new Article(new ArticleId((Long) map.get("id")),
            (String) map.get("name"),
            (String[]) tagsStr.getArray(),
            (Boolean) map.get("trending"),
            (Integer) map.get("commentscount"));
  }
}
