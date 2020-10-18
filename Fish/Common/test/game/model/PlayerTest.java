package game.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the Player class which represents a player from the game state's view.
 * TODO implement tests
 */
public class PlayerTest {
  Player p1;
  Player p2;
  Player p3;
  Player p4;

  @Before
  public void setUp() {
    p1 = new Player(20, Penguin.PenguinColor.WHITE);
    p2 = new Player(13, Penguin.PenguinColor.RED);
    p3 = new Player(0, Penguin.PenguinColor.BROWN);
    p4 = new Player(13, Penguin.PenguinColor.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorExceptionTooYoung() {
    p1 = new Player(-5, Penguin.PenguinColor.RED);
  }

  @Test
  public void copyConstructor() {
    Player p5 = new Player(p4);
    assertEquals(0, p4.getFish());
    assertEquals(0, p5.getFish());
    p4.addFish(2);
    assertEquals(2, p4.getFish());
    assertEquals(0, p5.getFish());
  }

  @Test
  public void getAge() {
    assertEquals(20, p1.getAge());
    assertNotEquals(43, p1.getAge());
    assertEquals(13, p2.getAge());
    assertNotEquals(54, p1.getAge());
    assertEquals(0, p3.getAge());
    assertNotEquals(1, p3.getAge());
  }

  @Test
  public void getFish() {
    assertEquals(0, p1.getFish());
    assertNotEquals(3, p1.getFish());
  }

  @Test
  public void getColor() {
    assertEquals(Penguin.PenguinColor.BROWN, p3.getColor());
    assertNotEquals(Penguin.PenguinColor.WHITE, p3.getColor());
  }

  @Test
  public void addFish() {
    assertEquals(0, p1.getFish());
    p1.addFish(4);
    assertNotEquals(0, p1.getFish());
    assertEquals(4, p1.getFish());
    p1.addFish(1);
    assertNotEquals(4, p1.getFish());
    assertEquals(5, p1.getFish());
  }

  public void equals() {
    assertNotEquals(p1, p2);
    assertEquals(p2, p4);
    p2.addFish(5);
    assertNotEquals(p2, p4);
  }
}