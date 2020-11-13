package referee;

import game.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import player.ExceptionPlayerComponent;
import player.FailingPlayerComponent;
import player.IPlayerComponent;
import player.IllogicalPlayerComponent;
import player.InfiniteLoopPlayerComponent;
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
  public void onePlaceTurn(){
    List<Integer> r1 = Arrays.asList(2, 3, 1, 2, 1);
    List<Integer> r2 = Arrays.asList(2, 1, 2, 1, 3);
    List<List<Integer>> rows = Arrays.asList(r1, r2);

    IBoard b = new Board(2, 5, rows);
    Player p1 = new Player(5, Penguin.PenguinColor.RED);
    Player p2 = new Player(6, Penguin.PenguinColor.BLACK);
    HashSet<Player> players = new HashSet<>(Arrays.asList(p1, p2));
    GameState gs = new GameState(players, b);
    Referee ref = new Referee(gs);

    ref.setGamePhase(Referee.GamePhase.PLACING);
    ref.takeOneAction();
    BoardPosition position = new BoardPosition(0, 0);
    assertTrue(ref.getGameState().isPenguinAtPosn(position));

    BoardPosition position1 = new BoardPosition(0, 1);
    assertFalse(ref.getGameState().isPenguinAtPosn(position1));
  }

  @Test
  public void twoPlaceTurns(){
    Referee randomRef = new Referee(pcomponents, 5, 5);
    randomRef.notifyGameStart();
    randomRef.setGamePhase(Referee.GamePhase.PLACING);
    randomRef.takeOneAction();
    // Figure out the first valid position to place penguin at for state
    Board b = (Board) randomRef.getGameState().getBoard();
    BoardPosition firstPos = null;
    BoardPosition secondPos = null;
    for (int i = 0; i < b.getRows(); i++) {
      for (int j = 0; j < b.getCols(); j++) {
        BoardPosition p = new BoardPosition(i, j);
        if (b.getSpace(p) instanceof Tile) {
          if (firstPos == null) {
            firstPos = p;
          }
          else if (secondPos == null) {
            secondPos = p;
          }
          else {
            break;
          }
        }
      }
    }
    assertTrue(randomRef.getGameState().isPenguinAtPosn(firstPos));
    randomRef.takeOneAction();
    assertTrue(randomRef.getGameState().isPenguinAtPosn(secondPos));
  }

  @Test
  public void onePlayTurn(){
    List<Integer> r1 = Arrays.asList(2, 3, 1, 2, 1);
    List<Integer> r2 = Arrays.asList(2, 1, 2, 1, 3);
    List<List<Integer>> rows = Arrays.asList(r1, r2);

    IBoard b = new Board(2, 5, rows);
    Player p1 = new Player(5, Penguin.PenguinColor.RED);
    Player p2 = new Player(6, Penguin.PenguinColor.BLACK);
    HashSet<Player> players = new HashSet<>(Arrays.asList(p1, p2));
    GameState gs = new GameState(players, b);
    Referee ref = new Referee(gs);

    ref.setGamePhase(Referee.GamePhase.PLACING);
    ref.doPlacingPhase();

    BoardPosition position = new BoardPosition(0, 4);
    assertFalse(ref.getGameState().getBoard().getSpace(position).isHole());
    ref.takeOneAction();
    assertTrue(ref.getGameState().getBoard().getSpace(position).isHole());
  }

  @Test
  public void onePlaceTurnWithCheatingPlayer(){
    List<IPlayerComponent> cheaterList = new ArrayList<>(Arrays.asList(new IllogicalPlayerComponent(), pc1, pc2, pc3));
    Referee cheatRef = new Referee(cheaterList, 5, 5);
    cheatRef.notifyGameStart();
    cheatRef.setGamePhase(Referee.GamePhase.PLACING);

    assertEquals(4, cheatRef.getGameState().getPlayers().size());
    cheatRef.takeOneAction();
    assertEquals(3, cheatRef.getGameState().getPlayers().size());
  }

  @Test
  public void onePlaceTurnWithFailingPlayer(){
    List<IPlayerComponent> cheaterList = new ArrayList<>(Arrays.asList(new FailingPlayerComponent(), pc1, pc2, pc3));
    Referee cheatRef = new Referee(cheaterList, 5, 5);
    cheatRef.notifyGameStart();
    cheatRef.setGamePhase(Referee.GamePhase.PLACING);

    assertEquals(4, cheatRef.getGameState().getPlayers().size());
    cheatRef.takeOneAction();
    assertEquals(3, cheatRef.getGameState().getPlayers().size());
  }

  @Test
  public void twoPlaceTurnsWithFailingAndCheatingPlayers(){
    List<IPlayerComponent> cheaterList = new ArrayList<>(Arrays.asList(new FailingPlayerComponent(),
            new IllogicalPlayerComponent(), pc1, pc2));
    Referee cheatRef = new Referee(cheaterList, 5, 5);
    cheatRef.notifyGameStart();
    cheatRef.setGamePhase(Referee.GamePhase.PLACING);

    assertEquals(4, cheatRef.getGameState().getPlayers().size());
    cheatRef.takeOneAction();
    assertEquals(3, cheatRef.getGameState().getPlayers().size());
    cheatRef.takeOneAction();
    assertEquals(2, cheatRef.getGameState().getPlayers().size());
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

  @Test
  public void exceptionThrownInGetAge() {
    List<IPlayerComponent> eList =
            new ArrayList<>(Arrays.asList(new ExceptionPlayerComponent(true),
                    pc2, pc3, new ExceptionPlayerComponent(true)));
    Referee eRef = new Referee(eList, 5, 5);
    eRef.setGamePhase(Referee.GamePhase.END);
    assertEquals(2, eRef.getFailures().size());
    assertEquals(0, eRef.getCheaters().size());
  }

  @Test
  public void exceptionPlayersStartPlaying() {
    List<IPlayerComponent> eList =
            new ArrayList<>(Arrays.asList(new ExceptionPlayerComponent(false),
            pc2, pc3, new ExceptionPlayerComponent(false)));
    Referee eRef = new Referee(eList, 5, 5);
    eRef.notifyGameStart();
    eRef.setGamePhase(Referee.GamePhase.END);
    assertEquals(2, eRef.getFailures().size());
    assertEquals(0, eRef.getCheaters().size());
  }

  @Test
  public void placementsWithExceptionPlayers() {
    List<IPlayerComponent> eList = new ArrayList<>(Arrays.asList(pc1, pc2,
            new ExceptionPlayerComponent(false), new ExceptionPlayerComponent(false)));
    Referee eRef = new Referee(eList, 5, 5);
    eRef.setGamePhase(Referee.GamePhase.PLACING);
    eRef.doPlacingPhase();
    eRef.setGamePhase(Referee.GamePhase.END);
    assertEquals(2, eRef.getFailures().size());
    assertEquals(0, eRef.getCheaters().size());
  }

  @Test
  public void exceptionPlayersMakingMoves() {
    List<IPlayerComponent> eList = new ArrayList<>(Arrays.asList(pc1, pc2,
            new ExceptionPlayerComponent(false), new ExceptionPlayerComponent(false)));
    Referee eRef = new Referee(eList, 5, 5);
    eRef.setGamePhase(Referee.GamePhase.PLACING);
    eRef.doPlacingPhase();
    eRef.setGamePhase(Referee.GamePhase.END);
    assertEquals(2, eRef.getFailures().size());
    assertEquals(0, eRef.getCheaters().size());
  }

  @Test
  public void exceptionPlayersFinishPlaying() {
    List<IPlayerComponent> eList = new ArrayList<>(Arrays.asList(pc1, pc2,
            new ExceptionPlayerComponent(false), pc4));
    Referee eRef = new Referee(eList, 5, 5);
    pc1.startPlaying(Penguin.PenguinColor.RED);
    pc2.startPlaying(Penguin.PenguinColor.BROWN);
    pc4.startPlaying(Penguin.PenguinColor.WHITE);
    eRef.setGamePhase(Referee.GamePhase.END);
    eRef.notifyGameEnd();
    assertEquals(1, eRef.getFailures().size());
    assertEquals(0, eRef.getCheaters().size());
  }

  @Test
  public void runGameWithExceptionPlayer() {
    List<IPlayerComponent> eList = new ArrayList<>(Arrays.asList(pc1, pc2,
            new ExceptionPlayerComponent(false), pc4));
    Referee eRef = new Referee(eList, 5, 5);
    eRef.notifyGameStart();
    eRef.runGame();
    eRef.notifyGameEnd();
    assertEquals(1, eRef.getFailures().size());
    assertEquals(0, eRef.getCheaters().size());
    assertNotEquals(0, eRef.getWinningPlayers().size());
    for (IPlayerComponent p : eRef.getFailures()) {
      assertFalse(eRef.getWinningPlayers().contains(p));
    }
  }

  @Test
  public void infiniteLoopInGetAge() {
    System.out.println("Entering a timeout-based test, a pause will occur.");
    List<IPlayerComponent> infList =
            new ArrayList<>(Arrays.asList(new InfiniteLoopPlayerComponent(true),
            new InfiniteLoopPlayerComponent(true), pc3, pc4));
    Referee infRef = new Referee(infList, 5, 5);
    infRef.setGamePhase(Referee.GamePhase.END);
    assertEquals(2, infRef.getFailures().size());
    assertEquals(0, infRef.getCheaters().size());
  }

  @Test
  public void startPlayingInfiniteLoop() {
    System.out.println("Entering a timeout-based test, a pause will occur.");
    List<IPlayerComponent> infList =
            new ArrayList<>(Arrays.asList(new InfiniteLoopPlayerComponent(false),
            pc2, pc3, pc4));
    Referee infRef = new Referee(infList, 5, 5);
    infRef.notifyGameStart();
    infRef.setGamePhase(Referee.GamePhase.END);
    assertEquals(1, infRef.getFailures().size());
    assertEquals(0, infRef.getCheaters().size());
  }

  @Test
  public void placementsWithInfiniteLoopPlayers() {
    System.out.println("Entering a timeout-based test, a pause will occur.");
    List<IPlayerComponent> infList =
            new ArrayList<>(Arrays.asList(new InfiniteLoopPlayerComponent(false),
            new InfiniteLoopPlayerComponent(false), new InfiniteLoopPlayerComponent(false), pc4));
    Referee infRef = new Referee(infList, 5, 5);
    infRef.notifyGameStart();
    infRef.doPlacingPhase();
    infRef.setGamePhase(Referee.GamePhase.END);
    assertEquals(3, infRef.getFailures().size());
    assertEquals(0, infRef.getCheaters().size());
  }

  @Test
  public void finishPlayingInfiniteLoop() {
    System.out.println("Entering a timeout-based test, a pause will occur.");
    List<IPlayerComponent> infList =
            new ArrayList<>(Arrays.asList(new InfiniteLoopPlayerComponent(false),
            pc2, pc3, pc4));
    Referee infRef = new Referee(infList, 5, 5);
    pc2.startPlaying(Penguin.PenguinColor.BLACK);
    pc3.startPlaying(Penguin.PenguinColor.WHITE);
    pc4.startPlaying(Penguin.PenguinColor.RED);
    infRef.setGamePhase(Referee.GamePhase.END);
    infRef.notifyGameEnd();
    assertEquals(1, infRef.getFailures().size());
    assertEquals(0, infRef.getCheaters().size());
  }

  @Test
  public void runGameWithInfiniteLoopAndExceptionPlayers() {
    System.out.println("Entering a timeout-based test, a pause will occur.");
    List<IPlayerComponent> infAndExcList = new ArrayList<>(Arrays.asList(pc1,
            new InfiniteLoopPlayerComponent(false),
            new ExceptionPlayerComponent(false),
            new InfiniteLoopPlayerComponent(false)));
    Referee infAndExcRef = new Referee(infAndExcList, 5, 5);
    infAndExcRef.notifyGameStart();
    infAndExcRef.runGame();
    infAndExcRef.notifyGameEnd();
    assertEquals(3, infAndExcRef.getFailures().size());
    assertEquals(0, infAndExcRef.getCheaters().size());
    assertEquals(1, infAndExcRef.getWinningPlayers().size());
    for (IPlayerComponent p : infAndExcRef.getFailures()) {
      assertFalse(infAndExcRef.getWinningPlayers().contains(p));
    }
  }
}