package org.denvot;

import org.denvot.news.controllers.ControllerBase;

import java.util.List;

public class Application {
  private final List<ControllerBase> controllers;

  public Application(List<ControllerBase> controllers) {
    this.controllers = controllers;
  }

  public void start() {
    for (ControllerBase controller : controllers) {
      controller.initializeEndpoints();
    }
  }
}
