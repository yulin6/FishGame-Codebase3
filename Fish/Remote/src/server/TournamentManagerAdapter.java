package server;

import game.model.GameState;
import game.model.Penguin.PenguinColor;
import game.model.Player;
import game.observer.StateChangeListener;
import java.util.ArrayList;
import java.util.List;
import player.IPlayerComponent;
import tmanager.ITournamentManager;
import tmanager.TournamentManager;

/**
 * TODO
 */
public class TournamentManagerAdapter implements ITournamentManager, StateChangeListener {
  private TournamentManager tm;
  private List<FishClientProxy> proxies;
  private List<PenguinColor> currentGameColors;

  /**
   * TODO
   * @param players
   */
  public TournamentManagerAdapter(List<FishClientProxy> players) {
    this.proxies = players;
    for (FishClientProxy proxy: players){
      proxy.setTournamentManagerAdapter(this);
    }
  }

  @Override
  public void runTournament() {
    List<IPlayerComponent> playerComponents = new ArrayList<>(this.proxies);
    this.tm = new TournamentManager(playerComponents);
    this.tm.addGameListener(this);
    this.tm.runTournament();
  }

  @Override
  public List<IPlayerComponent> getWinners() throws IllegalStateException {
    return this.tm.getWinners();
  }

  @Override
  public void gameStarted(GameState gs) {
    this.currentGameColors = new ArrayList<>();
    for (Player player : gs.getPlayers()) {
      this.currentGameColors.add(player.getColor());
    }
  }

  @Override
  public void newGameState(GameState gs) { }

  List<PenguinColor> getColorsInCurrentGame() {
    return new ArrayList<>(this.currentGameColors);
  }
}
