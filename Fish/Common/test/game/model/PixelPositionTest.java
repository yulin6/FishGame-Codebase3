package game.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class to test utility class PixelPosition.
 */
public class PixelPositionTest {

  PixelPosition pos1;
  PixelPosition pos2;

  @Before
  public void setUp() {
    pos1 = new PixelPosition(70, 412);
    pos2 = new PixelPosition(150, 243);
  }

  @Test
  public void getX() {
    assertEquals(70, pos1.getX());
    assertEquals(150, pos2.getX());
    assertNotEquals(pos1.getX(), pos2.getX());
  }

  @Test
  public void getY() {
    assertEquals(412, pos1.getY());
    assertEquals(243, pos2.getY());
    assertNotEquals(pos1.getY(), pos2.getY());
  }
}