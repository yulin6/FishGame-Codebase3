package server;

import static org.junit.Assert.assertEquals;

import game.model.Board;
import game.model.BoardPosition;
import game.model.GameState;
import game.model.GameTreeNode;
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

public class FishClientProxyTest {

  private FishClientProxy proxy;
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
    this.proxy = new FishClientProxy(serverReadable, serverWritable, 0);
  }

  @Test
  public void testJoinTournament() throws IOException {
    this.clientWritable.writeUTF("void");
    this.proxy.joinTournament();
    assertEquals("[\"start\",[true]]", this.clientReadable.readUTF());
  }

  @Test
  public void testLeaveTournament() throws IOException {
    this.clientWritable.writeUTF("void");
    this.proxy.leaveTournament(true);
    assertEquals("[\"end\",[true]]", this.clientReadable.readUTF());

    this.clientWritable.writeUTF("void");
    this.proxy.leaveTournament(false);
    assertEquals("[\"end\",[false]]", this.clientReadable.readUTF());

  }

  public GameState setupTournamentGame() throws IOException {
    ArrayList<FishClientProxy> proxies = new ArrayList<>();
    proxies.add(this.proxy);
    TournamentManagerAdapter tma = new TournamentManagerAdapter(proxies);
    HashSet<Player> players = new HashSet<>();
    players.add(new Player(0, PenguinColor.RED));
    players.add(new Player(1, PenguinColor.BLACK));
    players.add(new Player(2, PenguinColor.BROWN));
    GameState gameState = new GameState(players, new Board(4, 4, 4));
    tma.gameStarted(gameState);
    this.clientWritable.writeUTF("void");
    this.clientWritable.writeUTF("void");
    this.proxy.startPlaying(PenguinColor.RED);
    return gameState;
  }

  @Test
  public void testStartPlayingAndGetColor() throws IOException {
    setupTournamentGame();
    String playingAsMsg = this.clientReadable.readUTF();
    assertEquals("[\"playing-as\",[\"red\"]]", playingAsMsg);
    String playingWithMsg = this.clientReadable.readUTF();
    assertEquals("[\"playing-with\",[\"brown\",\"black\"]]", playingWithMsg);

    assertEquals(PenguinColor.RED, this.proxy.getColor());
  }

  @Test
  public void testPlacePenguin() throws IOException {
    GameState gameState = setupTournamentGame();
    String clear = this.clientReadable.readUTF();
    clear = this.clientReadable.readUTF();

    this.clientWritable.writeUTF("[1,1]");
    this.proxy.placePenguin(new GameTreeNode(gameState));
    String setupMessage = this.clientReadable.readUTF();
    assertEquals("[\"setup\",[{\"players\":["
        + "{\"color\":\"brown\",\"score\":0,\"places\":[]},"
        + "{\"color\":\"black\",\"score\":0,\"places\":[]},"
        + "{\"color\":\"red\",\"score\":0,\"places\":[]}],"
        + "\"board\":[[4,4,4,4],[4,4,4,4],[4,4,4,4],[4,4,4,4]]}]]", setupMessage);
  }

  @Test
  public void testTakeTurn() throws IOException {
    GameState gameState = setupTournamentGame();
    String clear = this.clientReadable.readUTF();
    clear = this.clientReadable.readUTF();
    gameState.placeAvatar(new BoardPosition(0,0), gameState.getCurrentPlayer());

    this.clientWritable.writeUTF("[[0,0],[1,1]]");
    this.proxy.takeTurn(new GameTreeNode(gameState));
    String takeTurnMessage = this.clientReadable.readUTF();
    assertEquals("[\"take-turn\",[{\"players\":["
        + "{\"color\":\"brown\",\"score\":0,\"places\":[]},"
        + "{\"color\":\"black\",\"score\":0,\"places\":[]},"
        + "{\"color\":\"red\",\"score\":0,\"places\":[[0,0]]}],"
        + "\"board\":[[4,4,4,4],[4,4,4,4],[4,4,4,4],[4,4,4,4]]}]]", takeTurnMessage);
  }

  @Test
  public void testGetAge() throws IOException {
    assertEquals(0, this.proxy.getAge());
    FishClientProxy oldProxy = new FishClientProxy(this.serverReadable, this.serverWritable, 10);
    assertEquals(10, oldProxy.getAge());
  }

  @Test (expected = IllegalStateException.class)
  public void testGetColorErrorsBeforeColorAssigned() {
    this.proxy.getColor();
  }


  @Test (expected = RuntimeException.class)
  public void testExpectVoidRsultErrorsOnNonVoidReply() throws IOException {
    this.clientWritable.writeUTF("false");
    this.proxy.expectVoidReply(100L);
  }

  @Test (expected = RuntimeException.class)
  public void testExpectVoidRsultErrorsOnNoReply() throws IOException {
    this.proxy.expectVoidReply(100L);
  }
}
