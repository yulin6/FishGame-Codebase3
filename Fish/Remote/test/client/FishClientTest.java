package client;

import static org.junit.Assert.assertEquals;

import game.model.Action;
import game.model.Board;
import game.model.BoardPosition;
import game.model.GameState;
import game.model.GameTreeNode;
import game.model.Move;
import game.model.Pass;
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
import player.FixedDepthPlayerComponent;
import player.IPlayerComponent;
import utils.JsonUtils;

public class FishClientTest {

  private DataOutputStream serverWritable;
  private DataInputStream serverReadable;
  private DataOutputStream clientWritable;
  private DataInputStream clientReadable;
  private FishClient client;
  private HashSet<Player> players;
  private Player p1;
  private Player p2;
  private Board board;
  private GameState gameState;

  @Before
  public void init() throws IOException {
    this.client = new FishClient("127.0.0.1", 12345);

    this.serverWritable = new DataOutputStream(
        new FileOutputStream("Fish/Remote/test/writable/serverWritable.txt"));
    this.serverReadable = new DataInputStream(
        new FileInputStream("Fish/Remote/test/writable/clientWritable.txt"));
    this.clientReadable = new DataInputStream(
        new FileInputStream("Fish/Remote/test/writable/serverWritable.txt"));
    this.clientWritable = new DataOutputStream(
        new FileOutputStream("Fish/Remote/test/writable/clientWritable.txt"));

    this.players = new HashSet<>();
    this.p1 = new Player(17, PenguinColor.BLACK);
    this.p2 = new Player(14, PenguinColor.BROWN);
    this.players.add(p1);
    this.players.add(p2);
    this.board = new Board(4, 3, 4);
    this.gameState = new GameState(this.players, this.board);
  }

  @Test
  public void testMakeName() {
    String randomName = this.client.makeName();
    Long num = Long.parseLong(randomName);
    assertEquals(12, randomName.length());
    assertEquals(0, num.compareTo(num)); // Asserts no error is thrown parsing as number
  }

  @Test(expected = IllegalStateException.class)
  public void testDetermineAndSendPlacementErrorsBeforeColorAssigned() throws IOException {
    this.client.determineAndSendPlacement(this.clientWritable, null, this.gameState);
  }

  @Test
  public void testDetermineAndSendPlacement() throws IOException {
    IPlayerComponent player = new FixedDepthPlayerComponent(0, 2, PenguinColor.BLACK);
    this.client.determineAndSendPlacement(clientWritable, player, this.gameState);
    assertEquals("[0,0]", this.serverReadable.readUTF());
    this.gameState.placeAvatar(new BoardPosition(0, 0), this.p2);
    this.client.determineAndSendPlacement(clientWritable, player, this.gameState);
    assertEquals("[0,1]", this.serverReadable.readUTF());
  }

  @Test(expected = IllegalStateException.class)
  public void testDetermineAndSendMoveErrorsBeforeColorAssigned() throws IOException {
    this.client.determineAndSendMove(this.clientWritable, null, new GameTreeNode(this.gameState));
  }

  private void performMove(BoardPosition start, BoardPosition end, Player player) {
    this.gameState.moveAvatar(end, start, player);
    this.gameState.setNextPlayer();
  }

  @Test
  public void testDetermineAndSendMove() throws IOException {
    IPlayerComponent player = new FixedDepthPlayerComponent(0, 2, PenguinColor.RED);

    for (int i = 0; i < 8; i++) {
      BoardPosition placement = player.placePenguin(new GameTreeNode(this.gameState)).getPosition();
      if (i % 2 == 0) {
        this.gameState.placeAvatar(placement, this.p2);
      } else {
        this.gameState.placeAvatar(placement, this.p1);
      }
    }

    this.client.determineAndSendMove(this.clientWritable, player, new GameTreeNode(this.gameState));
    assertEquals("[[0,2],[2,2]]", this.serverReadable.readUTF());
    this.performMove(new BoardPosition(0, 2), new BoardPosition(2, 2), this.p2);

    this.client.determineAndSendMove(this.clientWritable, player, new GameTreeNode(this.gameState));
    assertEquals("[[1,0],[3,0]]", this.serverReadable.readUTF());
    this.performMove(new BoardPosition(1, 0), new BoardPosition(3, 0), this.p1);

    this.client.determineAndSendMove(this.clientWritable, player, new GameTreeNode(this.gameState));
    assertEquals("[[1,1],[3,1]]", this.serverReadable.readUTF());
    this.performMove(new BoardPosition(1, 1), new BoardPosition(3, 1), this.p2);

    this.client.determineAndSendMove(this.clientWritable, player, new GameTreeNode(this.gameState));
    assertEquals("[[1,2],[3,2]]", this.serverReadable.readUTF());
    this.performMove(new BoardPosition(1, 2), new BoardPosition(3, 2), this.p1);

    this.client.determineAndSendMove(this.clientWritable, player, new GameTreeNode(this.gameState));
    assertEquals("false", this.serverReadable.readUTF());

    this.gameState.setNextPlayer();

    this.client.determineAndSendMove(clientWritable, player, new GameTreeNode(this.gameState));
    assertEquals("false", this.serverReadable.readUTF());
  }

  private Thread makeClientThread(
      FishClient tClient,
      DataInputStream tReadable,
      DataOutputStream tWriteable) {
    return new Thread(() -> {
      try {
        tClient.playTournament(tReadable, tWriteable);
      } catch (IOException ioe) {
        throw new RuntimeException("IOException caught: " + ioe.getMessage());
      }
    });
  }

  private String waitForClientMsg(DataInputStream readable) throws IOException {
    Long startTime = System.currentTimeMillis();
    Long timeout = 500L;
    while (true) {
      if (readable.available() > 0) {
        break;
      } else if (System.currentTimeMillis() - startTime > timeout) {
        throw new RuntimeException("Client thread timeout");
      }
    }
    return readable.readUTF();
  }

  @Test
  public void testPlayNoGameTournament() throws IOException, InterruptedException {
    Thread clientThread = makeClientThread(this.client, this.clientReadable, this.clientWritable);
    clientThread.start();
    String nameMsg = waitForClientMsg(this.serverReadable);
    assertEquals(12, nameMsg.length());
    JsonUtils.sendStartMessage(this.serverWritable);
    JsonUtils.sendEndMessage(this.serverWritable, false);
    String voidReturn = waitForClientMsg(this.serverReadable);
    assertEquals("void", voidReturn);
    clientThread.join();
  }

  private void playTestGameAsBlack() throws IOException {
    JsonUtils.sendPlayingAsMessage(this.serverWritable, PenguinColor.BLACK);
    waitForClientMsg(this.serverReadable);

    ArrayList<PenguinColor> opponents = new ArrayList<>();
    opponents.add(PenguinColor.BROWN);
    JsonUtils.sendPlayingWithMessage(this.serverWritable, opponents);
    waitForClientMsg(this.serverReadable);

    JsonUtils.sendSetupMessage(this.serverWritable, this.gameState);
    assertEquals("[0,0]", waitForClientMsg(this.serverReadable));
    this.gameState.placeAvatar(new BoardPosition(0,0), this.p2);
    this.gameState.placeAvatar(new BoardPosition(0,1), this.p1);

    JsonUtils.sendSetupMessage(this.serverWritable, this.gameState);
    assertEquals("[0,2]", waitForClientMsg(this.serverReadable));
    this.gameState.placeAvatar(new BoardPosition(0,2), this.p2);
    this.gameState.placeAvatar(new BoardPosition(1,0), this.p1);

    JsonUtils.sendSetupMessage(this.serverWritable, this.gameState);
    assertEquals("[1,1]", waitForClientMsg(this.serverReadable));
    this.gameState.placeAvatar(new BoardPosition(1,1), this.p2);
    this.gameState.placeAvatar(new BoardPosition(1,2), this.p1);

    JsonUtils.sendSetupMessage(this.serverWritable, this.gameState);
    assertEquals("[2,0]", waitForClientMsg(this.serverReadable));
    this.gameState.placeAvatar(new BoardPosition(2,0), this.p2);
    this.gameState.placeAvatar(new BoardPosition(2,1), this.p1);


    JsonUtils.sendTakeTurnMessage(this.serverWritable, this.gameState);
    assertEquals("[[0,2],[2,2]]", waitForClientMsg(this.serverReadable));
    this.gameState.moveAvatar(new BoardPosition(2,2), new BoardPosition(0,2), this.p2);
    this.gameState.setNextPlayer();

    BoardPosition from = new BoardPosition(1,2);
    BoardPosition to = new BoardPosition(3,2);
    this.gameState.moveAvatar(to, from, this.p1);
    this.gameState.setNextPlayer();

    JsonUtils.sendTakeTurnMessage(this.serverWritable, this.gameState);
    assertEquals("[[1,1],[3,1]]", waitForClientMsg(this.serverReadable));
    this.gameState.moveAvatar(new BoardPosition(3,1), new BoardPosition(1,1), this.p2);
    this.gameState.setNextPlayer();

    from = new BoardPosition(1,0);
    to = new BoardPosition(3,0);
    this.gameState.moveAvatar(to, from, this.p1);
    this.gameState.setNextPlayer();

    JsonUtils.sendTakeTurnMessage(this.serverWritable, this.gameState);
    assertEquals("false", waitForClientMsg(this.serverReadable));
  }

  private void playTestGameAsBrown() throws IOException {
    JsonUtils.sendPlayingAsMessage(this.serverWritable, PenguinColor.BROWN);
    waitForClientMsg(this.serverReadable);

    ArrayList<PenguinColor> opponents = new ArrayList<>();
    opponents.add(PenguinColor.BLACK);
    JsonUtils.sendPlayingWithMessage(this.serverWritable, opponents);
    waitForClientMsg(this.serverReadable);

    this.gameState.placeAvatar(new BoardPosition(0,0), this.p2);
    JsonUtils.sendSetupMessage(this.serverWritable, this.gameState);
    assertEquals("[0,1]", waitForClientMsg(this.serverReadable));
    this.gameState.placeAvatar(new BoardPosition(0,1), this.p1);

    this.gameState.placeAvatar(new BoardPosition(0,2), this.p2);
    JsonUtils.sendSetupMessage(this.serverWritable, this.gameState);
    assertEquals("[1,0]", waitForClientMsg(this.serverReadable));
    this.gameState.placeAvatar(new BoardPosition(1,0), this.p1);

    this.gameState.placeAvatar(new BoardPosition(1,1), this.p2);
    JsonUtils.sendSetupMessage(this.serverWritable, this.gameState);
    assertEquals("[1,2]", waitForClientMsg(this.serverReadable));
    this.gameState.placeAvatar(new BoardPosition(1,2), this.p1);

    this.gameState.placeAvatar(new BoardPosition(2,0), this.p2);
    JsonUtils.sendSetupMessage(this.serverWritable, this.gameState);
    assertEquals("[2,1]", waitForClientMsg(this.serverReadable));
    this.gameState.placeAvatar(new BoardPosition(2,1), this.p1);

    JsonUtils.sendTakeTurnMessage(this.serverWritable, this.gameState);
    assertEquals("[[1,0],[3,0]]", waitForClientMsg(this.serverReadable));
    this.gameState.moveAvatar(new BoardPosition(3,0), new BoardPosition(1,0), this.p1);
    this.gameState.setNextPlayer();

    BoardPosition from = new BoardPosition(2,0);
    BoardPosition to = new BoardPosition(3,1);
    this.gameState.moveAvatar(to, from, this.p2);
    this.gameState.setNextPlayer();

    JsonUtils.sendTakeTurnMessage(this.serverWritable, this.gameState);
    assertEquals("[[1,2],[2,2]]", waitForClientMsg(this.serverReadable));
    this.gameState.moveAvatar(new BoardPosition(2,2), new BoardPosition(1,2), this.p1);
    this.gameState.setNextPlayer();

    JsonUtils.sendTakeTurnMessage(this.serverWritable, this.gameState);
    assertEquals("[[2,2],[3,2]]", waitForClientMsg(this.serverReadable));
    this.gameState.moveAvatar(new BoardPosition(3,2), new BoardPosition(2,2), this.p1);

    JsonUtils.sendTakeTurnMessage(this.serverWritable, this.gameState);
    assertEquals("false", waitForClientMsg(this.serverReadable));
  }

  @Test
  public void testPlayOneGameTournament() throws IOException, InterruptedException {
    Thread clientThread = makeClientThread(this.client, this.clientReadable, this.clientWritable);
    clientThread.start();
    waitForClientMsg(this.serverReadable);
    JsonUtils.sendStartMessage(this.serverWritable);
    waitForClientMsg(this.serverReadable);

    playTestGameAsBlack();

    JsonUtils.sendEndMessage(this.serverWritable, true);
    waitForClientMsg(this.serverReadable);
    clientThread.join();
  }

  @Test
  public void testPlayMultiGameTournament() throws IOException, InterruptedException {
    Thread clientThread = makeClientThread(this.client, this.clientReadable, this.clientWritable);
    clientThread.start();
    waitForClientMsg(this.serverReadable);
    JsonUtils.sendStartMessage(this.serverWritable);
    waitForClientMsg(this.serverReadable);

    // Game 1
    playTestGameAsBlack();

    // Game 2
    this.board = new Board(4, 3, 4);
    this.gameState = new GameState(this.players, this.board);
    playTestGameAsBrown();

    // Game 3
    this.board = new Board(4, 3, 4);
    this.gameState = new GameState(this.players, this.board);
    playTestGameAsBlack();

    JsonUtils.sendEndMessage(this.serverWritable, false);
    clientThread.join();
  }
}
