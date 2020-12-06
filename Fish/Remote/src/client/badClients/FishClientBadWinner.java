package client.badClients;

import client.FishClient;
import game.model.Penguin.PenguinColor;
import java.io.IOException;
import player.BadWinnerPlayerComponent;
import player.IPlayerComponent;

public class FishClientBadWinner extends FishClient {
  public FishClientBadWinner(int port) throws IOException {
    super("127.0.0.1", port);
  }

  @Override
  public IPlayerComponent createPlayer(PenguinColor penguinColor) {
    return new BadWinnerPlayerComponent(0);
  }
}
