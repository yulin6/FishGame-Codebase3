package gamestate.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import gamestate.controller.FishController;

import static org.junit.Assert.*;

/**
 * Class to test interface methods of Board.
 */
public class BoardTest {
  Board random;
  Board uniform;
  ArrayList<BoardPosition> holes;


  @Before
  public void setUp() {
    holes = new ArrayList<>();
    holes.add(new BoardPosition(0, 0));
    holes.add(new BoardPosition(1, 1));
    random = new Board(3, 4, holes, 3);
    uniform = new Board(2, 2, 4);
  }

  @Test
  public void removeTile() {
    assertTrue(random.tiles[0][0].isHole());
    random.removeTile(new BoardPosition(0, 0));
    assertTrue(random.tiles[0][0].isHole());
    assertFalse(random.tiles[0][1].isHole());
    random.removeTile(new BoardPosition(0, 1));
    assertTrue(random.tiles[0][1].isHole());

    assertFalse(uniform.tiles[1][1].isHole());
    uniform.removeTile(new BoardPosition(1,1));
    assertTrue(uniform.tiles[1][1].isHole());
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeTileOOBException() {
    random.removeTile(new BoardPosition(-1, -1));
  }

  @Test
  public void setController() {
    FishController fc = new FishController(2, 2, 4);
    uniform.setController(fc);
    assertEquals(fc, uniform.getController());
  }

  @Test
  public void getRows() {
    assertEquals(3, random.getRows());
    assertEquals(2, uniform.getRows());
    assertNotEquals(random.getRows(), uniform.getRows());
  }

  @Test
  public void getCols() {
    assertEquals(4, random.getCols());
    assertEquals(2, uniform.getCols());
    assertNotEquals(random.getCols(), uniform.getCols());
  }

  @Test
  public void getValidMoves() {

  }
}