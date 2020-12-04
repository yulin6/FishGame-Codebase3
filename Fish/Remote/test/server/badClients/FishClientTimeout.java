package server.badClients;

import client.FishClient;
import game.model.Penguin.PenguinColor;
import java.io.IOException;
import player.IPlayerComponent;
import player.InfiniteLoopPlayerComponent;

public class FishClientTimeout extends FishClient {
  private final boolean loopGetAge;
  private final boolean loopJoinTournament;

  public FishClientTimeout(int port, boolean loopGetAge, boolean loopJoinTournament) throws IOException {
    super("127.0.0.1", port);
    this.loopGetAge = loopGetAge;
    this.loopJoinTournament = loopJoinTournament;
  }

  @Override
  public IPlayerComponent createPlayer(PenguinColor penguinColor) {
    return new InfiniteLoopPlayerComponent(this.loopGetAge, this.loopJoinTournament);
  }
}
