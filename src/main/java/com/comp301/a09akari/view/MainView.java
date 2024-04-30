package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class MainView implements FXComponent, ModelObserver {
  private final Scene scene;
  private final FXComponent boardView;
  private final TitleView titleView;
  private final WinningView winningView;
  private final Model model;
  private final ClassicMvcController controller;

  public MainView(Model model, ClassicMvcController controller) {
    this.boardView = new BoardView(model, controller);
    this.titleView = new TitleView(model, controller);
    this.winningView = new WinningView(model, controller);
    this.model = model;
    this.controller = controller;
    this.model.addObserver(this);

    this.scene = new Scene(render(), 700, 700);
    this.scene.getStylesheets().add("main.css");
  }

  public Scene getScene() {
    return scene;
  }

  @Override
  public Parent render() {
    BorderPane pane = new BorderPane();
    if (!controller.winner()) {
      pane.setTop(boardView.render()); // should be title
      pane.setCenter(titleView.render()); // should be board
    } else {
      pane.setTop(winningView.render()); // winning
      pane.setCenter(titleView.render()); // board
    }
    return pane;
  }

  @Override
  public void update(Model model) {
    scene.setRoot(render());
  }
}
