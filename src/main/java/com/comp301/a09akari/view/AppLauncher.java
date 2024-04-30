package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import com.comp301.a09akari.SamplePuzzles;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  public void start(Stage stage) {
    PuzzleLibraryImpl puzzleLibrary = new PuzzleLibraryImpl(); // new library
    Puzzle puzzle1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
    Puzzle puzzle2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
    Puzzle puzzle3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
    Puzzle puzzle4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
    Puzzle puzzle5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);

    puzzleLibrary.addPuzzle(puzzle1);
    puzzleLibrary.addPuzzle(puzzle2);
    puzzleLibrary.addPuzzle(puzzle3);
    puzzleLibrary.addPuzzle(puzzle4);
    puzzleLibrary.addPuzzle(puzzle5);

    Model model = new ModelImpl(puzzleLibrary);
    ClassicMvcController controller = new ControllerImpl(model);
    MainView mainView = new MainView(model, controller);

    stage.setScene(mainView.getScene());
    stage.setTitle("Akari Game");
    stage.show();
  }
}
