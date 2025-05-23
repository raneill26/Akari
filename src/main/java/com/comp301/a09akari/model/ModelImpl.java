package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private final PuzzleLibrary puzzleLibrary;
  private final List<ModelObserver> observers;
  private Puzzle activePuzzle;
  private int activePuzzleIndex;
  private boolean[][] lamps;
  private int height;
  private int width;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null || library.size() == 0) {
      throw new IllegalArgumentException();
    }
    this.puzzleLibrary = library;
    this.activePuzzleIndex = 0;
    this.activePuzzle = puzzleLibrary.getPuzzle(activePuzzleIndex);
    this.height = activePuzzle.getHeight();
    this.width = activePuzzle.getWidth();
    this.lamps = new boolean[height][width];
    this.observers = new ArrayList<>();
  }

  @Override
  public void addLamp(int r, int c) {
    if (r < 0 || r >= height || c < 0 || c >= width) {
      throw new IndexOutOfBoundsException(); // out of bounds
    }
    if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException(); // must be a corridor
    }
    lamps[r][c] = true;
    notifyObservers();
  }

  @Override
  public void removeLamp(int r, int c) {
    if (r < 0 || r >= height || c < 0 || c >= width) {
      throw new IndexOutOfBoundsException();
    }
    if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    lamps[r][c] = false;
    notifyObservers();
  }

  @Override
  public boolean isLit(int r, int c) {
    int h = activePuzzle.getHeight();
    int w = activePuzzle.getWidth();
    if (r < 0 || r >= h || c < 0 || c >= w) {
      throw new IndexOutOfBoundsException();
    }
    if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }

    if (isLamp(r, c)) { // if cell is a lamp, must be lit
      return true;
    }

    for (int i = c - 1; i >= 0; i--) { // lit from column
      if (activePuzzle.getCellType(r, i) == CellType.WALL
          || activePuzzle.getCellType(r, i) == CellType.CLUE) {
        break; // if wall or clue
      }
      if (isLamp(r, i)) {
        return true; // lamp in view
      }
    }

    for (int i = c + 1; i < w; i++) {
      if (activePuzzle.getCellType(r, i) == CellType.WALL
          || activePuzzle.getCellType(r, i) == CellType.CLUE) {
        break; // if wall or clue
      }
      if (isLamp(r, i)) {
        return true; // lamp in view
      }
    }

    for (int i = r - 1; i >= 0; i--) { // lit from row
      if (activePuzzle.getCellType(i, c) == CellType.WALL
          || activePuzzle.getCellType(i, c) == CellType.CLUE) {
        break; // if wall
      }
      if (isLamp(i, c)) {
        return true; // lamp in column
      }
    }

    for (int i = r + 1; i < h; i++) {
      if (activePuzzle.getCellType(i, c) == CellType.WALL
          || activePuzzle.getCellType(i, c) == CellType.CLUE) {
        break; // if wall
      }
      if (isLamp(i, c)) {
        return true; // lamp in column
      }
    }

    return false; // not lit yet
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (r < 0 || r >= height || c < 0 || c >= width) {
      throw new IndexOutOfBoundsException();
    }
    if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    return lamps[r][c];
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    int h = activePuzzle.getHeight();
    int w = activePuzzle.getWidth();
    if (r < 0 || r >= h || c < 0 || c >= w) {
      throw new IndexOutOfBoundsException();
    }
    if (!isLamp(r, c)) {
      throw new IllegalArgumentException();
    }
    // columns
    for (int i = c - 1; i >= 0; i--) {
      if (activePuzzle.getCellType(r, i) == CellType.CORRIDOR) {
        if (isLamp(r, i)) {
          return true;
        }
      }
      if (activePuzzle.getCellType(r, i) == CellType.WALL
          || activePuzzle.getCellType(r, i) == CellType.CLUE) {
        break;
      }
    }

    for (int i = c + 1; i < w; i++) {
      if (activePuzzle.getCellType(r, i) == CellType.CORRIDOR) {
        if (isLamp(r, i)) {
          return true;
        }
      }
      if (activePuzzle.getCellType(r, i) == CellType.WALL
          || activePuzzle.getCellType(r, i) == CellType.CLUE) {
        break;
      }
    }
    // rows
    for (int i = r - 1; i >= 0; i--) {
      if (activePuzzle.getCellType(i, c) == CellType.CORRIDOR) {
        if (isLamp(i, c)) {
          return true;
        }
      }
      if (activePuzzle.getCellType(i, c) == CellType.WALL
          || activePuzzle.getCellType(i, c) == CellType.CLUE) {
        break;
      }
    }
    for (int i = r + 1; i < h; i++) {
      if (activePuzzle.getCellType(i, c) == CellType.CORRIDOR) {
        if (isLamp(i, c)) {
          return true;
        }
      }
      if (activePuzzle.getCellType(i, c) == CellType.WALL
          || activePuzzle.getCellType(i, c) == CellType.CLUE) {
        break;
      }
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return activePuzzle;
  }

  @Override
  public int getActivePuzzleIndex() {
    return activePuzzleIndex;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= puzzleLibrary.size()) {
      throw new IndexOutOfBoundsException();
    }
    activePuzzleIndex = index;
    activePuzzle = puzzleLibrary.getPuzzle(activePuzzleIndex);
    height = activePuzzle.getHeight();
    width = activePuzzle.getWidth();
    lamps = new boolean[height][width];
    notifyObservers();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return puzzleLibrary.size();
  }

  @Override
  public void resetPuzzle() {
    for (int i = 0; i < height; i++) { // reset lamps
      for (int j = 0; j < width; j++) {
        lamps[i][j] = false;
      }
    }
    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    int h = activePuzzle.getHeight();
    int w = activePuzzle.getWidth();
    // clues satisfied check
    for (int r = 0; r < h; r++) {
      for (int c = 0; c < w; c++) {
        if (activePuzzle.getCellType(r, c) == CellType.CLUE && !isClueSatisfied(r, c)) {
          return false; // clues unsatisfied
        }
      }
    }
    // lit corridors check
    for (int r = 0; r < h; r++) {
      for (int c = 0; c < w; c++) {
        if (activePuzzle.getCellType(r, c) == CellType.CORRIDOR && !isLit(r, c)) {
          return false; // unlit corridors
        }
      }
    }
    // illegal lamp check
    for (int r = 0; r < h; r++) {
      for (int c = 0; c < w; c++) {
        if (activePuzzle.getCellType(r, c) == CellType.CORRIDOR) {
          if (isLamp(r, c)) {
            if (isLampIllegal(r, c)) {
              return false; // illegal lamps
            }
          }
        }
      }
    }
    // notifyObservers();
    return true; // satisfied
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (r < 0 || r >= height || c < 0 || c >= width) {
      throw new IndexOutOfBoundsException();
    }
    if (activePuzzle.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    int clueNumber = activePuzzle.getClue(r, c);
    int adjacentLamps = countAdjacentLamps(r, c);
    return adjacentLamps == clueNumber;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }

  public void notifyObservers() { // was private, changed
    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }

  // ************* HELPER METHOD ************* //

  private int countAdjacentLamps(int r, int c) {
    int count = 0;
    if (r > 0 && lamps[r - 1][c]) {
      count++; // up
    }
    if (r < height - 1 && lamps[r + 1][c]) {
      count++; // down
    }
    if (c > 0 && lamps[r][c - 1]) {
      count++; // left
    }
    if (c < width - 1 && lamps[r][c + 1]) {
      count++; // right
    }
    return count;
  }
}
