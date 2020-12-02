package server;

import game.model.Action;
import game.model.GameState;
import game.model.Penguin.PenguinColor;
import game.model.Player;
import game.observer.StateChangeListener;
import java.util.ArrayList;
import java.util.List;
import player.IPlayerComponent;
import tmanager.ITournamentManager;
import tmanager.TournamentManager;

public class TournamentManagerAdapter implements ITournamentManager, StateChangeListener {
  private TournamentManager tm;
  private List<FishClientProxy> players;
  private List<PenguinColor> currentGameColors;

  public TournamentManagerAdapter(List<FishClientProxy> players) {
    this.players = players;
    for (FishClientProxy proxy: players){
      proxy.setTournamentManagerAdapter(this);
    }
  }

  @Override
  public void runTournament() {
    List<IPlayerComponent> playerComponents = new ArrayList<>(this.players);
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
  public void actionPerformed(Action action) {

  }

  @Override
  public void newGameState(GameState gs) {

  }

  List<PenguinColor> getColorsInCurrentGame() {
    return new ArrayList<>(this.currentGameColors);
  }
}
