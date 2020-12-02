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
import java.io.ObjectOutputStream;
import java.util.LinkedHashSet;


public class JsonUtils {
  public static String getFishMessageType(String message) {
    Gson gson = new Gson();
    JsonArray msg = gson.fromJson(message, JsonArray.class);
    return msg.get(0).getAsString();
  }

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

  public static GameState parseStateFromMessage(String message) {
    Gson gson = new Gson();
    JsonArray argList = parseArgsList(message);
    JsonElement stateElement = argList.get(0);

    State state = gson.fromJson(stateElement, State.class);
    LinkedHashSet<Player> realPlayers = state.getPlayers();
    Board b = state.getBoard();
    GameState gs = new GameState(realPlayers, b);

    return gs;
  }

  public static void sendVoidReply(DataOutputStream writable) throws IOException {
    writable.writeUTF("void");
  }

  public static void sendSkipReply(DataOutputStream writable) throws IOException {
    writable.writeUTF("false");
  }

  public static void sendPlacementReply(DataOutputStream writable, BoardPosition position) throws IOException {
    Gson gson = new Gson();
    Integer[] placement = {position.getRow(), position.getCol()};
    writable.writeUTF(gson.toJson(placement));
  }

  public static void sendMoveReply(DataOutputStream writable, BoardPosition start, BoardPosition end) throws IOException {
    Gson gson = new Gson();
    Integer[][] moveArr = {{start.getRow(), start.getCol()}, {end.getRow(), end.getCol()}};
    writable.writeUTF(gson.toJson(moveArr));
  }

  public static void sendStartMessage(DataOutputStream writable) throws IOException {
    JsonArray args = new JsonArray();
    args.add(true);
    sendServerMessage(writable, "start", args);
  }
  public static void sendPlayingAsMessage(DataOutputStream writable, PenguinColor color)
      throws IOException  {
    JsonArray args = new JsonArray();
    args.add(color.toString());
    sendServerMessage(writable, "playing-as", args);
  }

  public static void sendPlayingWithMessage(DataOutputStream writable, List<PenguinColor> colors)
      throws IOException  {
    JsonArray args = new JsonArray();
    for (PenguinColor color : colors) {
      args.add(color.toString());
    }
    sendServerMessage(writable, "playing-with", args);
  }

  public static void sendSetupMessage(DataOutputStream writable, GameState gameState)
      throws IOException  {
    Gson gson = new Gson();
    JsonArray args = new JsonArray();
    State state = new State(gameState, gameState.getPlayers(), gameState.getBoard());
    JsonObject stateJson = gson.fromJson(gson.toJson(state, State.class), JsonObject.class);
    args.add(stateJson);
    sendServerMessage(writable, "setup", args);
  }

  public static void sendTakeTurnMessage(
      DataOutputStream writable,
      GameState gameState,
      List<Action> actions
  ) throws IOException {
    Gson gson = new Gson();
    JsonArray args = new JsonArray();
    State state = new State(gameState, gameState.getPlayers(), gameState.getBoard());
    args.add(gson.fromJson(gson.toJson(state, State.class), JsonObject.class));

    JsonArray actionsSinceLastTurn = new JsonArray();
    for (Action action : actions) {
      if (action instanceof Pass) {
        actionsSinceLastTurn.add(false);
      } else if (action instanceof Move) {
        Move move = (Move) action;
        BoardPosition start = move.getStart();
        BoardPosition end = move.getDestination();
        Integer[][] moveArr = {{start.getRow(), start.getCol()}, {end.getRow(), end.getCol()}};
        actionsSinceLastTurn.add(gson.fromJson(gson.toJson(moveArr), JsonArray.class));
      }
    }
    args.add(actionsSinceLastTurn);

    sendServerMessage(writable, "take-turn", args);
  }

  public static void sendEndMessage(DataOutputStream writable, boolean winner) throws IOException {
    JsonArray args = new JsonArray();
    args.add(winner);
    sendServerMessage(writable, "end", args);
  }

  /**
   * Try to parse the input String into a JsonArray, it will throw in the follow situations:
   *  - if the size of parsed JsonArray is not equal to 2.
   *  - if the second element in the parsed JsonArray is not a JsonArray.
   *  - if the inner JsonArray contains no argument.
   *  Then it will return the parsed JsonArray if nothing had been thrown.
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

  private static void sendServerMessage(DataOutputStream writable, String messageType, JsonArray args)
      throws IOException {
    JsonArray message = new JsonArray();
    message.add(messageType);
    message.add(args);
    String jsonString = new Gson().toJson(message);
//    System.out.println(jsonString);
    writable.writeUTF(jsonString);
  }

  public static BoardPosition parsePositionFromReply(String reply) {
    Gson gson = new Gson();
    JsonArray positionArr = gson.fromJson(reply, JsonArray.class);
    return new BoardPosition(positionArr.get(0).getAsInt(), positionArr.get(1).getAsInt());
  }

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
}