package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private final int[][] board;

  public PuzzleImpl(int[][] board) {
    this.board = new int[board.length][board[0].length];
    for (int i = 0; i < board.length; i++) {
      System.arraycopy(board[i], 0, this.board[i], 0, board[i].length);
    }
  }

  @Override
  public int getWidth() {
    return board[0].length;
  }

  @Override
  public int getHeight() {
    return board.length;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r < 0 || r >= getHeight() || c < 0 || c >= getWidth()) {
      throw new IndexOutOfBoundsException();
    }
    switch (board[r][c]) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
        return CellType.CLUE;
      case 5:
        return CellType.WALL;
      case 6:
        return CellType.CORRIDOR;
      default:
        throw new IllegalStateException();
    }
  }

  @Override
  public int getClue(int r, int c) {
    if (getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException(); // cell != cell
    }
    return board[r][c];
  }
}
