package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

// ** Should be TitleView ** //

public class BoardView implements FXComponent {
  private final Model model;
  private final ClassicMvcController controller;

  public BoardView(Model model, ClassicMvcController controller) {
    if (model == null || controller == null) {
      throw new IllegalArgumentException();
    }
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox pane = new VBox();
    pane.setPrefHeight(100);
    pane.setPrefWidth(300);
    pane.getChildren().clear();
    pane.getStyleClass().add("vbox");
    pane.setAlignment(Pos.CENTER);
    Label title = new Label("Akari!");
    title.getStyleClass().add("title");
    title.setWrapText(true);
    title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
    pane.getChildren().add(title);

    return pane;
  }
}
