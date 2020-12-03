package server;

import static org.junit.Assert.assertEquals;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class FishClientProxyTest {

  private FishClientProxy proxy;
  private DataOutputStream serverWritable;
  private DataInputStream serverReadable;
  private DataOutputStream clientWritable;
  private DataInputStream clientReadable;

  @Before
  public void init() throws FileNotFoundException, IOException {
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

  @Test (expected = RuntimeException.class)
  public void testJoinTournamentErrorsOnNonVoidReply() throws IOException {
    this.clientWritable.writeUTF("false");
    this.proxy.joinTournament();
  }

  @Test (expected = RuntimeException.class)
  public void testJoinTournamentErrorsOnNoReply() {
    this.proxy.joinTournament();
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

  @Test (expected = RuntimeException.class)
  public void testLeaveTournamentErrorsOnNonVoidReply() throws IOException {
    this.clientWritable.writeUTF("false");
    this.proxy.leaveTournament(false);
  }

  @Test (expected = RuntimeException.class)
  public void testLeaveTournamentErrorsOnNoReply() throws IOException {
    this.proxy.leaveTournament(false);
  }

  @Test
  public void testStartPlaying() {
    // TODO
  }

  @Test
  public void testPlacePenguin() {
    // TODO
  }

  @Test
  public void testTakeTurn() {
    // TODO
  }

  @Test
  public void testGetAge() throws IOException {
    assertEquals(0, this.proxy.getAge());
    FishClientProxy oldProxy = new FishClientProxy(this.serverReadable, this.serverWritable, 10);
    assertEquals(10, oldProxy.getAge());
  }

  @Test
  public void testGetColor() {
    // TODO
  }

  @Test (expected = IllegalStateException.class)
  public void testGetColorErrorsBeforeColorAssigned() {
    this.proxy.getColor();
  }

  @Test
  public void testExpectVoidReply() {
    // TODO
  }
}
