package game.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

public class GameStateTest {

  int row = 8;
  int col = 8;
  int minTiles = 3;
  int uniformNumFish = 2;
  IBoard holeBoard;
  IBoard uniformBoard;
  ArrayList<BoardPosition> holes;

  Player p1;
  Player p2;
  Player p3;
  Player p4;
  HashSet<Player> players;

  IState state1;
  IState state2;

  @Before
  public void setUp() {
    holes = new ArrayList<>();
    holes.add(new BoardPosition(0, 0));
    holes.add(new BoardPosition(3, 1));
    holes.add(new BoardPosition(2, 1));
    holes.add(new BoardPosition(1, 2));

    p1 = new Player(17, Penguin.PenguinColor.BLACK);
    p2 = new Player(14, Penguin.PenguinColor.BROWN);
    p3 = new Player(10, Penguin.PenguinColor.RED);
    p4 = new Player(21, Penguin.PenguinColor.WHITE);
    players = new HashSet<>();
    players.add(p1);
    players.add(p2);
    players.add(p3);
    players.add(p4);

    holeBoard = new Board(row, col, holes, minTiles);
    state1 = new GameState(players, holeBoard);

    uniformBoard = new Board(row, col, uniformNumFish);
    state2 = new GameState(players, uniformBoard);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testStateConstructorDuplicateColors() {
    players.add(new Player(23, Penguin.PenguinColor.WHITE));
    state1 = new GameState(players, holeBoard);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStateConstructorNoPlayers() {
    state1 = new GameState(new HashSet<>(), holeBoard);
  }

  @Test
  public void placeAvatarAndGetPenguinAtPosn() {
    BoardPosition placement1 = new BoardPosition(2, 2);
    BoardPosition placement2 = new BoardPosition(3, 0);
    BoardPosition placement3 = new BoardPosition(5, 3);
    BoardPosition placement4 = new BoardPosition(7, 1);

    state1.placeAvatar(placement1, p1);
    state1.placeAvatar(placement2, p2);
    state1.placeAvatar(placement3, p3);
    state1.placeAvatar(placement4, p4);

    assertEquals(p1.getColor(), state1.getPenguinAtPosn(placement1).getColor());
    assertNotEquals(p1.getColor(), state1.getPenguinAtPosn(placement2).getColor());
    assertEquals(p2.getColor(), state1.getPenguinAtPosn(placement2).getColor());
    assertNotEquals(p2.getColor(), state1.getPenguinAtPosn(placement3).getColor());
    assertEquals(p3.getColor(), state1.getPenguinAtPosn(placement3).getColor());
    assertNotEquals(p4.getColor(), state1.getPenguinAtPosn(placement3).getColor());
    assertEquals(p4.getColor(), state1.getPenguinAtPosn(placement4).getColor());
    assertNotEquals(p4.getColor(), state1.getPenguinAtPosn(placement1).getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPenguinAtUnoccupied() {
    BoardPosition placement = new BoardPosition(2,2);

    state1.placeAvatar(placement, p1);
    state1.getPenguinAtPosn(new BoardPosition(1, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceAvatarAtOccupiedSpace() {
    BoardPosition placement = new BoardPosition(2,2);

    state1.placeAvatar(placement, p1);
    state1.placeAvatar(placement, p2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceAvatarOOB() {
    BoardPosition placement = new BoardPosition(-1,0);

    state1.placeAvatar(placement, p1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceAvatarInHole() {
    BoardPosition placement = new BoardPosition(3,1);

    state1.placeAvatar(placement, p1);
  }

  @Test
  public void moveAvatar() {
    BoardPosition placement1From = new BoardPosition(2, 2);
    BoardPosition placement2From = new BoardPosition(3, 0);
    BoardPosition placement3From = new BoardPosition(5, 3);
    BoardPosition placement4From = new BoardPosition(7, 1);

    state1.placeAvatar(placement1From, p1);
    state1.placeAvatar(placement2From, p2);
    state1.placeAvatar(placement3From, p3);
    state1.placeAvatar(placement4From, p4);

    assertEquals(p1.getColor(), state1.getPenguinAtPosn(placement1From).getColor());
    assertEquals(p2.getColor(), state1.getPenguinAtPosn(placement2From).getColor());
    assertEquals(p3.getColor(), state1.getPenguinAtPosn(placement3From).getColor());
    assertEquals(p4.getColor(), state1.getPenguinAtPosn(placement4From).getColor());

    BoardPosition placement1To = new BoardPosition(0, 2);
    BoardPosition placement2To = new BoardPosition(4, 1);
    BoardPosition placement3To = new BoardPosition(3, 3);
    BoardPosition placement4To = new BoardPosition(5, 1);

    //holes.add(new BoardPosition(0, 0));
    //holes.add(new BoardPosition(3, 1));
    //holes.add(new BoardPosition(2, 1));
    //holes.add(new BoardPosition(1, 2));

    state1.moveAvatar(placement1To, placement1From, p1);
    state1.moveAvatar(placement2To, placement2From, p2);
    state1.moveAvatar(placement3To, placement3From, p3);
    state1.moveAvatar(placement4To, placement4From, p4);

    assertEquals(p1.getColor(), state1.getPenguinAtPosn(placement1To).getColor());
    assertEquals(p2.getColor(), state1.getPenguinAtPosn(placement2To).getColor());
    assertEquals(p3.getColor(), state1.getPenguinAtPosn(placement3To).getColor());
    assertEquals(p4.getColor(), state1.getPenguinAtPosn(placement4To).getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveAvatarOOB() {
    BoardPosition placementFrom = new BoardPosition(3,0);
    BoardPosition placementTo = new BoardPosition(0, 10);

    state1.placeAvatar(placementFrom, p1);
    state1.moveAvatar(placementTo, placementFrom, p1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveAvatarToHole() {
    BoardPosition placementFrom = new BoardPosition(3,0);
    BoardPosition placementTo = new BoardPosition(3, 1);

    state1.placeAvatar(placementFrom, p1);
    state1.moveAvatar(placementTo, placementFrom, p1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveOtherPlayerAvatar() {
    BoardPosition placementFrom = new BoardPosition(3,0);
    BoardPosition placementTo = new BoardPosition(3, 1);

    state1.placeAvatar(placementFrom, p1);
    state1.moveAvatar(placementTo, placementFrom, p2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveAvatarToOccupiedTile() {
    BoardPosition placementFrom = new BoardPosition(3,0);
    BoardPosition placementTo = new BoardPosition(3, 1);

    state1.placeAvatar(placementFrom, p1);
    state1.placeAvatar(placementTo, p2);
    state1.moveAvatar(placementTo, placementFrom, p1);
  }

  @Test
  public void movesPossible() {
    state1.placeAvatar(new BoardPosition(6, 0), p1);
    assertTrue(state1.movesPossible());

    //Holes surround penguin, no more moves
    holes = new ArrayList<>();
    holes.add(new BoardPosition(0, 0));
    holes.add(new BoardPosition(0, 1));
    holes.add(new BoardPosition(1, 1));


    holeBoard = new Board(2, 2, holes, 1);
    state1 = new GameState(players, holeBoard);

    state1.placeAvatar(new BoardPosition(1, 0), p1);
    assertFalse(state1.movesPossible());

    uniformBoard = new Board(2, 2, uniformNumFish);
    state2 = new GameState(players, uniformBoard);

    //Pneguins surround each other, no more moves
    state2.placeAvatar(new BoardPosition(0,0), p1);
    state2.placeAvatar(new BoardPosition(0, 1), p2);
    state2.placeAvatar(new BoardPosition(1, 0), p3);
    state2.placeAvatar(new BoardPosition(1, 1), p4);
    assertFalse(state2.movesPossible());
  }

  @Test
  public void getAndSetCurrentPlayer() {
    // p3 --> p2 --> p1 --> p4 is order in ascending age
    assertEquals(p3, state2.getCurrentPlayer());
    state2.setNextPlayer();
    assertEquals(p2, state2.getCurrentPlayer());
    state2.setNextPlayer();
    assertEquals(p1, state2.getCurrentPlayer());
    state2.setNextPlayer();
    assertEquals(p4, state2.getCurrentPlayer());
    state2.setNextPlayer();
    assertEquals(p3, state2.getCurrentPlayer());
  }

  @Test
  public void removePlayer() {
    state2.removePlayer(p2);

    //Now cycle through players to make sure p2 is not in there
    assertEquals(p3, state2.getCurrentPlayer());
    state2.setNextPlayer();

    //p2 would be here in the ordering
    assertEquals(p1, state2.getCurrentPlayer());
    assertNotEquals(p2, state2.getCurrentPlayer());
  }
}