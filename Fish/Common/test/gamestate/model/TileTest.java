package gamestate.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class to test interface methods of Tile.
 */
public class TileTest {

  Tile.TileBuilder tb = new Tile.TileBuilder();
  Tile t1;
  Tile t2;
  Tile t3;
  BoardPosition pos1;
  BoardPosition pos2;
  BoardPosition pos3;

  @Before
  public void setUp() {
    pos1 = new BoardPosition(3, 2);
    pos2 = new BoardPosition(1, 4);
    pos3 = new BoardPosition(0, 2);

    tb.setFish(3);
    tb.setPosition(pos1);
    t1 = tb.build();

    tb.setFish(1);
    tb.setPosition(pos2);
    t2 = tb.build();

    tb.setFish(5);
    tb.setPosition(pos3);
    t3 = tb.build();
  }

  @Test
  public void setOccupied() {
    assertFalse(t1.isOccupied());
    t1.setOccupied();
    assertTrue(t1.isOccupied());
  }

  @Test
  public void setUnoccupied() {
    assertFalse(t2.isOccupied());
    t2.setOccupied();
    assertTrue(t2.isOccupied());
    t2.setUnoccupied();
    assertFalse(t2.isOccupied());
  }

  @Test
  public void setHole() {
    assertFalse(t3.isHole());
    t3.setHole();
    assertTrue(t3.isHole());
  }

  @Test
  public void getNumFish() {
    assertEquals(3, t1.getNumFish());
    assertEquals(1, t2.getNumFish());
    assertEquals(5, t3.getNumFish());
    assertNotEquals(t1.getNumFish(), t2.getNumFish());
    assertNotEquals(t2.getNumFish(), t3.getNumFish());
  }
}