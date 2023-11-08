package org.denvot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.denvot.news.controllers.ArticlesController;
import org.denvot.news.controllers.CommentsController;
import org.denvot.news.controllers.ControllerBase;
import org.denvot.news.data.services.HashSetArticleRepository;
import spark.Service;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args ) {
        var articleRepository = new HashSetArticleRepository();
        var objMapper = new ObjectMapper();
        var sparkService = Service.ignite();
        var controllers = new ArrayList<ControllerBase>();

        controllers.add(new ArticlesController(sparkService, articleRepository, objMapper));
        controllers.add(new CommentsController(sparkService, objMapper, articleRepository));

        var app = new Application(controllers);
        app.start();
    }
}
