package server.badClients;

import client.FishClient;
import game.model.Penguin.PenguinColor;
import java.io.IOException;
import player.ExceptionPlayerComponent;
import player.IPlayerComponent;

public class FishClientException extends FishClient {
  private final boolean errGetAge;
  private final boolean errJoinTournament;

  public FishClientException(int port, boolean errGetAge, boolean errJoinTournament) throws IOException {
    super("127.0.0.1", port);
    this.errGetAge = errGetAge;
    this.errJoinTournament = errJoinTournament;
  }

  @Override
  public IPlayerComponent createPlayer(PenguinColor penguinColor) {
    return new ExceptionPlayerComponent(this.errGetAge, this.errJoinTournament);
  }
}
