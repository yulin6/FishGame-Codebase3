package client;

import static utils.JsonUtils.isEndMessage;
import static utils.JsonUtils.isPlayingAsMessage;
import static utils.JsonUtils.isPlayingWithMessage;
import static utils.JsonUtils.isSetupMessage;
import static utils.JsonUtils.isStartMessage;
import static utils.JsonUtils.isTakeTurnMessage;
import static utils.JsonUtils.parseColorFromPlayingAsMessage;
import static utils.JsonUtils.parseStateFromMessage;
import static utils.JsonUtils.sendMove;
import static utils.JsonUtils.sendPlacement;
import static utils.JsonUtils.sendSkip;


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

  public FishClient(Socket socket) throws IOException {
    this.clientSocket = socket;
    this.joinTournament();
    this.clientSocket.close();
  }

  /**
   *
   * @throws IOException
   */
  private void joinTournament() throws IOException {
    ObjectInputStream input = new ObjectInputStream(this.clientSocket.getInputStream());
    ObjectOutputStream output = new ObjectOutputStream(this.clientSocket.getOutputStream());

    output.writeChars(this.makeName());

    IPlayerComponent playerComponent = null;
    GameTreeNode gameTree = null;
    while(true) {
      String message = input.readUTF();

      if (isStartMessage(message) || isPlayingWithMessage(message)) {
        //TODO How do we return "void"?
        continue;
      }
      else if (isPlayingAsMessage(message)) {
        //TODO How do we return "void"?
        PenguinColor color = parseColorFromPlayingAsMessage(message);
        playerComponent = new FixedDepthPlayerComponent(this.age, SEARCH_DEPTH, color);
      }
      else if (isSetupMessage(message)) {
        if (playerComponent == null) {
          throw new RuntimeException("Cannot place a penguin before color is assigned");
        }
        GameState state = parseStateFromMessage(message);
        this.placePenguin(output, playerComponent, state);
      }
      else if (isTakeTurnMessage(message)) {
        if (playerComponent == null) {
          throw new RuntimeException("Cannot take a turn before color is assigned");
        }
        // TODO: Get the current GameTreeNode without regenerating any children
        GameState newState = parseStateFromMessage(message);
        gameTree = new GameTreeNode(newState);
        this.takeTurn(output, playerComponent, gameTree);
      }
      else if (isEndMessage(message)) {
        //TODO How do we return "void"?
        break;
      }
    }
  }

  /**
   * @return a random name based on the current nanoTime
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
  private void placePenguin(ObjectOutputStream output, IPlayerComponent player, GameState gameState) throws IOException {
    BoardPosition position = player.placePenguin(new GameTreeNode(gameState)).getPosition();
    sendPlacement(output, position);
  }

  /**
   * Determines the player's next desired move and sends it to the server, or sends false if the player
   * has no moves and must skip.
   *
   * @param player the player currently making the move
   * @param gameTree the current game being played
   */
  private void takeTurn(ObjectOutputStream output, IPlayerComponent player, GameTreeNode gameTree) throws IOException{
    Action action = player.takeTurn(gameTree);
    if (action instanceof Pass) {
      sendSkip(output);
    } else if (action instanceof Move) {
      sendMove(output, ((Move) action).getStart(), ((Move) action).getDestination());
    }
  }
}
