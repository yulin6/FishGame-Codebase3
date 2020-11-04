package game.model;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class JSONGameState extends GameState {
  public JSONGameState(String jsonToParse) {
    State s = gson.fromJson(jsonToParse, State.class);

    List<TestPlayer> tplayers = s.getTestPlayers();
    LinkedHashSet<Player> realPlayers = s.getPlayers();
    LinkedHashMap<Penguin.PenguinColor, Player> colorPlayers = new LinkedHashMap<>();
    for (Player p : realPlayers) {
      colorPlayers.put(p.getColor(), p);
    }
    Board b = s.getBoard();
    super(playerSet, b);
  }
}
