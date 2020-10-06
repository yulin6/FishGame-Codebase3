package gamestate.model;

import java.util.ArrayList;

import gamestate.view.BoardPanel;

/**
 *
 */
public class Board implements IBoard {
  int rows;
  int cols;

  private Board() {
    // TODO
  }

  @Override
  public Board buildBoard(int rows, int columns, ArrayList<Position> holes, int minTiles) {
    // TODO: create a board as specified
    return null;
  }

  @Override
  public Board buildUniformBoard(int fishNum, int rows, int columns) {
    // TODO: create a board as specified
    return null;
  }

  @Override
  public void removeTile(Position p) {
    // TODO: create a hole (remove from tiles data container)
  }

  @Override
  public void renderBoard(BoardPanel bp) {
    // TODO: board rendering via BoardFrame/BoardPanel
  }

  /**
   * Renders a single tile of the Board onto the BoardPanel.
   */
  private void renderTile(BoardPanel bp, Tile t) {
    // TODO
  }

  @Override
  public ArrayList<Tile> getValidMoves(Position p) {
    // TODO: algorithm to validate moves and add to list
    return null;
  }

}
