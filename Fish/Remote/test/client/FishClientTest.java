package client;

import static org.junit.Assert.assertEquals;

import game.model.Board;
import game.model.GameState;
import game.model.GameTreeNode;
import game.model.IBoard;
import game.model.Penguin;
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

public class FishClientTest {
  DataOutputStream writable;
  DataInputStream readable;
  FishClient client;

  @Before
  public void init() throws IOException {
    this.client = new FishClient("127.0.0.1", 12345);
    this.writable = new DataOutputStream(
        new FileOutputStream("Fish/Remote/test/writable/writable.txt"));
    this.readable = new DataInputStream(
        new FileInputStream("Fish/Remote/test/writable/writable.txt"));
  }

  @Test
  public void testMakeName() {
    String randomName = this.client.makeName();
    Long num = Long.parseLong(randomName);
    assertEquals(12, randomName.length());
    assertEquals(0, num.compareTo(num)); // Asserts no error is thrown parsing as number
  }

  @Test (expected = IllegalStateException.class)
  public void testDetermineAndSendPlacementErrorsBeforeColorAssigned() throws IOException {
    HashSet<Player> players = new HashSet<>();
    Player p1 = new Player(17, Penguin.PenguinColor.BLACK);
    Player p2 = new Player(14, Penguin.PenguinColor.BROWN);
    players.add(p1);
    players.add(p2);
    IBoard board = new Board(2, 2,2);
    client.determineAndSendPlacement(writable, null, new GameState(players, board));
  }

  @Test (expected = IllegalStateException.class)
  public void testDetermineAndSendMoveErrorsBeforeColorAssigned() throws IOException {
    HashSet<Player> players = new HashSet<>();
    Player p1 = new Player(17, Penguin.PenguinColor.BLACK);
    Player p2 = new Player(14, Penguin.PenguinColor.BROWN);
    players.add(p1);
    players.add(p2);
    IBoard board = new Board(2, 2,2);
    GameState gs = new GameState(players, board);
    client.determineAndSendMove(writable, null, new GameTreeNode(gs));
  }
}
