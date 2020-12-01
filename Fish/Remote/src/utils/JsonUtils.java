package utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import game.model.*;
import game.model.Penguin.PenguinColor;
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
    String color = argList.get(0).toString().toLowerCase();

    if (color.equals("red")) {
      return PenguinColor.RED;
    } else if (color.equals("brown")) {
      return PenguinColor.BROWN;
    } else if (color.equals("black")) {
      return PenguinColor.BLACK;
    } else if (color.equals("white")) {
      return PenguinColor.WHITE;
    } else {
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

  public static void sendVoid(ObjectOutputStream writable) throws IOException {
    writable.writeUTF("void");
  }

  public static void sendSkip(ObjectOutputStream writable) throws IOException {
    writable.writeUTF("false");
  }

  public static void sendPlacement(ObjectOutputStream writable, BoardPosition position) throws IOException {
    writable.writeUTF("[" + position.getRow() + "," + position.getCol() + "]");
  }

  public static void sendMove(ObjectOutputStream writable, BoardPosition start, BoardPosition end) throws IOException {
    String startPosn = "[" + start.getRow() + "," + start.getCol() +  "]";
    String endPosn = "[" + end.getRow() + "," + end.getCol() +  "]";
    writable.writeUTF("[" + startPosn + "," + endPosn + "]");
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
}