import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import game.model.Board;
import game.model.BoardPosition;
import game.model.GameState;
import game.model.Penguin;
import game.model.Player;

/**
 * Class to represent the State type objects in Java after being deserialized
 * from JSON.
 * State objects in JSON form are { "players" : Player*, "board" : Board}
 * where Players is a JSON array of "Player" as described in the documentation of TestPlayer,
 * and Board is a JSON array of numbers from 0 to 5.
 */
public class State {
  private List<TestPlayer> players;
  private List<List<Integer>> board;

  public State(GameState gs, HashSet<Player> pset, Board b) {
    HashMap<BoardPosition, Penguin> map = gs.getPenguins();
    Set<BoardPosition> positions = map.keySet();
    this.players = new ArrayList<>();
    boolean firstPlayer = true;
    Player fp = null;
    for (Player p : pset) {
      if (firstPlayer) {
        firstPlayer = false;
        fp = p;
        continue;
      }
      List<BoardPosition> penguins = new ArrayList<>();
      for (BoardPosition bp : positions) {
        if (map.get(bp).getColor() == p.getColor()) {
          penguins.add(bp);
        }
      }

      TestPlayer tp = new TestPlayer(p.getColor(), p.getFish(), penguins);
      this.players.add(tp);
    }
    assert(fp != null);
    List<BoardPosition> penguins = new ArrayList<>();
    for (BoardPosition bp : positions) {
      if (map.get(bp).getColor() == fp.getColor()) {
        penguins.add(bp);
      }
    }

    TestPlayer tp = new TestPlayer(fp.getColor(), fp.getFish(), penguins);
    this.players.add(tp);

    this.board = new ArrayList<>();
    for (int i = 0; i < b.getRows(); i++) {
      ArrayList<Integer> thisRow = new ArrayList<>();
      for (int j = 0; j < b.getCols(); j++) {
        thisRow.add(b.getSpace(new BoardPosition(i, j)).getNumFish());
      }
      board.add(thisRow);
    }
  }

  public Board getBoard() {
    int rows = board.size();
    int cols = 0;
    for (List<Integer> integers : board) {
      if (integers.size() > cols) {
        cols = integers.size();
      }
    }

    return new Board(rows, cols, board);
  }

  public LinkedHashSet<Player> getPlayers() {
    LinkedHashSet<Player> playerList = new LinkedHashSet<>();
    int i = 0;
    for (TestPlayer t : players) {
      Player p = new Player(i++, t.getColor());
      playerList.add(p);
    }
    return playerList;
  }

  public List<TestPlayer> getTestPlayers() {
    return players;
  }
}
