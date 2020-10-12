package gamestate.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class to test the interface methods of Hole objects.
 */
public class HoleTest {

  Hole h;

  @Before
  public void setUp() {
    h = new Hole();
  }

  @Test
  public void getNumFish() {
    assertEquals(0, h.getNumFish());
  }

  @Test
  public void isHole() {
    assertTrue(h.isHole());
  }

}