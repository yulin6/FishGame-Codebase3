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

/**
 * The TournamentManagerAdapter is a a wrapper of TournamentManager, is used for running the tournament for
 * FishClientProxies. It also implements StateChangeListener for receiving all the player colors in each game.
 */
public class TournamentManagerAdapter implements ITournamentManager, StateChangeListener {
  private TournamentManager tournamentManager;
  private List<FishClientProxy> players;
  private List<PenguinColor> currentGameColors;

  /**
   * the constructor of TournamentManagerAdapter takes in a list of FishClientProxies, which is an IPlayerComponent
   * for remote players, and then it create a TournamentManager with the list of FishClientProxies.
   *
   * @param players a list of FishClientProxies
   */
  public TournamentManagerAdapter(List<FishClientProxy> players) {
    this.players = players;
    for (FishClientProxy proxy: players){
      proxy.setTournamentManagerAdapter(this);
    }
    List<IPlayerComponent> playerComponents = new ArrayList<>(this.players);
    this.tournamentManager = new TournamentManager(playerComponents);
  }

  @Override
  public void runTournament() {
    this.tournamentManager.addGameListener(this);
    this.tournamentManager.runTournament();
  }

  @Override
  public List<IPlayerComponent> getWinners() throws IllegalStateException {
    return this.tournamentManager.getWinners();
  }

  @Override
  public void gameStarted(GameState gs) {
    this.currentGameColors = new ArrayList<>();
    for (Player player : gs.getPlayers()) {
      this.currentGameColors.add(player.getColor());
    }
  }

  @Override
  public void actionPerformed(Action action) { }

  @Override
  public void newGameState(GameState gs) { }

  /**
   * get all the player colors in the current game.
   *
   * @return a list of player colors
   */
  List<PenguinColor> getColorsInCurrentGame() {
    return new ArrayList<>(this.currentGameColors);
  }

  /**
   * the getter method of tournamentManager.
   *
   * @return the TournamentManager
   */
  public TournamentManager getTournamentManager() {
    return tournamentManager;
  }
}
