package game.model;

/**
 * Class to represent pixel positions, for use in GUI/rendering.
 * Has x and y values, representing the exact coordinate of a given pixel
 * (for use in a coordinate system typical for computer graphics).
 */
public class PixelPosition {
  private final int x;
  private final int y;

  /**
   * Constructs a new PixelPosition.
   * @param xcoord The x-coordinate of the position
   * @param ycoord The y-coordinate of the position
   */
  public PixelPosition(int xcoord, int ycoord) {
    this.x = xcoord;
    this.y = ycoord;
  }

  /**
   * Gets the x-value of this PixelPosition.
   * @return the x-value
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the y-value of this PixelPosition.
   * @return the y-value
   */
  public int getY() {
    return y;
  }

}
