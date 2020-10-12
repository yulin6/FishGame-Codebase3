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
    BoardPosition zerozero = new BoardPosition(0, 0);
    BoardPosition zeroone = new BoardPosition(0, 1);
    BoardPosition oneone = new BoardPosition(1, 1);

    assertTrue(random.getSpace(zerozero).isHole());
    random.removeTile(new BoardPosition(0, 0));
    assertTrue(random.getSpace(zerozero).isHole());
    assertFalse(random.getSpace(zeroone).isHole());
    random.removeTile(new BoardPosition(0, 1));
    assertTrue(random.getSpace(zeroone).isHole());

    assertTrue(random.getSpace(oneone).isHole());
    uniform.removeTile(new BoardPosition(1,1));
    assertTrue(random.getSpace(oneone).isHole());
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
    ArrayList<BoardPosition> positions = uniform.getValidMoves(new BoardPosition(1, 0),
            new ArrayList<>());
    assertEquals(2, positions.size());
    assertTrue(positions.contains(new BoardPosition(0, 0)));
    assertTrue(positions.contains(new BoardPosition(0, 1)));

    ArrayList<BoardPosition> positions2 = random.getValidMoves(new BoardPosition(1, 0),
            new ArrayList<>());
    assertEquals(3, positions2.size());
    assertTrue(positions2.contains(new BoardPosition(2, 0)));
    assertTrue(positions2.contains(new BoardPosition(0, 1)));
    assertTrue(positions2.contains(new BoardPosition(2, 1)));

    ArrayList<BoardPosition> positions3 = random.getValidMoves(new BoardPosition(1, 0),
            positions2);
    assertEquals(0, positions3.size());
  }
}