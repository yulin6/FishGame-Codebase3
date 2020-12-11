package client;

import static utils.JsonUtils.getFishMessageType;
import static utils.JsonUtils.parseColorFromPlayingAsMessage;
import static utils.JsonUtils.parseStateFromMessage;
import static utils.JsonUtils.parseWonFromEndMessage;
import static utils.JsonUtils.sendMoveReply;
import static utils.JsonUtils.sendPlacementReply;
import static utils.JsonUtils.sendSkipReply;
import static utils.JsonUtils.sendVoidReply;


import game.model.Action;
import game.model.BoardPosition;
import game.model.GameState;
import game.model.GameTreeNode;
import game.model.Move;
import game.model.Pass;
import java.io.*;

import java.net.*;

import java.time.Duration;
import java.time.Instant;
import player.FixedDepthPlayerComponent;
import player.IPlayerComponent;
import game.model.Penguin.PenguinColor;

/**
 * Represents a client who can connect to a remote server running a game of Fish.
 */
public class FishClient {
  private final int TIMEOUT = 60000;
  private final int NAME_LEN = 12;
  private final int SEARCH_DEPTH = 1;


  private final String ip;
  private final int port;
  private Socket clientSocket;
  // This will never be used, and only serves to adapt to the FixedDepthPlayerComponent spec.
  private int age = 0;

  /**
   * Initializes a FishClient, connecting to the provided server and registering by sending a random
   * name, and then replying to all messages the tournament manager sends as per the protocol
   * defined here: https://www.ccs.neu.edu/home/matthias/4500-f20/remote.html
   *
   * @param ip the IP of the server to connect to
   * @param port the port to connect to the server on
   * @throws IOException if the server connection fails
   */
  public FishClient(String ip, int port) throws IOException {
    this.ip = ip;
    this.port = port;
  }

  /**
   * creates a Socket for connecting with the game server, and starts playing the tournament.
   * @throws IOException
   */
  public boolean joinTournament() throws IOException {
    this.clientSocket = new Socket(this.ip, this.port);
    DataInputStream readable = new DataInputStream(clientSocket.getInputStream());
    DataOutputStream writable = new DataOutputStream(clientSocket.getOutputStream());
    boolean won = this.playTournament(readable, writable);
    clientSocket.close();
    return won;
  }

  /**
   * creates a name for signing up the tournament, and does different actions when received different message from server:
   * - when the message is "start" or "playing-with", sends "void" back to server.
   * - when the message is "playing-as", create a FixedDepthPlayerComponent with given color.
   * - when the message is "setup", parses the GameState, determines the next placement, and send back a position.
   * - when the message is "take-turn", parses the GameState, determines the next movement, and send back a action.
   * - when the message is "end", parses the boolean represents win or lose, reply server with "void", and return the boolean.
   *
   * @param readable a DataInputStream
   * @param writable a DataOutputStream
   * @throws IOException thrown by Streams
   */
  public boolean playTournament(DataInputStream readable, DataOutputStream writable)
      throws IOException {
    writable.writeUTF(this.makeName());

    IPlayerComponent playerComponent = null;
    GameTreeNode gameTree = null;

    Instant lastServerMessageTime = Instant.now();

    while (Duration.between(lastServerMessageTime, Instant.now()).toMillis() < this.TIMEOUT) {
      if (readable.available() != 0) { // available() returns an estimate of the number of bytes that can be read.
        lastServerMessageTime = Instant.now();
        String message = readable.readUTF();
        String messageType = getFishMessageType(message);

        switch (messageType) {
          case "start":
          case "playing-with":
            sendVoidReply(writable);
            break;
          case "playing-as":
            PenguinColor color = parseColorFromPlayingAsMessage(message);
            playerComponent = this.createPlayer(color);
            playerComponent.joinTournament();
            sendVoidReply(writable);
            break;
          case "setup":
            GameState gameState = parseStateFromMessage(message);
            this.determineAndSendPlacement(writable, playerComponent, gameState);
            break;
          case "take-turn":
            GameState newState = parseStateFromMessage(message);
            this.determineAndSendMove(writable, playerComponent, newState);
            break;
          case "end":
            boolean won = parseWonFromEndMessage(message);
            playerComponent.leaveTournament(won);
            sendVoidReply(writable);
            return won;
          default:
            throw new RuntimeException("Invalid message type");
        }
      }
    }
    return false;
  }

  /**
   * Creates the IPlayerComponent used to make decisions for this Client.
   *
   * Should be overridden by testing classes with malicious IPlayerComponents.
   *
   * @param color the player's color
   * @return a standard well-behaved IPlayerComponent with a fixed search depth
   */
  public IPlayerComponent createPlayer(PenguinColor color) {
    return new FixedDepthPlayerComponent(this.age, SEARCH_DEPTH, color);
  }

  /**
   * @return a random numeric name based on the current nanoTime (not guaranteed unique)
   */
  public String makeName() {
    String time = String.valueOf(System.nanoTime());
    int len = time.length();
    return time.substring(len - NAME_LEN, len);
  }

  /**
   * Determines the player's next desired placement, then sends a message to the server with the
   * placement.
   *
   * @param player the player making the placement
   * @param gameState the current state of the game
   */
  public void determineAndSendPlacement(
      DataOutputStream output, IPlayerComponent player, GameState gameState
  ) throws IOException {
    if (player == null) {
      throw new IllegalStateException("Cannot place a penguin before color is assigned");
    }
    BoardPosition position = player.placePenguin(new GameTreeNode(gameState)).getPosition();
    sendPlacementReply(output, position);
  }

  /**
   * Determines the player's next desired move and sends it to the server, or sends false if the
   * player has no moves and must skip.
   *
   * @param player the player currently making the move
   * @param gameState the current state of the game
   */
  public void determineAndSendMove(
      DataOutputStream output, IPlayerComponent player, GameState gameState
  ) throws IOException {
    if (player == null) {
      throw new IllegalStateException("Cannot take a turn before color is assigned");
    }

    Action action = player.takeTurn(new GameTreeNode(gameState));
    if (action instanceof Pass) {
      sendSkipReply(output);
    } else if (action instanceof Move) {
      sendMoveReply(output, ((Move) action).getStart(), ((Move) action).getDestination());
    }
  }
}
