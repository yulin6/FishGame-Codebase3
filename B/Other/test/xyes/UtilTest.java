package xyes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Class to test the methods of Util.
 */
public class UtilTest {

  @Test
  public void concat() {
    Util util = new Util();
    String[] strArray1 = {"array", "of", "strings"};
    String[] strArray2 = {"singleword"};
    String[] strArray3 = {"bunch", "of strings with spaces in them", "with one at the end "};
    assertEquals("array of strings", util.concat(strArray1));
    assertEquals("singleword", util.concat(strArray2));
    assertEquals("bunch of strings with spaces in them with one at the end ",
            util.concat(strArray3));
  }
}