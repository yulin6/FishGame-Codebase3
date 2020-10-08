package gamestate.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class to test utility class BoardPosition.
 */
public class BoardPositionTest {

  BoardPosition pos1;
  BoardPosition pos2;

  @Before
  public void setUp() {
    pos1 = new BoardPosition(5, 12);
    pos2 = new BoardPosition(3, 2);
  }

  @Test
  public void getRow() {
    assertEquals(5, pos1.getRow());
    assertEquals(3, pos2.getRow());
    assertNotEquals(pos1.getRow(), pos2.getRow());
  }

  @Test
  public void getCol() {
    assertEquals(12, pos1.getCol());
    assertEquals(2, pos2.getCol());
    assertNotEquals(pos1.getCol(), pos2.getCol());
  }
}