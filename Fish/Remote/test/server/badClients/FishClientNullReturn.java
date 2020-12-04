package server.badClients;

import client.FishClient;
import game.model.Penguin.PenguinColor;
import java.io.IOException;
import player.IPlayerComponent;
import player.NullReturnPlayerComponent;

public class FishClientNullReturn extends FishClient {
  FishClientNullReturn(int port) throws IOException {
    super("127.0.0.1", port);
  }

  @Override
  public IPlayerComponent createPlayer(PenguinColor penguinColor) {
    return new NullReturnPlayerComponent();
  }
}
