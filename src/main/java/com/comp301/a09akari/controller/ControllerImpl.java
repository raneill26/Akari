package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelImpl;

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
    int nextIndex = model.getActivePuzzleIndex() + 1;
    if (nextIndex < model.getPuzzleLibrarySize()) {
      model.setActivePuzzleIndex(nextIndex);
    }
  }

  @Override
  public void clickPrevPuzzle() {
    int prevIndex = model.getActivePuzzleIndex() - 1;
    if (prevIndex >= 0) {
      model.setActivePuzzleIndex(prevIndex);
    }
  }

  @Override
  public void clickRandPuzzle() {
    int randIndex = (int) (Math.random() * model.getPuzzleLibrarySize());
    model.setActivePuzzleIndex(randIndex);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (r < 0
        || r >= model.getActivePuzzle().getHeight()
        || c < 0
        || c >= model.getActivePuzzle().getWidth()) {
      return;
    }
    if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
      if (model.isLamp(r, c)) {
        model.removeLamp(r, c); // remove lamp if it's there
      } else {
        model.addLamp(r, c); // add lamp if there is not one
      }
    }
    model.notifyObservers();
  }
}
