package xyes;

import java.util.StringJoiner;

/**
 * Utility class to make some functionality testable.
 */
public class Util {

  /**
   * Empty constructor to enable use of methods.
   */
  public Util() {
  }

  /**
   * Concatenates an input array of strings and returns them as a single String with
   * separating spaces between each string.
   * @param words The input array of String objects to concatenate.
   * @return A String representing the concatenated objects with spaces separating them.
   */
  public String concat(String[] words) {
    StringJoiner outString = new StringJoiner(" ");
    for (String s : words) {
      outString.add(s);
    }
    return outString.toString();
  }
}