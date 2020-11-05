import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import game.model.Board;
import game.model.BoardPosition;
import game.model.GameState;
import game.model.GameTree;
import game.model.Penguin;
import game.model.Player;
import player.FailingPlayerComponent;
import player.IPlayer;
import player.IllogicalPlayerComponent;
import player.PlayerComponent;

import static org.junit.Assert.*;

/**
 * Class to test the methods of the implementation of the referee component for a game of Fish.
 * Should be able to perform all the functionality described in the referee interface
 * (../src/IReferee.java).
 */
public class RefereeTest {
  Player p1;
  Player p2;
  Player p3;
  Player p4;

  PlayerComponent pc1;
  PlayerComponent pc2;
  PlayerComponent pc3;
  PlayerComponent pc4;

  int seed = 24;

  List<IPlayer> pcomponents;

  @Before
  public void setUp() {
    p1 = new Player(15, Penguin.PenguinColor.RED);
    pc1 = new PlayerComponent(p1.getAge(), seed);

    p2 = new Player(20, Penguin.PenguinColor.BROWN);
    pc2 = new PlayerComponent(p2.getAge(), seed);

    p3 = new Player(25, Penguin.PenguinColor.BLACK);
    pc3 = new PlayerComponent(p3.getAge(), seed);

    p4 = new Player(30, Penguin.PenguinColor.WHITE);
    pc4 = new PlayerComponent(p4.getAge(), seed);

    pcomponents = new ArrayList<>(Arrays.asList(pc1, pc2, pc3, pc4));
  }

  @Test (expected = IllegalArgumentException.class)
  public void constructorBoardSizeException() {
    Referee randomRef = new Referee(pcomponents, 2, 2);
    randomRef.notifyGameStart();
  }

  @Test
  public void notifyGameStart() {
    Referee randomRef = new Referee(pcomponents, 4, 4);
    randomRef.notifyGameStart();
    for (IPlayer p : pcomponents) {
      assertNotNull(p.getColor());
    }
  }

  @Test (expected = IllegalArgumentException.class)
  public void gameStartWrongPhase() {
    Referee randomRef = new Referee(pcomponents, 4, 4);
    randomRef.notifyGameStart();
    randomRef.runGame();
    randomRef.notifyGameEnd();
    randomRef.notifyGameStart();
  }

  @Test
  public void runTrivialGame() {
    Referee randomRef = new Referee(pcomponents, 2, 4);
    randomRef.notifyGameStart();
    randomRef.runGame();
    randomRef.notifyGameEnd();
    assertEquals(4, randomRef.getWinningPlayers().size());
  }

  @Test
  public void runCleanGame() {
    Referee randomRef = new Referee(pcomponents, 5, 5);
    randomRef.notifyGameStart();
    randomRef.runGame();
    randomRef.notifyGameEnd();
    assertEquals(0, randomRef.getCheaters().size());
    assertEquals(0, randomRef.getFailures().size());
  }

  @Test
  public void runGameCheaterPresent() {
    List<IPlayer> cheaterList = new ArrayList<>(Arrays.asList(pc1, pc2, pc3,
            new IllogicalPlayerComponent()));
    Referee cheatRef = new Referee(cheaterList, 5, 5);
    cheatRef.notifyGameStart();
    cheatRef.runGame();
    cheatRef.notifyGameEnd();
    assertEquals(1, cheatRef.getCheaters().size());
    assertEquals(0, cheatRef.getFailures().size());
    assertNotEquals(0, cheatRef.getWinningPlayers().size());
    for (IPlayer p : cheatRef.getCheaters()) {
      assertFalse(cheatRef.getWinningPlayers().contains(p));
    }
  }

  @Test
  public void runGameFailurePresent() {
    List<IPlayer> failureList = new ArrayList<>(Arrays.asList(pc1, pc2, pc3,
            new FailingPlayerComponent()));
    Referee failRef = new Referee(failureList, 5, 5);
    failRef.notifyGameStart();
    failRef.runGame();
    failRef.notifyGameEnd();
    assertEquals(1, failRef.getFailures().size());
    assertEquals(0, failRef.getCheaters().size());
    assertNotEquals(0, failRef.getWinningPlayers().size());
    for (IPlayer p : failRef.getFailures()) {
      assertFalse(failRef.getWinningPlayers().contains(p));
    }
  }

  @Test
  public void runGameCheaterAndFailurePresent() {
    List<IPlayer> cfList = new ArrayList<>(Arrays.asList(pc1, pc2, new IllogicalPlayerComponent(),
            new FailingPlayerComponent()));
    Referee cfRef = new Referee(cfList, 5, 5);
    cfRef.notifyGameStart();
    cfRef.runGame();
    cfRef.notifyGameEnd();
    assertEquals(1, cfRef.getFailures().size());
    assertEquals(1, cfRef.getCheaters().size());
    assertNotEquals(0, cfRef.getWinningPlayers().size());
    for (IPlayer p : cfRef.getFailures()) {
      assertFalse(cfRef.getWinningPlayers().contains(p));
    }
    for (IPlayer p : cfRef.getCheaters()) {
      assertFalse(cfRef.getWinningPlayers().contains(p));
    }
  }

  @Test
  public void notifyGameEnd() {
    Referee randomRef = new Referee(pcomponents, 4, 4);
    randomRef.notifyGameStart();
    for (IPlayer p : pcomponents) {
      assertNotNull(p.getColor());
    }
    randomRef.runGame();
    randomRef.notifyGameEnd();
    for (IPlayer p : pcomponents) {
      try {
        p.getColor();
        fail("Exception for null player component color not thrown");
      } catch (IllegalArgumentException iae) {
        // do nothing, intentionally
      }
    }
  }

  @Test (expected = IllegalArgumentException.class)
  public void notifyGameEndWrongPhase() {
    Referee randomRef = new Referee(pcomponents, 3, 4);
    randomRef.notifyGameStart();
    randomRef.notifyGameEnd();
  }

  @Test
  public void getWinningPlayers() {
    Referee randomRef = new Referee(pcomponents, 2, 5);
    // No matter the game, it should end with minimum 1 winner - ties are >1 winner
    // and if everyone has 0 it ties
    randomRef.notifyGameStart();
    randomRef.runGame();
    randomRef.notifyGameEnd();
    assertNotEquals(0, randomRef.getWinningPlayers().size());
  }

  @Test
  public void getWinningPlayersWrongPhase() {
    Referee randomRef = new Referee(pcomponents, 2, 5);
    randomRef.notifyGameStart();
    randomRef.runGame();
    randomRef.getWinningPlayers();
  }

  @Test (expected = IllegalArgumentException.class)
  public void getFailuresWrongPhase() {
    Referee randomRef = new Referee(pcomponents, 2, 4);
    randomRef.notifyGameStart();
    randomRef.getFailures();
  }

  @Test (expected = IllegalArgumentException.class)
  public void getCheatersWrongPhase() {
    Referee randomRef = new Referee(pcomponents, 4, 2);
    randomRef.notifyGameStart();
    randomRef.getCheaters();
  }

  @Test
  public void soloPlayerFinishesGame() {
    List<IPlayer> oneGoodPlayerList = new ArrayList<>(Arrays.asList(pc1,
            new IllogicalPlayerComponent()));
    Referee ref = new Referee(oneGoodPlayerList, 4, 4);
    ref.notifyGameStart();
    ref.runGame();
    ref.notifyGameEnd();
    assertEquals(1, ref.getWinningPlayers().size());
  }
}