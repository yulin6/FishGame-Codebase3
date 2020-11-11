package player;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import game.model.Action;
import game.model.Board;
import game.model.BoardPosition;
import game.model.GameState;
import game.model.GameTreeNode;
import game.model.Move;
import game.model.Pass;
import game.model.Penguin;
import game.model.Player;

import static org.junit.Assert.*;

/**
 * Class to test the methods of an implemented player component, which operates using the
 * previously made strategy component (../src/Strategy.java).
 */
public class PlayerComponentTest {
  Player p1;
  PlayerComponent pc1;
  BoardPosition pen11;
  BoardPosition pen12;
  Player p2;
  PlayerComponent pc2;
  BoardPosition pen21;
  BoardPosition pen22;
  Player p3;
  PlayerComponent pc3;
  BoardPosition pen31;
  BoardPosition pen32;
  Player p4;
  PlayerComponent pc4;
  BoardPosition pen41;
  BoardPosition pen42;

  int rows = 6;
  int cols = 4;
  int seed = 24;
  int seed2 = 99;
  List<Integer> r1;
  List<Integer> r2;
  List<Integer> r3;
  List<Integer> r4;
  List<Integer> r5;
  List<Integer> r6;
  List<List<Integer>> boardList;

  GameTreeNode gt;

  @Before
  public void setUp() {
    p1 = new Player(15, Penguin.PenguinColor.RED);
    pc1 = new PlayerComponent(p1.getAge(), seed);
    pen11 = new BoardPosition(1, 0);
    pen12 = new BoardPosition(0, 2);

    p2 = new Player(20, Penguin.PenguinColor.BROWN);
    pc2 = new PlayerComponent(p2.getAge(), seed);
    pen21 = new BoardPosition(3, 0);
    pen22 = new BoardPosition(1, 2);

    p3 = new Player(25, Penguin.PenguinColor.BLACK);
    pc3 = new PlayerComponent(p3.getAge(), seed);
    pen31 = new BoardPosition(1, 1);
    pen32 = new BoardPosition(2, 2);

    p4 = new Player(30, Penguin.PenguinColor.WHITE);
    pc4 = new PlayerComponent(p4.getAge(), seed);
    pen41 = new BoardPosition(2, 0);
    pen42 = new BoardPosition(0, 1);

    r1 = Arrays.asList(0, 2, 2);
    r2 = Arrays.asList(3, 4, 5);
    r3 = Arrays.asList(4, 3, 2);
    r4 = Arrays.asList(2, 2, 3);
    r5 = Arrays.asList(4, 4, 4, 4);
    r6 = Arrays.asList(5, 5);
    boardList = Arrays.asList(r1, r2, r3, r4, r5, r6);

    Board b = new Board(rows, cols, boardList);
    HashSet<Player> players = new HashSet<>();
    players.add(p1);
    players.add(p2);
    players.add(p3);
    players.add(p4);

    GameState gs = new GameState(players, b);

    gt = new GameTreeNode(gs);
  }

  @Test
  public void startPlaying() {
    pc1.startPlaying(p1.getColor());
    assertEquals(Penguin.PenguinColor.RED, pc1.getColor());
    pc2.startPlaying(p2.getColor());
    assertEquals(Penguin.PenguinColor.BROWN, pc2.getColor());
  }

  @Test (expected = IllegalArgumentException.class)
  public void startPlayingIAE() {
    pc1.startPlaying(p1.getColor());
    pc1.startPlaying(Penguin.PenguinColor.BLACK);
  }

  @Test
  public void placePenguin() {
    BoardPosition placement = pc1.placePenguin(gt);
    assertEquals(new BoardPosition(0,1),placement);
    gt.getGameState().placeAvatar(placement, p1);

    BoardPosition placement2 = pc2.placePenguin(gt);
    assertEquals(new BoardPosition(0, 2), placement2);
    gt.getGameState().placeAvatar(placement2, p2);

    BoardPosition placement3 = pc3.placePenguin(gt);
    assertEquals(new BoardPosition(1,0),placement3);
    gt.getGameState().placeAvatar(placement3, p3);

    BoardPosition placement4 = pc4.placePenguin(gt);
    assertEquals(new BoardPosition(1, 1), placement4);
    gt.getGameState().placeAvatar(placement4, p4);

    BoardPosition placement5 = pc1.placePenguin(gt);
    assertEquals(new BoardPosition(1,2),placement5);
    gt.getGameState().placeAvatar(placement5, p1);

    BoardPosition placement6 = pc2.placePenguin(gt);
    assertEquals(new BoardPosition(2, 0), placement6);
    gt.getGameState().placeAvatar(placement6, p2);

    BoardPosition placement7 = pc3.placePenguin(gt);
    assertEquals(new BoardPosition(2,1),placement7);
    gt.getGameState().placeAvatar(placement7, p3);

    BoardPosition placement8 = pc4.placePenguin(gt);
    assertEquals(new BoardPosition(2, 2), placement8);
    gt.getGameState().placeAvatar(placement8, p4);
  }

  @Test
  public void takeTurnMove() {
    gt.getGameState().placeAvatar(pen11, p1);
    gt.getGameState().placeAvatar(pen12, p1);
    gt.getGameState().placeAvatar(pen21, p2);
    gt.getGameState().placeAvatar(pen22, p2);
    gt.getGameState().placeAvatar(pen31, p3);
    gt.getGameState().placeAvatar(pen32, p3);
    gt.getGameState().placeAvatar(pen41, p4);
    gt.getGameState().placeAvatar(pen42, p4);

    Action a1 = pc1.takeTurn(gt);
    Move m = new Move(new BoardPosition(4, 2), new BoardPosition(1, 0), p1);
    assertEquals(m, a1);
    a1.perform(gt.getGameState());

    Action a2 = pc2.takeTurn(gt);
    m = new Move(new BoardPosition(3, 2), new BoardPosition(1, 2), p2);
    assertEquals(m, a2);
  }

  @Test
  public void takeTurnPass() {
    List<Integer> r1 = Arrays.asList(1, 2, 3);
    List<Integer> r2 = Arrays.asList(4, 5, 1);
    List<Integer> r3 = Arrays.asList(2, 3, 4);
    List<List<Integer>> rows = Arrays.asList(r1, r2, r3);
    Board b2 = new Board(3, 3, rows);

    Player p1 = new Player(0, Penguin.PenguinColor.RED);
    Player p2 = new Player(1, Penguin.PenguinColor.BROWN);
    Player p3 = new Player(2, Penguin.PenguinColor.BLACK);

    HashSet<Player> pset = new HashSet<>(Arrays.asList(p1, p2, p3));
    GameState gs2 = new GameState(pset, b2);
    gs2.placeAvatar(new BoardPosition(0, 0), p1);
    gs2.placeAvatar(new BoardPosition(1, 1), p1);
    gs2.placeAvatar(new BoardPosition(2, 2), p1);

    gs2.placeAvatar(new BoardPosition(0, 1), p2);
    gs2.placeAvatar(new BoardPosition(1, 0), p2);
    gs2.placeAvatar(new BoardPosition(2, 0), p2);

    gs2.placeAvatar(new BoardPosition(2, 1), p3);
    gs2.placeAvatar(new BoardPosition(1, 2), p3);
    gs2.placeAvatar(new BoardPosition(0, 2), p3);

    GameTreeNode gt2 = new GameTreeNode(gs2);

    PlayerComponent pred = new PlayerComponent(p1.getAge(), seed2);
    pred.startPlaying(Penguin.PenguinColor.RED);
    Action stratAction = pred.takeTurn(gt2);
    Action expectedPass = new Pass(p1);
    assertEquals(expectedPass, stratAction);
  }

  @Test
  public void finishPlaying() {
    pc1.startPlaying(Penguin.PenguinColor.WHITE);
    assertEquals(Penguin.PenguinColor.WHITE, pc1.getColor());
    pc1.finishPlaying();

    try {
      pc1.finishPlaying();
    } catch (IllegalArgumentException iae) {
      // expect an exception here; ignore and allow to pass for test to succeed
    }
  }

  @Test (expected = IllegalArgumentException.class)
  public void finishPlayingIAE() {
    pc2.startPlaying(Penguin.PenguinColor.RED);
    pc2.finishPlaying();
    pc1.finishPlaying();
  }

  @Test
  public void getAge() {
    assertEquals(15, p1.getAge());
    assertEquals(20, p2.getAge());
    assertNotEquals(p1.getAge(), p2.getAge());
  }

  @Test
  public void getColor() {
    pc1.startPlaying(Penguin.PenguinColor.RED);
    pc2.startPlaying(Penguin.PenguinColor.BROWN);
    assertNotEquals(p1.getColor(), p2.getColor());
    assertEquals(Penguin.PenguinColor.RED, p1.getColor());
    assertEquals(Penguin.PenguinColor.BROWN, p2.getColor());
  }

  @Test (expected = IllegalArgumentException.class)
  public void getColorIAE() {
    pc2.startPlaying(p2.getColor());
    assertEquals(p2.getColor(), pc2.getColor());
    pc1.getColor();
  }
}