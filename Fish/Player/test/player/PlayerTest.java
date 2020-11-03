package player;

import org.junit.Before;
import org.junit.Test;

import game.model.GameTree;
import game.model.Penguin;

import static org.junit.Assert.*;

/**
 * Class to test the methods of an implemented player component, which operates using the
 * previously made strategy component (../src/Strategy.java).
 */
public class PlayerTest {
  Player p1;
  Player p2;
  GameTree gt;

  @Before
  public void setUp() {
    p1 = new Player(15);
    p2 = new Player(83);


  }

  @Test
  public void startPlaying() {
    p1.startPlaying(Penguin.PenguinColor.BLACK);
    assertEquals(Penguin.PenguinColor.BLACK, p1.getColor());
    p2.startPlaying(Penguin.PenguinColor.BROWN);
    assertEquals(Penguin.PenguinColor.BROWN, p2.getColor());
  }

  @Test (expected = IllegalArgumentException.class)
  public void startPlayingIAE() {
    p1.startPlaying(Penguin.PenguinColor.BLACK);
    p1.startPlaying(Penguin.PenguinColor.RED);
  }

  @Test
  public void placePenguin() {

  }

  @Test
  public void takeTurn() {

  }

  @Test
  public void finishPlaying() {
    p1.startPlaying(Penguin.PenguinColor.WHITE);
    assertEquals(Penguin.PenguinColor.WHITE, p1.getColor());
    p1.finishPlaying();
  }

  @Test (expected = IllegalArgumentException.class)
  public void finishPlayingIAE() {
    p1.finishPlaying();
  }

  @Test
  public void getAge() {
    assertEquals(15, p1.getAge());
    assertEquals(83, p2.getAge());
    assertNotEquals(p1.getAge(), p2.getAge());
  }

  @Test
  public void getColor() {
    p1.startPlaying(Penguin.PenguinColor.RED);
    p2.startPlaying(Penguin.PenguinColor.BROWN);
    assertNotEquals(p1.getColor(), p2.getColor());
    assertEquals(Penguin.PenguinColor.RED, p1.getColor());
    assertEquals(Penguin.PenguinColor.BROWN, p2.getColor());
  }

  @Test (expected = IllegalArgumentException.class)
  public void getColorIAE() {
    p2.startPlaying(Penguin.PenguinColor.WHITE);
    assertEquals(Penguin.PenguinColor.WHITE, p2.getColor());
    p1.getColor();
  }
}