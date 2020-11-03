package player;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import game.model.Action;
import game.model.Board;
import game.model.BoardPosition;
import game.model.GameState;
import game.model.GameTree;
import game.model.IBoard;
import game.model.Move;
import game.model.Pass;
import game.model.Penguin;
import game.model.Player;

import static org.junit.Assert.*;

public class StrategyTest {
  GameTree gt;
  GameTree gtFull;
  GameTree minMaxTestGt;

  List<List<Integer>> minMaxTestTiles;

  int row = 8;
  int col = 8;
  int minTiles = 3;
  IBoard holeBoard;
  IBoard trivialBoard;
  IBoard minMaxTestBoard;
  int minMaxTestRows = 4;
  int minMaxTestCols = 4;
  ArrayList<BoardPosition> holes;
  HashSet<Player> players;

  BoardPosition placement1;
  BoardPosition placement2;
  BoardPosition placement3;
  BoardPosition placement4;

  BoardPosition minMaxPlacement1;
  BoardPosition minMaxPlacement2;
  BoardPosition minMaxPlacement3;
  BoardPosition minMaxPlacement4;

  Player player1;
  Player player2;
  Player player3;
  Player player4;
  Player tp1;
  Player tp2;

  GameState state1;
  GameState trivialGs;
  GameState minMaxTestGs;

  Strategy strat;

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

    ArrayList<BoardPosition> bps = new ArrayList<>();
    bps.add(new BoardPosition(0,0));
    bps.add(new BoardPosition(0,1));
    trivialBoard = new Board(2,2, bps, 0);
    HashSet<Player> trivialPlayers = new HashSet<>();
    tp1 = new Player(10, Penguin.PenguinColor.BLACK);
    tp2 = new Player(11, Penguin.PenguinColor.BROWN);
    trivialPlayers.add(tp1);
    trivialPlayers.add(tp2);

    trivialGs = new GameState(trivialPlayers, trivialBoard);
    trivialGs.placeAvatar(new BoardPosition(1, 0), tp2);
    trivialGs.placeAvatar(new BoardPosition(1, 1), tp1);

    placement1 = new BoardPosition(2, 2);
    placement2 = new BoardPosition(3, 0);
    placement3 = new BoardPosition(5, 3);
    placement4 = new BoardPosition(7, 1);

    state1.placeAvatar(placement1, player3);
    state1.placeAvatar(placement2, player2);
    state1.placeAvatar(placement3, player1);
    state1.placeAvatar(placement4, player4);

    gt = new GameTree(state1);
    gtFull = new GameTree(trivialGs);

    minMaxTestTiles = new ArrayList<>();

    ArrayList<Integer> firstRow = new ArrayList<>();
    firstRow.add(3);
    firstRow.add(4);
    firstRow.add(1);
    firstRow.add(1);

    ArrayList<Integer> secondRow = new ArrayList<>();
    secondRow.add(2);
    secondRow.add(4);
    secondRow.add(0);
    secondRow.add(2);

    ArrayList<Integer> thirdRow = new ArrayList<>();
    thirdRow.add(4);
    thirdRow.add(5);
    thirdRow.add(1);
    thirdRow.add(0);

    ArrayList<Integer> fourthRow = new ArrayList<>();
    fourthRow.add(5);
    fourthRow.add(1);
    fourthRow.add(5);
    fourthRow.add(3);

    minMaxTestTiles.add(firstRow);
    minMaxTestTiles.add(secondRow);
    minMaxTestTiles.add(thirdRow);
    minMaxTestTiles.add(fourthRow);

    minMaxTestBoard = new Board(minMaxTestRows, minMaxTestCols, minMaxTestTiles);

    minMaxTestGs = new GameState(players, minMaxTestBoard);
    minMaxPlacement1 = new BoardPosition(1, 0);
    minMaxPlacement2 = new BoardPosition(0, 3);
    minMaxPlacement3 = new BoardPosition(3, 1);
    minMaxPlacement4 = new BoardPosition(0, 2);

    minMaxTestGs = new GameState(players, minMaxTestBoard);
    minMaxTestGs.placeAvatar(minMaxPlacement1, player3);
    minMaxTestGs.placeAvatar(minMaxPlacement2, player2);
    minMaxTestGs.placeAvatar(minMaxPlacement3, player1);
    minMaxTestGs.placeAvatar(minMaxPlacement4, player4);

    minMaxTestGt = new GameTree(minMaxTestGs);

    strat = new Strategy();
  }

  @Test
  public void placePenguin() {
    BoardPosition nextPlacement = strat.placePenguin(gt);
    assertEquals(nextPlacement, new BoardPosition(0, 1));
    gt.getGameState().placeAvatar(new BoardPosition(0, 1), player3);
    nextPlacement = strat.placePenguin(gt);
    assertEquals(nextPlacement, new BoardPosition(0, 2));
    gt.getGameState().placeAvatar(new BoardPosition(0, 2), player2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void placePenguinNoSpaces() {
    BoardPosition nextPlacement = strat.placePenguin(gtFull);
  }

  @Test
  public void getMinMaxAction() {
    Player p = minMaxTestGt.getGameState().getCurrentPlayer();
    Action minMaxAction = strat.getMinMaxAction(minMaxTestGt, 2);
    BoardPosition source = minMaxPlacement1;
    BoardPosition dest = new BoardPosition(2, 1);
    Action result = new Move(dest, source, p);
    assertEquals(minMaxAction, result);

    Action minMaxAction2 = strat.getMinMaxAction(minMaxTestGt, 3);
    assertEquals(minMaxAction2, result);
  }

  @Test
  public void getMinMaxAction1Turn()
  {
    Player p = minMaxTestGt.getGameState().getCurrentPlayer();
    Action minMaxAction3 = strat.getMinMaxAction(minMaxTestGt, 1);
    BoardPosition source = minMaxPlacement1;
    BoardPosition dest = new BoardPosition(0, 0);
    Action result = new Move(dest, source, p);
    assertEquals(minMaxAction3, result);
  }

  @Test
  public void getMinMaxActionMultiplePenguins() {
    Player p = minMaxTestGt.getGameState().getCurrentPlayer();
    BoardPosition newPen = new BoardPosition(2, 1);
    minMaxTestGt.getGameState().placeAvatar(newPen, p);
    Action minMaxAction4 = strat.getMinMaxAction(minMaxTestGt, 1);
    BoardPosition dest = new BoardPosition(0, 1);
    Move result = new Move(dest, newPen, p);
    assertEquals(minMaxAction4, result);

    Action minMaxAction5 = strat.getMinMaxAction(minMaxTestGt, 4);
    BoardPosition source = new BoardPosition(1, 0);
    result = new Move(dest, source, p);
    assertEquals(minMaxAction5, result);
  }

  @Test
  public void getMinMaxActionPass() {
    Action minMaxAction6 = strat.getMinMaxAction(gtFull, 2);
    assertEquals(new Pass(tp1), minMaxAction6);
  }

  @Test
  public void getMinMaxActionTiebreaker() {
    Board uniform = new Board(3, 3, 4);
    HashSet<Player> uniPlayers = new HashSet<>();
    uniPlayers.add(tp1);
    uniPlayers.add(tp2);
    GameState uniGs = new GameState(uniPlayers, uniform);
    GameTree uniGt = new GameTree(uniGs);

    BoardPosition pen1 = new BoardPosition(0, 0);
    BoardPosition pen2 = new BoardPosition(2, 0);

    uniGt.getGameState().placeAvatar(pen1, tp2);
    uniGt.getGameState().placeAvatar(pen2, tp1);

    Action tiedDestMove = strat.getMinMaxAction(uniGt, 1);
    BoardPosition expectedDest = new BoardPosition(0, 1);
    Action expectedMove = new Move(expectedDest, pen2, tp1);
    assertEquals(expectedMove, tiedDestMove);
  }

}