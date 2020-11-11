package game.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.*;

/**
 * Tests for objects of the GameTree class, which represent a complete tree of game states.
 * Tests are performed for:
 * - looking ahead to future directly-connected game states, with exception checks
 * - applying functions to all children of the the state
 * - legality checking of a move in a given state
 */
public class GameTreeTest {
  GameTreeNode g1;
  GameTreeNode g2;

  int row = 8;
  int col = 8;
  int minTiles = 3;
  IBoard holeBoard;
  IBoard trivialBoard;
  ArrayList<BoardPosition> holes;
  HashSet<Player> players;

  BoardPosition placement1;
  BoardPosition placement2;
  BoardPosition placement3;
  BoardPosition placement4;
  BoardPosition from1;
  BoardPosition from2;
  BoardPosition from3;
  BoardPosition from4;
  BoardPosition to1;
  BoardPosition to2;
  BoardPosition to3;
  BoardPosition to4;

  Move m1;
  Move m2;
  Move m3;
  Move m4;
  Move m5;
  Pass p1;
  Pass p2;
  Pass p3;
  Pass trivPass;

  Player player1;
  Player player2;
  Player player3;
  Player player4;

  GameState state1;
  GameState trivialGs;

  @Before
  public void setUp() {
    holes = new ArrayList<>();
    holes.add(new BoardPosition(0, 0));
    holes.add(new BoardPosition(3, 1));
    holes.add(new BoardPosition(2, 1));
    holes.add(new BoardPosition(1, 2));

    player1 = new Player(17, Penguin.PenguinColor.BLACK);
    player2 = new Player(14, Penguin.PenguinColor.BROWN);
    player3 = new Player(10, Penguin.PenguinColor.RED);
    player4 = new Player(21, Penguin.PenguinColor.WHITE);
    players = new HashSet<>();
    players.add(player1);
    players.add(player2);
    players.add(player3);
    players.add(player4);

    holeBoard = new Board(row, col, holes, minTiles);
    state1 = new GameState(players, holeBoard);

    placement1 = new BoardPosition(2, 2);
    placement2 = new BoardPosition(3, 0);
    placement3 = new BoardPosition(5, 3);
    placement4 = new BoardPosition(7, 1);

    state1.placeAvatar(placement1, player3);
    state1.placeAvatar(placement2, player2);
    state1.placeAvatar(placement3, player1);
    state1.placeAvatar(placement4, player4);

    from1 = new BoardPosition(2, 2);
    from2 = new BoardPosition(3, 0);
    from3 = new BoardPosition(5,3);
    from4 = new BoardPosition(7, 1);
    to1 = new BoardPosition(0, 2);
    to2 = new BoardPosition(4, 1);
    to3 = new BoardPosition(3,3);
    to4 = new BoardPosition(5,1);

    m1 = new Move(to1, from1, player3);
    m2 = new Move(to2, from2, player2);
    m3 = new Move(to3, from3, player1);
    m4 = new Move(to4, from4, player4);
    m5 = new Move(to4, from4, player1);
    p1 = new Pass(player1);
    p2 = new Pass(player2);
    p3 = new Pass(player3);

    g1 = new GameTreeNode(state1);

    ArrayList<BoardPosition> bps = new ArrayList<>();
    bps.add(new BoardPosition(0,0));
    bps.add(new BoardPosition(0,1));
    trivialBoard = new Board(2,2, bps, 0);
    HashSet<Player> trivialPlayers = new HashSet<>();
    Player tp1 = new Player(10, Penguin.PenguinColor.BLACK);
    Player tp2 = new Player(11, Penguin.PenguinColor.BROWN);
    trivialPlayers.add(tp1);
    trivialPlayers.add(tp2);
    trivialGs = new GameState(trivialPlayers, trivialBoard);
    trivialGs.placeAvatar(new BoardPosition(1, 0), tp2);
    trivialGs.placeAvatar(new BoardPosition(1, 1), tp1);
    trivPass = new Pass(tp1);

    g2 = new GameTreeNode(trivialGs);

  }

  @Test
  public void lookAhead() {
    GameTreeNode result1 = g1.lookAhead(m1);
    GameState resultState1 = result1.getGameState();
    assertEquals(resultState1.getPenguinAtPosn(to1).getColor(),
            g1.getGameState().getPenguinAtPosn(from1).getColor());

    GameTreeNode result2 = result1.lookAhead(m2);
    GameState resultState2 = result2.getGameState();
    assertEquals(resultState2.getPenguinAtPosn(to2).getColor(),
            resultState1.getPenguinAtPosn(from2).getColor());

    GameTreeNode result3 = result2.lookAhead(m3);
    GameState resultState3 = result3.getGameState();
    assertEquals(resultState3.getPenguinAtPosn(to3).getColor(),
            resultState2.getPenguinAtPosn(from3).getColor());

    GameTreeNode result4 = result3.lookAhead(m4);
    GameState resultState4 = result4.getGameState();
    assertEquals(resultState4.getPenguinAtPosn(to4).getColor(),
            resultState3.getPenguinAtPosn(from4).getColor());
  }

  @Test (expected = IllegalStateException.class)
  public void lookAheadInvalidPass() {
    GameTreeNode result = g1.lookAhead(p3);
  }

  @Test (expected = IllegalStateException.class)
  public void lookAheadInvalidMove() {
    GameTreeNode result = g1.lookAhead(m2);
  }

  @Test
  public void applyAllChildren() {
    Consumer<List<GameTreeNode>> testConsumer = gameTrees -> {
      System.out.println("Test \"applyAllChildren\" w/printing as func to apply");
      for (GameTreeNode g : gameTrees) {
        System.out.println("Is m2 Legal: " + g.isLegal(g.getGameState(), m2));
        System.out.println("Is m3 Legal: " + g.isLegal(g.getGameState(), m3));
      }
    };

    int numChildren = g1.getGameState().getPossibleActions().size();
    List<GameTreeNode> testedChildren = g1.applyAllChildren(testConsumer);
    assertEquals(numChildren, testedChildren.size());
  }

  @Test
  public void isLegal() {
    assertTrue(g1.isLegal(state1, m1));
    assertTrue(g2.isLegal(trivialGs, trivPass));
    assertFalse(g1.isLegal(state1, m4));
    assertFalse(g2.isLegal(state1, p2));
    assertFalse(g1.isLegal(state1, p1));
    assertFalse(g2.isLegal(state1, m5));
  }
}