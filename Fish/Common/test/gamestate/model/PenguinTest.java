package gamestate.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the Penguin class.
 */
public class PenguinTest {
  Penguin p1;
  Penguin p2;
  Penguin p3;
  BoardPosition bp1;
  BoardPosition bp2;
  BoardPosition bp3;
  BoardPosition bp4;

  @Before
  public void setUp() {
    bp1 = new BoardPosition(1, 3);
    bp2 = new BoardPosition(4, 3);
    bp3 = new BoardPosition(2, 2);
    bp4 = new BoardPosition(1, 5);
    p1 = new Penguin(Penguin.PenguinColor.BLACK, bp1);
    p2 = new Penguin(Penguin.PenguinColor.RED, bp2);
    p3 = new Penguin(Penguin.PenguinColor.WHITE, bp3);
  }

  @Test
  public void getPosition() {
    assertEquals(new BoardPosition(1, 3), p1.getPosition());
    assertNotEquals(p2.getPosition(), p3.getPosition());
  }

  @Test
  public void setPosition() {
    assertEquals(new BoardPosition(1,3), p1.getPosition());
    assertNotEquals(new BoardPosition(1, 5), p1.getPosition());
    p1.setPosition(bp4);
    assertNotEquals(new BoardPosition(1, 3), p1.getPosition());
    assertEquals(new BoardPosition(1,5), p1.getPosition());
  }

  @Test
  public void getColor() {
    assertEquals(Penguin.PenguinColor.WHITE, p3.getColor());
    assertNotEquals(Penguin.PenguinColor.BROWN, p3.getColor());
    assertNotEquals(Penguin.PenguinColor.RED, p3.getColor());
  }
}