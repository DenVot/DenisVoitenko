package org.denvot.news.controllers.responses;

import org.denvot.news.data.entities.Article;

import java.util.ArrayList;
import java.util.List;

public record ArticleResponse(long id, String name, String[] tags, boolean trending) {
  public static ArticleResponse fromOriginal(Article article) {
    return new ArticleResponse(article.getId().getValue(), article.getName(), article.getTags(), article.isTrending());
  }

  public static List<ArticleResponse> fromOriginal(List<Article> articles) {
    var articlesResponse = new ArrayList<ArticleResponse>();

    for (Article article : articles) {
      articlesResponse.add(fromOriginal(article));
    }

    return articlesResponse;
  }
}
