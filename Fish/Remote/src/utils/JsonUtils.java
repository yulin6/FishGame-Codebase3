package utils;

import game.model.Penguin.PenguinColor;
import java.util.List;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class JsonUtils {
  public static boolean isStartMessage(String message) {
    return message.startsWith("[\"start\"");
  }

  public static boolean isPlayingAsMessage(String message) {
    return message.startsWith("[\"playing-as\"");
  }

  /*
  public static PenguinColor parseColorFromPlayingAsMessage(String message) {
    throw new NotImplementedException();
  }
  */

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
}