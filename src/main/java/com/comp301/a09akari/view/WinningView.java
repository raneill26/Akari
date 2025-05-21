package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class WinningView implements FXComponent {


  public WinningView(Model model, ClassicMvcController controller) {
    if (model == null || controller == null) {
      throw new IllegalArgumentException();
    }
  }

  public Parent render() {
    VBox pane = new VBox();
    pane.setPrefHeight(200);
    pane.setPrefWidth(500);
    pane.getChildren().clear();
    pane.getStyleClass().add("vbox");
    pane.setAlignment(Pos.CENTER);
    Label title = new Label("Winner!");
    title.getStyleClass().add("title");
    title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
    title.setWrapText(true);
    pane.getChildren().add(title);

    return pane;
  }
}
