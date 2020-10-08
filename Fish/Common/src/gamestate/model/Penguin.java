package gamestate.model;

/**
 * Class to represent a penguin.
 */
public class Penguin {
  private final PenguinColor color;
  private BoardPosition position;

  /**
   * Basic constructor for a penguin with color and position.
   * @param pc Color of the penguin.
   * @param bp Position to initialize the penguin at.
   */
  public Penguin(PenguinColor pc, BoardPosition bp) {
    this.color = pc;
    this.position = bp;
  }

  /**
   * Gets the current position of the penguin.
   * @return the current position
   */
  public BoardPosition getPosition() {
    return position;
  }

  /**
   * Sets the current position of the penguin.
   * @param position the new position
   */
  public void setPosition(BoardPosition position) {
    this.position = position;
  }

  /**
   * Returns the color of the penguin.
   * @return the penguin's color
   */
  public PenguinColor getColor() {
    return color;
  }

  /**
   * Enum for colors of a penguin.
   */
  public enum PenguinColor {
    RED,
    WHITE,
    BROWN,
    BLACK
  }
}
