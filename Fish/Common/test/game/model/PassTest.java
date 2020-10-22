package game.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Class for unit tests of objects of the Pass class; tests performing passes and equality.
 */
public class PassTest {
  Pass p1;
  Pass p2;
  Pass p3;
  Pass p5;
  Player player1;
  Player player2;
  Player player3;
  Player player4;
  Player player5;

  int row = 8;
  int col = 8;
  int minTiles = 3;
  IBoard holeBoard;
  ArrayList<BoardPosition> holes;
  HashSet<Player> players;
  GameState state1;

  @Before
  public void setUp() {
    player1 = new Player(21, Penguin.PenguinColor.BLACK);
    player2 = new Player(17, Penguin.PenguinColor.RED);
    player3 = new Player(21, Penguin.PenguinColor.BLACK);
    player4 = new Player(21, Penguin.PenguinColor.WHITE);
    player5 = new Player(21, Penguin.PenguinColor.BROWN);

    players = new HashSet<>();
    players.add(player1);
    players.add(player2);
    players.add(player4);
    players.add(player5);

    holes = new ArrayList<>();
    holes.add(new BoardPosition(0, 0));
    holes.add(new BoardPosition(3, 1));
    holes.add(new BoardPosition(2, 1));
    holes.add(new BoardPosition(1, 2));

    holeBoard = new Board(row, col, holes, minTiles);
    state1 = new GameState(players, holeBoard);

    p1 = new Pass(player1);
    p2 = new Pass(player2);
    p3 = new Pass(player3);
    p5 = new Pass(player5);
  }

  @Test
  public void perform() {
    assertEquals(player2, state1.getCurrentPlayer());
    p2.perform(state1);
    assertEquals(player1, state1.getCurrentPlayer());
    p1.perform(state1);
    assertEquals(player5, state1.getCurrentPlayer());
    p5.perform(state1);
    assertEquals(player4, state1.getCurrentPlayer());
  }

  @Test
  public void testEquals() {
    assertEquals(player1, player3);
    assertNotEquals(player1, player2);
  }
}