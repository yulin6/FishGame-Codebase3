package client;

import static utils.JsonUtils.getFishMessageType;
import static utils.JsonUtils.parseColorFromPlayingAsMessage;
import static utils.JsonUtils.parseStateFromMessage;
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

import player.FixedDepthPlayerComponent;
import player.IPlayerComponent;
import game.model.Penguin.PenguinColor;

/**
 * Represents a client who can connect to a remote server running a game of Fish.
 */
public class FishClient extends Thread{
  private final int NAME_LEN = 12;
  private final int SEARCH_DEPTH = 2;

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
    this.clientSocket = new Socket(ip, port);
    this.joinTournament();
    this.clientSocket.close();
  }

  /**
   * TODO
   * @throws IOException
   */
  private void joinTournament() throws IOException {
    DataInputStream readable = new DataInputStream(this.clientSocket.getInputStream());
    DataOutputStream writable = new DataOutputStream(this.clientSocket.getOutputStream());

    writable.writeUTF(this.makeName());

    IPlayerComponent playerComponent = null;
    GameTreeNode gameTree = null;

    while(true) {
      String message = readable.readUTF();
      String messageType = getFishMessageType(message);

      switch(messageType) {
        case "start":
        case "playing-with":
        case "end":
          sendVoidReply(writable);
          break;
        case "playing-as":
          PenguinColor color = parseColorFromPlayingAsMessage(message);
          playerComponent = this.buildPlayerWithColor(color);
          break;
        case "setup":
          GameState gameState = parseStateFromMessage(message);
          this.determineAndSendPlacement(writable, playerComponent, gameState);
          break;
        case "take-turn":
          GameState newState = parseStateFromMessage(message);
          gameTree = new GameTreeNode(newState);
          this.determineAndSendMove(writable, playerComponent, gameTree);
          break;
        default:
          throw new RuntimeException("Invalid message type");
      }
    }
  }

  private IPlayerComponent buildPlayerWithColor(PenguinColor color) {
    return new FixedDepthPlayerComponent(this.age, SEARCH_DEPTH, color);
  }

  /**
   * @return a random numeric name based on the current nanoTime (not guaranteed unique)
   */
  private String makeName() {
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
  private void determineAndSendPlacement(
          DataOutputStream output,
      IPlayerComponent player,
      GameState gameState
  ) throws IOException {
    if (player == null) throw new RuntimeException("Cannot place a penguin before color is assigned");
    BoardPosition position = player.placePenguin(new GameTreeNode(gameState)).getPosition();
    sendPlacementReply(output, position);
  }

  /**
   * Determines the player's next desired move and sends it to the server, or sends false if the
   * player has no moves and must skip.
   *
   * @param player the player currently making the move
   * @param gameTree the current game being played
   */
  private void determineAndSendMove(
          DataOutputStream output,
      IPlayerComponent player,
      GameTreeNode gameTree
  ) throws IOException {
    if (player == null) throw new RuntimeException("Cannot take a turn before color is assigned");

    Action action = player.takeTurn(gameTree);
    if (action instanceof Pass) {
      sendSkipReply(output);
    } else if (action instanceof Move) {
      sendMoveReply(output, ((Move) action).getStart(), ((Move) action).getDestination());
    }
  }
}
