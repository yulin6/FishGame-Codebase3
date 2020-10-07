package gamestate.controller;

import java.util.ArrayList;

import gamestate.model.Board;
import gamestate.model.Position;
import gamestate.view.BoardFrame;
import gamestate.view.BoardPanel;

/**
 *
 */
public class FishController {
  private Board board;
  private BoardFrame frame;

  /**
   *
   * @param rows
   * @param cols
   * @param holes
   * @param minTiles
   */
  public FishController(int rows, int cols, ArrayList<Position> holes, int minTiles) {
    buildBoard(rows, cols, holes, minTiles);

    this.frame = new BoardFrame();
    this.frame.addPanel(new BoardPanel());

    this.frame.setController(this);
    this.board.setController(this);
  }

  /**
   *
   * @param rows
   * @param cols
   * @param numFish
   */
  public FishController(int rows, int cols, int numFish) {
    buildUniformBoard(rows, cols, numFish);

    this.frame = new BoardFrame();
    this.frame.addPanel(new BoardPanel());

    this.frame.setController(this);
    this.board.setController(this);
  }

  /**
   *
   * @param rows
   * @param cols
   * @param holes
   * @param minTiles
   */
  public void buildBoard(int rows, int cols, ArrayList<Position> holes, int minTiles) {
    board = new Board(rows, cols, holes, minTiles);
  }

  /**
   *
   * @param rows
   * @param cols
   * @param numFish
   */
  public void buildUniformBoard(int rows, int cols, int numFish) {
    board = new Board(rows, cols, numFish);
  }

  public static void main(String [] args) {
    //Uniform board
    FishController fc = new FishController(4, 3, 4);

    fc.board.renderBoard(fc.frame.getPanel(), fc.frame.getPanel().getGraphics());
  }

}
