package gamestate.controller;

import java.util.ArrayList;

import gamestate.model.Board;
import gamestate.model.Position;
import gamestate.view.BoardFrame;

/**
 *
 */
public class FishController {
  private Board board;
  private BoardFrame frame;

  /**
   *
   */
  public FishController() {

  }

  /**
   *
   * @param rows
   * @param cols
   * @param holes
   * @param minTiles
   */
  public void buildBoard(int rows, int cols, ArrayList<Position> holes, int minTiles) {
    board = board.buildBoard(rows, cols, holes, minTiles);
  }

  /**
   *
   * @param rows
   * @param cols
   * @param numFish
   */
  public void buildUniformBoard(int rows, int cols, int numFish) {
    board = board.buildUniformBoard(rows, cols, numFish);
  }


}
