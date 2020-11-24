package game.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashSet;


import game.model.Board;
import game.model.BoardPosition;
import game.model.GameState;
import game.model.IBoard;
import game.model.IState;
import game.model.Penguin;
import game.model.Player;
import game.observer.StateChangeListener;
import game.view.FishFrame;
import game.view.FishPanel;
import referee.Referee;

import javax.swing.*;

/**
 * Controller for a single game of FishGame. Has access to the display frame and the game state
 * For now, the controller sorta acts like our referee, creating a game with a set of players
 * and a board layout. It is used for testing the rendering of game states. Previously, it was
 * used to test the rendering of just the game board.
 */
public class FishController implements StateChangeListener {
  private GameState state;
  private final FishFrame frame;
  private Referee referee;

//  private int speed = 10;
//  private Timer timer = new Timer(1000 / speed, this);

  /**
   * Constructor for a FishController for a board with random fish amounts and fixed holes.
   * @param rows Number of rows to make the board with.
   * @param cols Number of columns to make the board with.
   * @param holes List of positions at which to place holes.
   * @param minTiles Minimum number of tiles that must have exactly 1 fish.
   */
  public FishController(int rows, int cols, HashSet<Player> players,
                        ArrayList<BoardPosition> holes, int minTiles) {
    IBoard b = buildBoard(rows, cols, holes, minTiles);
    state = new GameState(players, b);

    this.frame = new FishFrame(rows, cols);
    this.frame.addPanel(new FishPanel());

    this.frame.setController(this);
    this.referee = new Referee(this.state);
    this.referee.addListener(this);
  }

  /**
   * Constructor for a FishController with a uniform board - same # of fish on all tiles, no holes.
   * @param rows Number of rows to make the board with.
   * @param cols Number of columns to make the board with.
   * @param numFish Number of fish to place on every tile.
   */
  public FishController(int rows, int cols, HashSet<Player> players, int numFish) {
    IBoard b = buildUniformBoard(rows, cols, numFish);
    state = new GameState(players, b);

    this.frame = new FishFrame(rows, cols);
    this.frame.addPanel(new FishPanel());

    this.frame.setController(this);
    this.referee = new Referee(this.state);
    this.referee.addListener(this);
  }

  /**
   * Alternative constructor for a controller that takes a GameState directly.
   * @param gs GameState to make controller with.
   */
  public FishController(GameState gs) {
    state = gs;

    this.frame = new FishFrame(gs.getBoard().getRows(), gs.getBoard().getCols());
    this.frame.addPanel(new FishPanel());

    this.frame.setController(this);
    this.referee = new Referee(this.state);
    this.referee.addListener(this);
  }


  /**
   * Getter for the frame of this controller.
   * @return The frame, which can be used to display the state.
   */
  public FishFrame getView() {
    return this.frame;
  }

  /**
   * Constructs a board with the specified parameters, holes, and min number of 1-fish tiles.
   * @param rows Number of rows to make the board with.
   * @param cols Number of columns to make the board with.
   * @param holes List of positions at which to place holes.
   * @param minTiles Minimum number of tiles that must have exactly 1 fish.
   *
   * @return A game board with holes and a minimum number of 1-fish tiles
   */
  public IBoard buildBoard(int rows, int cols, ArrayList<BoardPosition> holes, int minTiles) {
    return new Board(rows, cols, holes, minTiles);
  }

  /**
   * Constructs a uniform board.
   * @param rows Number of rows to make the board with.
   * @param cols Number of columns to make the board with.
   * @param numFish Number of fish to place on every tile.
   *
   * @return A uniform game board where there are no holes
   * and each tile has the same number of fish.
   */
  public IBoard buildUniformBoard(int rows, int cols, int numFish) {
    return new Board(rows, cols, numFish);
  }

  /**
   * Gets this controller's game state
   * @return The game state of the controller
   */
  public IState getState() {
    return state;
  }

  @Override
  public void actionPerformed() {
    this.state = referee.getGameState();
    this.frame.repaint();
  }

  public void runGame() {
//    this.referee.setGamePhase(Referee.GamePhase.PLACING);
    frame.display();
    this.referee.runGame();
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

    Player p1 = new Player(17, Penguin.PenguinColor.BLACK);
    Player p2 = new Player(14, Penguin.PenguinColor.BROWN);
    Player p3 = new Player(10, Penguin.PenguinColor.RED);
    Player p4 = new Player(21, Penguin.PenguinColor.WHITE);

    HashSet<Player> players = new HashSet<>();
    players.add(p1);
    players.add(p2);

    FishController fc = new FishController(8, 8, players, holes, 3);

    BoardPosition pen1Posn = new BoardPosition(4, 4);
    BoardPosition pen2Posn = new BoardPosition(3, 6);
    BoardPosition pen3Posn = new BoardPosition(1, 1);
    BoardPosition pen4Posn = new BoardPosition(3, 3);

    fc.state.placeAvatar(pen1Posn, p1);
    fc.state.placeAvatar(pen2Posn, p2);
    fc.state.placeAvatar(pen3Posn, p3);
    fc.state.placeAvatar(pen4Posn, p4);

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
