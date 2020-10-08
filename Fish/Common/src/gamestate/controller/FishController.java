package gamestate.controller;

import java.util.ArrayList;

import gamestate.model.Board;
import gamestate.model.BoardPosition;
import gamestate.model.Penguin;
import gamestate.view.BoardFrame;
import gamestate.view.BoardPanel;

/**
 * Controller for a single game of FishGame. Has access to both frame and board.
 */
public class FishController {
  private Board board;
  private BoardFrame frame;

  /**
   * Constructor for a FishController for a board with random fish amounts and fixed holes.
   * @param rows Number of rows to make the board with.
   * @param cols Number of columns to make the board with.
   * @param holes List of positions at which to place holes.
   * @param minTiles Minimum number of tiles that must have exactly 1 fish.
   */
  public FishController(int rows, int cols, ArrayList<BoardPosition> holes, int minTiles) {
    buildBoard(rows, cols, holes, minTiles);

    this.frame = new BoardFrame();
    this.frame.addPanel(new BoardPanel());

    this.frame.setController(this);
    this.board.setController(this);
  }

  /**
   * Constructor for a FishController with a uniform board - same # of fish on all tiles, no holes.
   * @param rows Number of rows to make the board with.
   * @param cols Number of columns to make the board with.
   * @param numFish Number of fish to place on every tile.
   */
  public FishController(int rows, int cols, int numFish) {
    buildUniformBoard(rows, cols, numFish);

    this.frame = new BoardFrame();
    this.frame.addPanel(new BoardPanel());

    this.frame.setController(this);
    this.board.setController(this);
  }

  /**
   * Constructs a board with the specified parameters, holes, and min number of 1-fish tiles.
   * @param rows Number of rows to make the board with.
   * @param cols Number of columns to make the board with.
   * @param holes List of positions at which to place holes.
   * @param minTiles Minimum number of tiles that must have exactly 1 fish.
   */
  public void buildBoard(int rows, int cols, ArrayList<BoardPosition> holes, int minTiles) {
    board = new Board(rows, cols, holes, minTiles);
  }

  /**
   * Constructs a uniform board.
   * @param rows Number of rows to make the board with.
   * @param cols Number of columns to make the board with.
   * @param numFish Number of fish to place on every tile.
   */
  public void buildUniformBoard(int rows, int cols, int numFish) {
    board = new Board(rows, cols, numFish);
  }

  /**
   * Returns the board associated with this controller.
   * @return The Board object of this controller.
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Main for testing.
   * @param args Arguments to the main function.
   */
  public static void main(String [] args) {
    //Uniform board
    //FishController fc = new FishController(4, 3, 4);
    //int rAmt = randomInt(1, 5);
    //int cAmt = randomInt(1, 5);

    ArrayList<BoardPosition> holes = new ArrayList<>();
    /* testing - generate random holes
    for (int i = 0; i < 3; i++) {
      holes.add(new BoardPosition(randomInt(0, rAmt - 1), randomInt(0, cAmt - 1)));
    }
    */
    holes.add(new BoardPosition(0, 0));
    holes.add(new BoardPosition(3, 1));
    holes.add(new BoardPosition(2, 1));
    holes.add(new BoardPosition(1, 2));

    FishController fc = new FishController(4, 3, holes, 3);
    Penguin p1 = new Penguin(Penguin.PenguinColor.BLACK, new BoardPosition(0, 1));
    Penguin p2 = new Penguin(Penguin.PenguinColor.BROWN, new BoardPosition(2, 2));
    fc.getBoard().placePenguin(p1);
    fc.getBoard().placePenguin(p2);

    //FishController fc = new FishController(rAmt, cAmt, holes, randomInt(0, rAmt * cAmt - 4));
    fc.frame.display();
  }

  /**
   * Returns an integer from the range, start and end inclusive.
   * @param min minimum value
   * @param max maximum value
   * @return integer in the range
   */
  private static int randomInt(int min, int max) {
    int range = max - min + 1;
    return (int) (Math.random() * range) + min;
  }
}
