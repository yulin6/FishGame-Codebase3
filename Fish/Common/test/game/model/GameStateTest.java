package game.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
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

  GameState state1;
  GameState state2;

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
  public void copyConstructor() {
    GameState copy1 = new GameState(state1);

    state1.setNextPlayer();
    assertNotSame(copy1.getCurrentPlayer(), state1.getCurrentPlayer());
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
    BoardPosition placement = new BoardPosition(2, 2);

    state1.placeAvatar(placement, p1);
    state1.getPenguinAtPosn(new BoardPosition(1, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceAvatarAtOccupiedSpace() {
    BoardPosition placement = new BoardPosition(2, 2);

    state1.placeAvatar(placement, p1);
    state1.placeAvatar(placement, p2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceAvatarOOB() {
    BoardPosition placement = new BoardPosition(-1, 0);

    state1.placeAvatar(placement, p1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceAvatarInHole() {
    BoardPosition placement = new BoardPosition(3, 1);

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

  @Test
  public void fullPlayerRotation() {
    BoardPosition p1f = new BoardPosition(0, 0);
    BoardPosition p2f = new BoardPosition(1, 1);
    BoardPosition p3f = new BoardPosition(2, 2);
    BoardPosition p4f = new BoardPosition(3, 3);
    BoardPosition p1t = new BoardPosition(4, 0);
    BoardPosition p2t = new BoardPosition(0, 1);
    BoardPosition p3t = new BoardPosition(6, 2);
    BoardPosition p4t = new BoardPosition(1, 3);
    BoardPosition p1t2 = new BoardPosition(4, 1);
    BoardPosition p2t2 = new BoardPosition(2, 1);
    BoardPosition p3t2 = new BoardPosition(7, 2);
    BoardPosition p4t2 = new BoardPosition(0, 3);

    state2.placeAvatar(p1f, p1);
    state2.placeAvatar(p2f, p2);
    state2.placeAvatar(p3f, p3);
    state2.placeAvatar(p4f, p4);

    assertEquals(state2.getCurrentPlayer(), p3);
    state2.moveAvatar(p3t, p3f, p3);
    state2.setNextPlayer();
    assertEquals(state2.getCurrentPlayer(), p2);
    state2.moveAvatar(p2t, p2f, p2);
    state2.setNextPlayer();
    assertEquals(state2.getCurrentPlayer(), p1);
    state2.moveAvatar(p1t, p1f, p1);
    state2.setNextPlayer();
    assertEquals(state2.getCurrentPlayer(), p4);
    state2.moveAvatar(p4t, p4f, p4);
    state2.setNextPlayer();
    // have cycled through each player once; repeat
    assertEquals(state2.getCurrentPlayer(), p3);
    state2.moveAvatar(p3t2, p3t, p3);
    state2.setNextPlayer();
    assertEquals(state2.getCurrentPlayer(), p2);
    state2.moveAvatar(p2t2, p2t, p2);
    state2.setNextPlayer();
    assertEquals(state2.getCurrentPlayer(), p1);
    state2.moveAvatar(p1t2, p1t, p1);
    state2.setNextPlayer();
    assertEquals(state2.getCurrentPlayer(), p4);
    state2.moveAvatar(p4t2, p4t, p4);
    state2.setNextPlayer();
    assertEquals(state2.getCurrentPlayer(), p3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveAvatarOOB() {
    BoardPosition placementFrom = new BoardPosition(3, 0);
    BoardPosition placementTo = new BoardPosition(0, 10);

    state1.placeAvatar(placementFrom, p1);
    state1.moveAvatar(placementTo, placementFrom, p1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveAvatarToHole() {
    BoardPosition placementFrom = new BoardPosition(3, 0);
    BoardPosition placementTo = new BoardPosition(3, 1);

    state1.placeAvatar(placementFrom, p1);
    state1.moveAvatar(placementTo, placementFrom, p1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveOtherPlayerAvatar() {
    BoardPosition placementFrom = new BoardPosition(3, 0);
    BoardPosition placementTo = new BoardPosition(3, 1);

    state1.placeAvatar(placementFrom, p1);
    state1.moveAvatar(placementTo, placementFrom, p2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveAvatarToOccupiedTile() {
    BoardPosition placementFrom = new BoardPosition(3, 0);
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


    holeBoard = new Board(2, 2, holes, 0);
    state1 = new GameState(players, holeBoard);

    state1.placeAvatar(new BoardPosition(1, 0), p1);
    assertFalse(state1.movesPossible());

    uniformBoard = new Board(2, 2, uniformNumFish);
    state2 = new GameState(players, uniformBoard);

    //Pneguins surround each other, no more moves
    state2.placeAvatar(new BoardPosition(0, 0), p1);
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
  public void testTiedAges() {
    // should go in order of 17, 21Bl, 21Br, 21Wh
    Player age21Black = new Player(21, Penguin.PenguinColor.BLACK);
    Player age21Brown = new Player(21, Penguin.PenguinColor.BROWN);
    Player age17Red = new Player(17, Penguin.PenguinColor.RED);
    Player age21White = new Player(21, Penguin.PenguinColor.WHITE);
    HashSet<Player> tiedPlayers = new HashSet<>();
    tiedPlayers.add(age21Black);
    tiedPlayers.add(age21Brown);
    tiedPlayers.add(age17Red);
    tiedPlayers.add(age21White);

    GameState tiedAges = new GameState(tiedPlayers, holeBoard);
    assertEquals(age17Red, tiedAges.getCurrentPlayer());
    tiedAges.setNextPlayer();
    assertEquals(age21Black, tiedAges.getCurrentPlayer());
    tiedAges.setNextPlayer();
    assertEquals(age21Brown, tiedAges.getCurrentPlayer());
    tiedAges.setNextPlayer();
    assertEquals(age21White, tiedAges.getCurrentPlayer());

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

  @Test
  public void removeCurrentPlayer() {
    state2.removePlayer(p3);

    assertEquals(p2, state2.getCurrentPlayer());
    state2.setNextPlayer();
    assertEquals(p1, state2.getCurrentPlayer());
    state2.setNextPlayer();
    assertEquals(p4, state2.getCurrentPlayer());
    state2.setNextPlayer();
    // loop around
    assertEquals(p2, state2.getCurrentPlayer());
  }
}