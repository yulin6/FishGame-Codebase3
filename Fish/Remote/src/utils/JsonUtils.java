package utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import game.model.*;
import game.model.Penguin.PenguinColor;

import java.io.DataOutputStream;
import java.util.List;
import state.State;

import java.io.IOException;
import java.util.LinkedHashSet;
import state.TestPlayer;


/**
 * the class handles all the serialization and deserialization of the messages between client and server.
 */
public class JsonUtils {


  /**
   * CLIENT --> SERVER
   * sends server a message of "void" by writing to given DataOutputStream.
   *
   * @param writable  a DataOutputStream
   * @throws IOException thrown by writing to DataOutputStream
   */
  public static void sendVoidReply(DataOutputStream writable) throws IOException {
    writable.writeUTF("void");
  }

  /**
   * CLIENT --> SERVER
   * sends server a message of "false" by writing to given DataOutputStream.
   *
   * @param writable a DataOutputStream
   * @throws IOException thrown by writing to DataOutputStream
   */
  public static void sendSkipReply(DataOutputStream writable) throws IOException {
    writable.writeUTF("false");
  }

  /**
   * CLIENT --> SERVER
   * serializes the given BoardPosition and send back to server by writing to given DataOutputStream.
   *
   * @param writable a DataOutputStream
   * @param position a BoardPosition
   * @throws IOException thrown by writing to DataOutputStream
   */
  public static void sendPlacementReply(DataOutputStream writable, BoardPosition position) throws IOException {
    Gson gson = new Gson();
    Integer[] placement = {position.getRow(), position.getCol()};
    writable.writeUTF(gson.toJson(placement));
  }

  /**
   * CLIENT --> SERVER
   * uses given BoardPositions for creating a Move, and serialize it before sending back to server
   * by writing to given DataOutputStream.
   *
   * @param writable a DataOutputStream
   * @param start a start position of the Move
   * @param end an end position of the Move
   * @throws IOException thrown by writing to DataOutputStream
   */
  public static void sendMoveReply(DataOutputStream writable, BoardPosition start, BoardPosition end) throws IOException {
    Gson gson = new Gson();
    Integer[][] moveArr = {{start.getRow(), start.getCol()}, {end.getRow(), end.getCol()}};
    writable.writeUTF(gson.toJson(moveArr));
  }

  /**
   * SERVER --> CLIENT
   * sends the serialized "start" message to client for signaling the start of tournament.
   *
   * @param writable a DataOutputStream
   * @throws IOException thrown by writing to DataOutputStream
   */
  public static void sendStartMessage(DataOutputStream writable) throws IOException {
    JsonArray args = new JsonArray();
    args.add(true);
    sendServerMessage(writable, "start", args);
  }

  /**
   * SERVER --> CLIENT
   * sends the serialized "playing-as" message to client for informing their color in the game the of tournament.
   *
   * @param writable a DataOutputStream
   * @param color the PenguinColor
   * @throws IOException thrown by writing to DataOutputStream
   */
  public static void sendPlayingAsMessage(DataOutputStream writable, PenguinColor color)
      throws IOException  {
    JsonArray args = new JsonArray();
    args.add(color.toString());
    sendServerMessage(writable, "playing-as", args);
  }

  /**
   * SERVER --> CLIENT
   * sends the serialized "playing-with" message to client for informing their opponents' colors
   * in the game the of tournament.
   *
   * @param writable a DataOutputStream
   * @param colors list of opponents' colors
   * @throws IOException thrown by writing to DataOutputStream
   */
  public static void sendPlayingWithMessage(DataOutputStream writable, List<PenguinColor> colors)
      throws IOException  {
    JsonArray args = new JsonArray();
    for (PenguinColor color : colors) {
      args.add(color.toString());
    }
    sendServerMessage(writable, "playing-with", args);
  }

  /**
   * SERVER --> CLIENT
   * sends the serialized "setup" message to client for requesting their next position to place a penguin.
   *
   * @param writable a DataOutputStream
   * @param gameState the current GameState
   * @throws IOException thrown by writing to DataOutputStream
   */
  public static void sendSetupMessage(DataOutputStream writable, GameState gameState)
      throws IOException  {
    Gson gson = new Gson();
    JsonArray args = new JsonArray();
    State state = new State(gameState, gameState.getPlayers(), gameState.getBoard());
    JsonObject stateJson = gson.fromJson(gson.toJson(state, State.class), JsonObject.class);
    args.add(stateJson);
    sendServerMessage(writable, "setup", args);
  }

  /**
   * SERVER --> CLIENT
   * sends the serialized "take-turn" message to client for requesting their next move.
   *
   * @param writable a DataOutputStream
   * @param gameState the current GameState
   * @throws IOException thrown by writing to DataOutputStream
   */
  public static void sendTakeTurnMessage(DataOutputStream writable, GameState gameState)
      throws IOException {
    Gson gson = new Gson();
    JsonArray args = new JsonArray();
    State state = new State(gameState, gameState.getPlayers(), gameState.getBoard());
    args.add(gson.fromJson(gson.toJson(state, State.class), JsonObject.class));
    args.add(new JsonArray());

    sendServerMessage(writable, "take-turn", args);
  }

  /**
   * SERVER --> CLIENT
   * sends the serialized "end" message to client for signaling the end of tournament.
   *
   * @param writable a DataOutputStream
   * @param winner a boolean for determining whether they win or lose the tournament
   * @throws IOException thrown by writing to DataOutputStream
   */
  public static void sendEndMessage(DataOutputStream writable, boolean winner) throws IOException {
    JsonArray args = new JsonArray();
    args.add(winner);
    sendServerMessage(writable, "end", args);
  }



  /**
   * SERVER --> CLIENT
   * a helper method for serializing the input messageType and args, and sending the serialized string to client.
   *
   * @param writable a DataOutputStream
   * @param messageType the type of message
   * @param args the argument array
   * @throws IOException thrown by writing to DataOutputStream
   */
  private static void sendServerMessage(DataOutputStream writable, String messageType, JsonArray args)
      throws IOException {
    JsonArray message = new JsonArray();
    message.add(messageType);
    message.add(args);
    String jsonString = new Gson().toJson(message);
    writable.writeUTF(jsonString);
  }


  /**
   * CLIENT
   * parses the type of message sent from the game server and return the type String.
   *
   * @param message the message sent from the game server.
   * @return the type of message
   */
  public static String getFishMessageType(String message) {
    Gson gson = new Gson();
    JsonArray msg = gson.fromJson(message, JsonArray.class);
    return msg.get(0).getAsString();
  }

  /**
   * CLIENT
   * parses the "playing-as" message and return the deserialized PenguinColor.
   *
   * @param message the "playing-as" message sent from server
   * @return a PenguinColor.
   */
  public static PenguinColor parseColorFromPlayingAsMessage(String message) {
    JsonArray argList = parseArgsList(message);
    String color = argList.get(0).getAsString().toLowerCase();

    switch(color) {
      case "red":
        return PenguinColor.RED;
      case "brown":
        return PenguinColor.BROWN;
      case "black":
        return PenguinColor.BLACK;
      case "white":
        return PenguinColor.WHITE;
      default:
        throw new IllegalArgumentException("Message does not contain a valid color: " + message);
    }
  }

  /**
   * CLIENT
   * parses the game state that contains in the message and return the deserialized GameState.
   *
   * @param message a message sent from server
   * @return a GameState parsed in from the message
   */
  public static GameState parseStateFromMessage(String message) {
    Gson gson = new Gson();
    JsonArray argList = parseArgsList(message);
    JsonElement stateElement = argList.get(0);

    State state = gson.fromJson(stateElement, State.class);
    LinkedHashSet<Player> realPlayers = state.getPlayers();
    Board b = state.getBoard();
    GameState gs = new GameState(realPlayers, b);

    List<TestPlayer> testPlayers = state.getTestPlayers();
    for (TestPlayer testPlayer : testPlayers) {
      for (Player player : realPlayers) {
        if (player.getColor() == testPlayer.getColor()) {
          for (BoardPosition pos : testPlayer.getPlaces()) {
            gs.placeAvatar(pos, player);
          }
        }
      }
    }

    return gs;
  }

  /**
   * CLIENT
   * parses an "End" message and returns a boolean for determining win or lose in the tournament.
   *
   * @param message an "End" message sent from server
   * @return a boolean represents whether they win or lose the tournament
   */
  public static boolean parseWonFromEndMessage(String message) {
    JsonArray argList = parseArgsList(message);
    return argList.get(0).getAsBoolean();
  }

  /**
   * SERVER
   * parses the position sent back from client and returns the deserialized BoardPosition
   *
   * @param reply a serialized position sent from client
   * @return a deserialized BoardPosition
   */
  public static BoardPosition parsePositionFromReply(String reply) {
    Gson gson = new Gson();
    JsonArray positionArr = gson.fromJson(reply, JsonArray.class);
    return new BoardPosition(positionArr.get(0).getAsInt(), positionArr.get(1).getAsInt());
  }

  /**
   * SERVER
   * parses the action send back from client and returns the deserialized action, which is either a Pass or a Move.
   *
   * @param reply a serialized action sent from client
   * @param player the player who sends the action
   * @return a deserialized action
   */
  public static Action parseActionFromReply(String reply, Player player) {
    if (reply.equals("false")) {
      return new Pass(player);
    }
    else {
      Gson gson = new Gson();
      JsonArray positionArr = gson.fromJson(reply, JsonArray.class);
      JsonArray start = positionArr.get(0).getAsJsonArray();
      JsonArray end = positionArr.get(1).getAsJsonArray();
      return new Move( //to, from, player
              new BoardPosition(end.get(0).getAsInt(), end.get(1).getAsInt()),
              new BoardPosition(start.get(0).getAsInt(), start.get(1).getAsInt()),
              player
      );
    }
  }

  /**
   * parses the input message and return the parsed JsonArray.
   *
   * @param message the string of the function call
   * @return a JsonArray of the function call.
   */
  private static JsonArray parseArgsList(String message){
    Gson gson = new Gson();
    JsonArray jsonArray = gson.fromJson(message, JsonArray.class);
    JsonArray argList = (JsonArray) jsonArray.get(1);
    return argList;
  }
}