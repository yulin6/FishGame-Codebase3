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
 * The TournamentManagerAdapter is a wrapper of TournamentManager, is used for running the tournament for
 * FishClientProxies. It also implements StateChangeListener for receiving all the player colors in each game.
 */
public class TournamentManagerAdapter implements ITournamentManager, StateChangeListener {
  private List<FishClientProxy> proxies;
  private TournamentManager tournamentManager;
  private List<PenguinColor> currentGameColors;

  private final int boardRows = 5;
  private final int boardCols = 5;
  private final int fishNum = 2;

  /**
   * the constructor of TournamentManagerAdapter takes in a list of FishClientProxies, which is an IPlayerComponent
   * for remote players, and then it create a TournamentManager with the list of FishClientProxies.
   *
   * @param players a list of FishClientProxies
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
    this.tournamentManager = new TournamentManager(playerComponents, this.boardRows, this.boardCols, this.fishNum);
    this.tournamentManager.addGameListener(this);
    this.tournamentManager.runTournament();
  }

  @Override
  public List<IPlayerComponent> getWinners() throws IllegalStateException {
    return this.tournamentManager.getWinners();
  }

  public List<IPlayerComponent> getCheatersAndFailures() {
    ArrayList<IPlayerComponent> cheatersAndFailures = new ArrayList<>();
    cheatersAndFailures.addAll(this.tournamentManager.getCheaters());
    cheatersAndFailures.addAll(this.tournamentManager.getFailures());
    return cheatersAndFailures;
  }


  @Override
  public void gameStarted(GameState gs) {
    this.currentGameColors = new ArrayList<>();
    for (Player player : gs.getPlayers()) {
      this.currentGameColors.add(player.getColor());
    }
  }

  @Override
  public void gameStateUpdated(GameState gs) { }

  /**
   * get all the player colors in the current game.
   *
   * @return a list of player colors
   */
  List<PenguinColor> getColorsInCurrentGame() {
    return new ArrayList<>(this.currentGameColors);
  }
}
