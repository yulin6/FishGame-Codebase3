package game.model;

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

  @Before
  public void setUp() {
    p1 = new Penguin(Penguin.PenguinColor.BLACK);
    p2 = new Penguin(Penguin.PenguinColor.RED);
    p3 = new Penguin(Penguin.PenguinColor.WHITE);
  }

  @Test
  public void getColor() {
    assertEquals(Penguin.PenguinColor.WHITE, p3.getColor());
    assertNotEquals(Penguin.PenguinColor.BROWN, p3.getColor());
    assertNotEquals(Penguin.PenguinColor.RED, p3.getColor());
  }
}