package game.model;

import java.awt.*;

/**
 * Class to represent a hole in a game of Fish.
 * A Hole signifies the lack of anything in a given space in a Board,
 * having no graphical representation nor any fish, though ideally
 * it should be checked via isHole() before relying on fish quantity.
 */
public class Hole implements BoardSpace {

  /**
   * Constructor for Hole objects.
   */
  public Hole() {

  }

  @Override
  public int getNumFish() {
    return 0;
  }

  @Override
  public boolean isHole() {
    return true;
  }

  @Override
  public void render(BoardPosition p, Graphics g) {
    // has no graphical representation
  }
}
