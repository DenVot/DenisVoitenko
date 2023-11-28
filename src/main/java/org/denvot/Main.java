package org.denvot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import org.denvot.news.controllers.ArticlesController;
import org.denvot.news.controllers.ArticlesViewsController;
import org.denvot.news.controllers.CommentsController;
import org.denvot.news.controllers.ControllerBase;
import org.denvot.news.data.services.HashSetArticleRepository;
import org.flywaydb.core.Flyway;
import spark.Service;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args ) {
        Config config = ConfigFactory.load();

        Flyway flyway =
                Flyway.configure()
                        .outOfOrder(true)
                        .locations("classpath:db/migrations")
                        .dataSource(config.getString("app.database.url"), config.getString("app.database.user"),
                                config.getString("app.database.password"))
                        .load();
        flyway.migrate();

        var articleRepository = new HashSetArticleRepository();

        var freeMakerConfig = new Configuration(Configuration.VERSION_2_3_0);
        freeMakerConfig.setTemplateLoader(new ClassTemplateLoader(Main.class, "/"));
        var freeMakerEngine = new FreeMarkerEngine(freeMakerConfig);

        var objMapper = new ObjectMapper();
        var sparkService = Service.ignite();
        var controllers = new ArrayList<ControllerBase>();

        controllers.add(new ArticlesController(sparkService, articleRepository, objMapper));
        controllers.add(new CommentsController(sparkService, objMapper, articleRepository));
        controllers.add(new ArticlesViewsController(sparkService, articleRepository, freeMakerEngine));

        var app = new Application(controllers);
        app.start();
    }
}
