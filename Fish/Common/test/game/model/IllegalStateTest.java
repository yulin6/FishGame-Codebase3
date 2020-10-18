package game.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IllegalStateTest {

  @Before
  public void setUp() {
  }

  @Test (expected = IllegalStateException.class)
  public void placeAvatar() {
  }

  @Test (expected = IllegalStateException.class)
  public void moveAvatar() {
  }

  @Test (expected = IllegalStateException.class)
  public void movesPossible() {
  }

  @Test (expected = IllegalStateException.class)
  public void render() {
  }

  @Test (expected = IllegalStateException.class)
  public void getCurrentPlayer() {
  }

  @Test (expected = IllegalStateException.class)
  public void removePlayer() {
  }

  @Test (expected = IllegalStateException.class)
  public void setNextPlayer() {
  }

  @Test (expected = IllegalStateException.class)
  public void getPenguinAtPosn() {
  }
}