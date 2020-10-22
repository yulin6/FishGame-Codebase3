package game.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Class for unit tests of objects of the Move class; tests performing moves and equality.
 */
public class MoveTest {
  Move m1;
  Move m2;
  Player player1;
  Player player2;
  Player player3;
  Player player4;

  BoardPosition placement1;
  BoardPosition placement2;
  BoardPosition placement3;
  BoardPosition placement4;
  BoardPosition from1;
  BoardPosition from2;
  BoardPosition to1;
  BoardPosition to2;

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
    player3 = new Player(21, Penguin.PenguinColor.WHITE);
    player4 = new Player(21, Penguin.PenguinColor.BROWN);

    players = new HashSet<>();
    players.add(player1);
    players.add(player2);
    players.add(player3);
    players.add(player4);

    holes = new ArrayList<>();
    holes.add(new BoardPosition(0, 0));
    holes.add(new BoardPosition(3, 1));
    holes.add(new BoardPosition(2, 1));
    holes.add(new BoardPosition(1, 2));

    holeBoard = new Board(row, col, holes, minTiles);
    state1 = new GameState(players, holeBoard);

    placement1 = new BoardPosition(2, 2);
    placement2 = new BoardPosition(3, 0);
    placement3 = new BoardPosition(5, 3);
    placement4 = new BoardPosition(7, 1);

    state1.placeAvatar(placement1, player2);
    state1.placeAvatar(placement2, player1);
    state1.placeAvatar(placement3, player4);
    state1.placeAvatar(placement4, player3);

    from1 = new BoardPosition(2, 2);
    from2 = new BoardPosition(3, 0);
    to1 = new BoardPosition(0, 2);
    to2 = new BoardPosition(4, 1);

    m1 = new Move(to1, from1, player2);
    m2 = new Move(to2, from2, player1);
  }

  @Test
  public void perform() {
    assertEquals(player2, state1.getCurrentPlayer());
    m1.perform(state1);
    assertEquals(player1, state1.getCurrentPlayer());
    assertEquals(player2.getColor(), state1.getPenguinAtPosn(to1).getColor());
    m2.perform(state1);
    assertEquals(player4, state1.getCurrentPlayer());
    assertEquals(player1.getColor(), state1.getPenguinAtPosn(to2).getColor());
  }

  @Test
  public void testEquals() {
    Move m3 = new Move(to1, from1, player2);
    assertEquals(m1, m3);
    assertNotEquals(m1, m2);
    assertNotEquals(m2, m3);
    assertNotEquals(m2, m3);
    Move m4 = new Move(from1, to1, player3);
    assertNotEquals(m3, m4);
  }
}