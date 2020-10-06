package gamestate.model;

import java.util.ArrayList;

import gamestate.view.BoardPanel;

public interface IBoard {

  /**
   * Makes a board that has holes in specific places and a minimum number of 1-fish tiles.
   * tiles
   * @param rows
   * @param columns
   * @param holes
   * @param minTiles
   */
  Board buildBoard(int rows, int columns, ArrayList<Position> holes, int minTiles);

  /**
   * Builds a board with the same number of fish on every tile & no holes.
   * @param fishNum number of fish on each tile
   * @param rows number of rows of tiles
   * @param columns number of olumns of tiles
   * @return
   */
  Board buildUniformBoard(int fishNum, int rows, int columns);

  /**
   *
   * @param p
   * @return
   */
  ArrayList<Tile> getValidMoves(Position p);

  /**
   *
   * @param p
   */
  void removeTile(Position p);

  /**
   *
   */
  void renderBoard(BoardPanel bp);

}