package org.denvot.news.controllers;

import org.denvot.news.controllers.responses.ArticleResponse;
import org.denvot.news.data.services.BaseArticleService;
import spark.ModelAndView;
import spark.Service;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;

public class ArticlesViewsController implements ControllerBase {
  private final Service service;
  private final BaseArticleService articleService;
  private final FreeMarkerEngine freeMarkerEngine;

  public ArticlesViewsController(Service service, BaseArticleService articleService, FreeMarkerEngine freeMarkerEngine) {

    this.service = service;
    this.articleService = articleService;
    this.freeMarkerEngine = freeMarkerEngine;
  }

  @Override
  public void initializeEndpoints() {
    getArticlesPage();
  }

  private void getArticlesPage() {
    service.get("/articles", ((request, response) -> {
      response.type("text/html; charset=utf-8");

      var articles = ArticleResponse.fromOriginal(articleService.getAllArticles());
      var model = new HashMap<String, Object>();

      model.put("articles", articles);

      return freeMarkerEngine.render(new ModelAndView(model, "index.ftl"));
    }));
  }
}
