package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;


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
    GridPane board = setUp();
    Button nextPuzzle = new Button("Next Puzzle");
    nextPuzzle.setOnAction(event -> controller.clickNextPuzzle());

    Button prevPuzzle = new Button("Previous Puzzle");
    prevPuzzle.setOnAction(event -> controller.clickPrevPuzzle());

    Button randPuzzle = new Button("Random Puzzle");
    randPuzzle.setOnAction(event -> controller.clickRandPuzzle());

    Button resetPuzzle = new Button("Reset Puzzle");
    resetPuzzle.setOnAction(event -> controller.clickResetPuzzle());

    VBox pane = new VBox();
    pane.setAlignment(Pos.CENTER);
    pane.getChildren().addAll(new HBox(nextPuzzle, prevPuzzle, randPuzzle, resetPuzzle), board);

    // board.add(nextPuzzle, 1, 5);
    // board.add(prevPuzzle, 2, 5);
    // board.add(randPuzzle, 3, 5);
    // board.add(resetPuzzle, 4, 5);

    return pane;
  }

  private GridPane setUp() {
    GridPane gridPane = new GridPane();
    int h = model.getActivePuzzle().getHeight();
    int w = model.getActivePuzzle().getWidth();

    for (int i = 0; i < h; i++) {
      for (int i2 = 0; i2 < w; i2++) {
        final int r = i;
        final int c = i2;
        Button newCell = new Button();
        formatNewCell(newCell, r, c);
        gridPane.add(newCell, i2, i);
      }
    }
    return gridPane;
  }

  private void formatNewCell(Button newCell, int r, int c) {
    newCell.setPrefSize(50, 50);
    if (model.getActivePuzzle().getCellType(r, c) == CellType.WALL) {
      newCell.setStyle("-fx-background-color: black; -fx-border-color: black;");
    } else if (model.getActivePuzzle().getCellType(r, c) == CellType.CLUE) {
      if (!model.isClueSatisfied(r, c)) {
        newCell.setStyle(
            "-fx-background-color: black; -fx-text-fill: white; -fx-border-color: red;");
        newCell.setText(String.valueOf(model.getActivePuzzle().getClue(r, c)));
      } else {
        newCell.setStyle(
            "-fx-background-color: black; -fx-text-fill: white; -fx-border-color: black;");
        newCell.setText(String.valueOf(model.getActivePuzzle().getClue(r, c)));
      }
    } else if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
      if (model.isLamp(r, c) && !model.isLampIllegal(r, c)) {
        Image bulb = new Image("/light-bulb.png");
        ImageView display = new ImageView(bulb);
        display.setFitHeight(25);
        display.setFitWidth(25);
        newCell.setGraphic(display); // display in ()
        newCell.setStyle("-fx-background-color: yellow; -fx-border-color: white;");
      } else if (model.isLamp(r, c) && model.isLampIllegal(r, c)) {
        Image bulb = new Image("/light-bulb.png");
        ImageView display = new ImageView(bulb);
        display.setFitHeight(20);
        display.setFitWidth(20);
        newCell.setGraphic(display); // display in ()
        newCell.setStyle("-fx-background-color: yellow; -fx-border-color: red;");
      } else if (model.isLit(r, c)) {
        newCell.setStyle("-fx-background-color: yellow; -fx-border-color: black;");
      } else {
        newCell.setStyle("-fx-background-color: white; -fx-border-color: black;");
      }
    }
    newCell.setOnAction(event -> controller.clickCell(r, c));
  }
}
