package referee;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import game.model.Board;
import game.model.GameState;
import game.model.IBoard;
import game.model.Penguin;
import game.model.Player;
import player.FailingPlayerComponent;
import player.IPlayerComponent;
import player.IllogicalPlayerComponent;
import player.PlayerComponent;

import static org.junit.Assert.*;

/**
 * Class to test the methods of the implementation of the referee component for a game of Fish.
 * Should be able to perform all the functionality described in the referee interface
 * (../src/referee.IReferee.java).
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

  List<IPlayerComponent> pcomponents;

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
    for (IPlayerComponent p : pcomponents) {
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
    // 4 players & 8 tiles -> no moves possible and all players tie to win w/0 fish
    Referee randomRef = new Referee(pcomponents, 2, 4);
    randomRef.notifyGameStart();
    randomRef.runGame();
    randomRef.notifyGameEnd();
    assertEquals(4, randomRef.getWinningPlayers().size());
  }

  @Test
  public void runFullCleanGame() {
    Referee randomRef = new Referee(pcomponents, 5, 5);
    randomRef.notifyGameStart();
    randomRef.runGame();
    randomRef.notifyGameEnd();
    assertEquals(0, randomRef.getCheaters().size());
    assertEquals(0, randomRef.getFailures().size());
    assertNotEquals(0, randomRef.getWinningPlayers().size());
    assertTrue(randomRef.getWinningPlayers().size() <= pcomponents.size());
  }

  @Test
  public void runGameCheaterPresent() {
    List<IPlayerComponent> cheaterList = new ArrayList<>(Arrays.asList(pc1, pc2, pc3,
            new IllogicalPlayerComponent()));
    Referee cheatRef = new Referee(cheaterList, 5, 5);
    cheatRef.notifyGameStart();
    cheatRef.runGame();
    cheatRef.notifyGameEnd();
    assertEquals(1, cheatRef.getCheaters().size());
    assertEquals(0, cheatRef.getFailures().size());
    assertNotEquals(0, cheatRef.getWinningPlayers().size());
    for (IPlayerComponent p : cheatRef.getCheaters()) {
      assertFalse(cheatRef.getWinningPlayers().contains(p));
    }
  }

  @Test
  public void runGameFailurePresent() {
    List<IPlayerComponent> failureList = new ArrayList<>(Arrays.asList(pc1, pc2, pc3,
            new FailingPlayerComponent()));
    Referee failRef = new Referee(failureList, 5, 5);
    failRef.notifyGameStart();
    failRef.runGame();
    failRef.notifyGameEnd();
    assertEquals(1, failRef.getFailures().size());
    assertEquals(0, failRef.getCheaters().size());
    assertNotEquals(0, failRef.getWinningPlayers().size());
    for (IPlayerComponent p : failRef.getFailures()) {
      assertFalse(failRef.getWinningPlayers().contains(p));
    }
  }

  @Test
  public void runGameCheaterAndFailurePresent() {
    List<IPlayerComponent> cfList = new ArrayList<>(Arrays.asList(pc1, pc2, new IllogicalPlayerComponent(),
            new FailingPlayerComponent()));
    Referee cfRef = new Referee(cfList, 5, 5);
    cfRef.notifyGameStart();
    cfRef.runGame();
    cfRef.notifyGameEnd();
    assertEquals(1, cfRef.getFailures().size());
    assertEquals(1, cfRef.getCheaters().size());
    assertNotEquals(0, cfRef.getWinningPlayers().size());
    for (IPlayerComponent p : cfRef.getFailures()) {
      assertFalse(cfRef.getWinningPlayers().contains(p));
    }
    for (IPlayerComponent p : cfRef.getCheaters()) {
      assertFalse(cfRef.getWinningPlayers().contains(p));
    }
  }

  @Test
  public void notifyGameEnd() {
    Referee randomRef = new Referee(pcomponents, 4, 4);
    randomRef.notifyGameStart();
    for (IPlayerComponent p : pcomponents) {
      assertNotNull(p.getColor());
    }
    randomRef.runGame();
    randomRef.notifyGameEnd();
    for (IPlayerComponent p : pcomponents) {
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
    List<IPlayerComponent> oneGoodPlayerList = new ArrayList<>(Arrays.asList(pc1,
            new IllogicalPlayerComponent()));
    Referee ref = new Referee(oneGoodPlayerList, 4, 4);
    ref.notifyGameStart();
    ref.runGame();
    ref.notifyGameEnd();
    assertEquals(1, ref.getWinningPlayers().size());
  }

  @Test
  public void knownOutcomeRunTiedGame() {
    List<Integer> r1 = Arrays.asList(2, 3, 1, 2, 1);
    List<Integer> r2 = Arrays.asList(2, 0, 2, 0, 3);
    List<Integer> r3 = Arrays.asList(4, 0, 0, 3);

    List<List<Integer>> rows = Arrays.asList(r1, r2, r3);

    IBoard b = new Board(3, 5, rows);
    Player p1 = new Player(5, Penguin.PenguinColor.RED);
    Player p2 = new Player(6, Penguin.PenguinColor.BLACK);
    HashSet<Player> players = new HashSet<>(Arrays.asList(p1, p2));

    GameState gs = new GameState(players, b);
    Referee ref = new Referee(gs);
    ref.runGame();
    ref.notifyGameEnd();

    assertEquals(2, ref.getWinningPlayers().size());
    assertEquals(0, ref.getCheaters().size());
    assertEquals(0, ref.getFailures().size());
  }
}