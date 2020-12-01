package utils;

import game.model.BoardPosition;
import game.model.GameState;
import game.model.Penguin.PenguinColor;
import game.model.Player;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;

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
    message = message.toLowerCase();
    if (message.contains("red")) {
      return PenguinColor.RED;
    } else if (message.contains("brown")) {
      return PenguinColor.BROWN;
    } else if (message.contains("black")) {
      return PenguinColor.BLACK;
    } else if (message.contains("white")) {
      return PenguinColor.WHITE;
    } else {
      throw new IllegalArgumentException("Message does not contain a color: " + message);
    }
  }

  public static GameState parseStateFromSetupMessage(String message) {
    // HashSet<Player> playerSet
    // IBoard b
    return new GameState();
  }

  public static void sendSkip(ObjectOutputStream output) throws IOException {
    output.writeChars("false");
  }

  public static void sendPlacement(ObjectOutputStream output, BoardPosition position) throws IOException {
    output.writeChars("[" + position.getRow() + "," + position.getCol() + "]");
  }

  public static void sendMove(ObjectOutputStream output, BoardPosition start, BoardPosition end) throws IOException {
    String startPosn = "[" + start.getRow() + "," + start.getCol() +  "]";
    String endPosn = "[" + end.getRow() + "," + end.getCol() +  "]";
    output.writeChars("[" + startPosn + "," + endPosn + "]");
  }
}