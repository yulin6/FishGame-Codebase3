import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import game.model.Board;
import game.model.BoardPosition;
import game.model.GameState;
import game.model.Penguin;
import game.model.Player;

/**
 * Class to represent the Move-Response-Query JSON input after being deserialized from JSON. A
 * Move-Response-Query is a JSON Object in the form of { "state" : State, "from" : Position,
 * "to" : Position }. State and Position are as defined in previous testing tasks.
 */
public class MovResQue {
  private State state;
  private List<Integer> from;
  private List<Integer> to;

  public BoardPosition getFrom() {
    return new BoardPosition(from.get(0), from.get(1));
    //return new BoardPosition(from.getRow(), from.getCol());
  }

  public BoardPosition getTo() {
    return new BoardPosition(to.get(0), to.get(1));
    //return new BoardPosition(to.getRow(), to.getCol());
  }

  public GameState getGameState() {
    List<TestPlayer> tplayers = state.getTestPlayers();
    LinkedHashSet<Player> realPlayers = state.getPlayers();
    LinkedHashMap<Penguin.PenguinColor, Player> colorPlayers = new LinkedHashMap<>();
    for (Player p : realPlayers) {
      colorPlayers.put(p.getColor(), p);
    }
    Board b = state.getBoard();

    GameState gs = new GameState(realPlayers, b);
    for (TestPlayer tp : tplayers) {
      colorPlayers.get(tp.getColor()).addFish(tp.getScore());
      for (BoardPosition bp : tp.getPlaces()) {
        gs.placeAvatar(bp, colorPlayers.get(tp.getColor()));
      }
    }
    return gs;
  }
}
