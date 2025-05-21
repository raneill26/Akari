package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;

public class ControllerImpl implements ClassicMvcController {
  private final Model model;

  public ControllerImpl(Model model) {
    if (model == null) {
      throw new IllegalArgumentException();
    }
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    int currentPuzzle = model.getActivePuzzleIndex();
    int nextPuzzle;
    if (currentPuzzle == 4) {
      nextPuzzle = 0;
    } else {
      nextPuzzle = currentPuzzle + 1;
    }
    model.setActivePuzzleIndex(nextPuzzle);
  }

  @Override
  public void clickPrevPuzzle() {
    int currentPuzzle = model.getActivePuzzleIndex();
    int previousPuzzle;
    if (currentPuzzle == 0) {
      previousPuzzle = 4;
    } else {
      previousPuzzle = currentPuzzle - 1;
    }
    model.setActivePuzzleIndex(previousPuzzle);
  }

  @Override
  public void clickRandPuzzle() {
    int puzzleAmount = model.getPuzzleLibrarySize();
    int randIndex = (int) (Math.random() * puzzleAmount);
    model.setActivePuzzleIndex(randIndex);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    int h = model.getActivePuzzle().getHeight();
    int w = model.getActivePuzzle().getWidth();
    if (r < h && r >= 0 && c < w || c >= 0) {
      if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
        if (model.isLamp(r, c)) {
          model.removeLamp(r, c); // remove lamp if it's there
        } else {
          model.addLamp(r, c); // add lamp if there is not one
        }
      }
    }
  }

  public boolean winner() {
    return model.isSolved();
  }
}
