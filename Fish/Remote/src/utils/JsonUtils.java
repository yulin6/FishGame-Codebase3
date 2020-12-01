package utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import game.model.*;
import game.model.Penguin.PenguinColor;
import state.State;
import state.TestPlayer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;


public class JsonUtils {
  public static boolean isStartMessage(String message) {
    return message.startsWith("[\"start\"");
  }

  public static boolean isPlayingAsMessage(String message) {
    return message.startsWith("[\"playing-as\"");
  }

  public static boolean isPlayingWithMessage(String message) {
    return message.startsWith("[\"playing-with\"");
  }

  public static boolean isSetupMessage(String message) {
    return message.startsWith("[\"setup\"");
  }

  public static boolean isTakeTurnMessage(String message) {
    return message.startsWith("[\"take-turn\"");
  }

  public static boolean isEndMessage(String message) {
    return message.startsWith("[\"end\"");
  }

  public static PenguinColor parseColorFromPlayingAsMessage(String message) {

    JsonArray argList = parseArgsList(message);
    String color = argList.get(0).toString();

    color = color.toLowerCase();
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

  public static void sendSkip(ObjectOutputStream output) throws IOException {
    output.writeUTF("false");
  }

  public static void sendPlacement(ObjectOutputStream output, BoardPosition position) throws IOException {
    output.writeUTF("[" + position.getRow() + "," + position.getCol() + "]");
  }

  public static void sendMove(ObjectOutputStream output, BoardPosition start, BoardPosition end) throws IOException {
    String startPosn = "[" + start.getRow() + "," + start.getCol() +  "]";
    String endPosn = "[" + end.getRow() + "," + end.getCol() +  "]";
    output.writeUTF("[" + startPosn + "," + endPosn + "]");
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

    //TODO Do we even need to do all the contract checking?
    // see https://piazza.com/class/kevisd7ggfb502?cid=453

    if(jsonArray.size() != 2){
      throw new IllegalArgumentException("Malformed message, " +
              "a function call should contain a Name and a list of arguments: "+ message);
    }
    if(!jsonArray.get(1).isJsonArray()){
      throw new IllegalArgumentException("Malformed message, second element should be an argument list: "+ message);
    }
    JsonArray argList = (JsonArray) jsonArray.get(1);

    if (argList.size() == 0){
      throw new IllegalArgumentException("Malformed message with zero argument in the list: " + message);
    }

    return argList;
  }
}