package server;

import static utils.JsonUtils.parseActionFromReply;
import static utils.JsonUtils.parsePositionFromReply;
import static utils.JsonUtils.sendEndMessage;
import static utils.JsonUtils.sendPlayingAsMessage;
import static utils.JsonUtils.sendPlayingWithMessage;
import static utils.JsonUtils.sendSetupMessage;
import static utils.JsonUtils.sendStartMessage;
import static utils.JsonUtils.sendTakeTurnMessage;

import game.model.Action;
import game.model.BoardPosition;
import game.model.GameState;
import game.model.GameTreeNode;
import game.model.Penguin.PenguinColor;
import game.model.Place;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import player.IPlayerComponent;

public class FishClientProxy implements IPlayerComponent {
  private final Long TIMEOUT_MILLIS = 1000L;

  private final int age;
  private final DataInputStream readable;
  private final DataOutputStream writable;
  private TournamentManagerAdapter tma;
  private PenguinColor color;

  /**
   * TODO
   * @param socket
   * @param age
   * @throws IOException
   */
  public FishClientProxy(Socket socket, int age) throws IOException {
    this.age = age;
    this.readable = new DataInputStream(socket.getInputStream());
    this.writable = new DataOutputStream(socket.getOutputStream());
  }

  /**
   * TODO
   * @param readable
   * @param writable
   * @param age
   * @throws IOException
   */
  public FishClientProxy(DataInputStream readable, DataOutputStream writable, int age) {
    this.age = age;
    this.readable = readable;
    this.writable = writable;
  }

  public void setTournamentManagerAdapter(TournamentManagerAdapter tma) {
    this.tma = tma;
  }

  @Override
  public void joinTournament() {
    try {
      sendStartMessage(this.writable);
      expectVoidReply(TIMEOUT_MILLIS);
    } catch (IOException ioe) {
      throw new RuntimeException("IOException");
    }
  }

  @Override
  public void leaveTournament(Boolean winner) {
    try {
      sendEndMessage(this.writable, winner);
      this.expectVoidReply(TIMEOUT_MILLIS);
    } catch (IOException ioe) {
      throw new RuntimeException("IOException");
    }
  }

  @Override
  public void startPlaying(PenguinColor color) {
    this.color = color;
    try {
      sendPlayingAsMessage(this.writable, color);
      expectVoidReply(TIMEOUT_MILLIS);
      List<PenguinColor> opponents = this.tma.getColorsInCurrentGame();
      opponents.remove(color);
      sendPlayingWithMessage(this.writable, opponents);
      expectVoidReply(TIMEOUT_MILLIS);
    } catch(IOException ioe) {
      throw new RuntimeException("IOException");
    }
  }

  @Override
  public Place placePenguin(GameTreeNode gt) {
    try {
      GameState gs = gt.getGameState();
      sendSetupMessage(this.writable, gs);
      String reply = this.readable.readUTF();
      BoardPosition boardPosn = parsePositionFromReply(reply);
      return new Place(boardPosn, gs.getCurrentPlayer());
    } catch(IOException ioe) {
      throw new RuntimeException("IOException");
    }
  }

  @Override
  public Action takeTurn(GameTreeNode gt) {
    try {
      GameState gs = gt.getGameState();
      sendTakeTurnMessage(writable, gs);
      String reply = this.readable.readUTF();
      return parseActionFromReply(reply, gs.getCurrentPlayer());
    } catch(IOException ioe) {
      throw new RuntimeException("IOException");
    }
  }

  @Override
  public void finishPlaying() { }

  @Override
  public int getAge() {
    return this.age;
  }

  @Override
  public PenguinColor getColor() {
    if (this.color == null) {
      throw new IllegalStateException("Cannot get color before color has been assigned");
    }
    return this.color;
  }

  /**
   * TODO
   * @throws IOException
   */
  public void expectVoidReply(Long timeout) throws IOException {
    Long startTime = System.currentTimeMillis();
    while (true) {
      if (readable.available() > 0) {
        break;
      } else if (System.currentTimeMillis() - startTime > timeout) {
        throw new RuntimeException("Client thread timeout");
      }
    }
    String reply = readable.readUTF();
    if (!reply.equals("void")) {
      throw new RuntimeException("Received non-void reply message: " + reply);
    }
  }
}
