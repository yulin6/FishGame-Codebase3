package server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import game.model.Board;
import game.model.GameState;
import game.model.Penguin.PenguinColor;
import game.model.Player;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;

public class TournamentManagerAdapterTest {

  private DataOutputStream serverWritable;
  private DataInputStream serverReadable;
  private DataOutputStream clientWritable;
  private DataInputStream clientReadable;

  @Before
  public void init() throws IOException {
    this.serverWritable = new DataOutputStream(
        new FileOutputStream("Fish/Remote/test/writable/serverWritable.txt"));
    this.serverReadable = new DataInputStream(
        new FileInputStream("Fish/Remote/test/writable/clientWritable.txt"));
    this.clientReadable = new DataInputStream(
        new FileInputStream("Fish/Remote/test/writable/serverWritable.txt"));
    this.clientWritable = new DataOutputStream(
        new FileOutputStream("Fish/Remote/test/writable/clientWritable.txt"));
  }

  @Test
  public void testRunTournament() {
    // TODO
  }

  @Test
  public void testGameStartedAndGetColorsInCurrentGame() {
    ArrayList<FishClientProxy> proxies = new ArrayList<>();
    proxies.add(new FishClientProxy(this.serverReadable, this.serverWritable, 0));
    proxies.add(new FishClientProxy(this.serverReadable, this.serverWritable, 1));
    TournamentManagerAdapter tma = new TournamentManagerAdapter(proxies);

    HashSet<Player> players = new HashSet<>();
    players.add(new Player(0, PenguinColor.RED));
    players.add(new Player(0, PenguinColor.BLACK));
    Board board = new Board(4,4,4);
    GameState gs = new GameState(players, board);

    tma.gameStarted(gs);
    assertTrue(tma.getColorsInCurrentGame().contains(PenguinColor.RED));
    assertTrue(tma.getColorsInCurrentGame().contains(PenguinColor.BLACK));
  }
}
