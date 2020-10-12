package game.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class to test interface methods of Tile.
 */
public class TileTest {

  Tile t1;
  Tile t2;
  Tile t3;
  Tile t4;

  @Before
  public void setUp() {
    t1 = new Tile(3);
    t2 = new Tile(1);
    t3 = new Tile(5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void TileException() {
    t4 = new Tile(Board.MIN_FISH - 1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void TileException2() {
    t4 = new Tile(Board.MAX_FISH + 1);
  }

  @Test
  public void getNumFish() {
    assertEquals(3, t1.getNumFish());
    assertEquals(1, t2.getNumFish());
    assertEquals(5, t3.getNumFish());
    assertNotEquals(t1.getNumFish(), t2.getNumFish());
    assertNotEquals(t2.getNumFish(), t3.getNumFish());
  }

  @Test
  public void isHole() {
    assertFalse(t1.isHole());
    assertEquals(t1.isHole(), t2.isHole());
  }
}