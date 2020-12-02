package game.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Class to represent a penguin.
 * TODO: Improve description
 */
public class Penguin {
  private final PenguinColor color;
  static Image PENGUIN_RED = null;
  static Image PENGUIN_WHITE = null;
  static Image PENGUIN_BROWN = null;
  static Image PENGUIN_BLACK = null;

  /**
   * Constructor for a Penguin with its assigned color.
   * If the static image representations of the penguins are not yet
   * @param pc Color of the penguin.
   */
  public Penguin(PenguinColor pc) {
    this.color = pc;

    BufferedImage image;

    if (PENGUIN_RED == null || PENGUIN_WHITE == null || PENGUIN_BLACK == null
            || PENGUIN_BROWN == null) {

      try {
        image = ImageIO.read(new File("Other/dep/resources/redpenguin.png"));
        PENGUIN_RED = image;
        image = ImageIO.read(new File("Other/dep/resources/whitepenguin.png"));
        PENGUIN_WHITE = image;
        image = ImageIO.read(new File("Other/dep/resources/brownpenguin.png"));
        PENGUIN_BROWN = image;
        image = ImageIO.read(new File("Other/dep/resources/blackpenguin.png"));
        PENGUIN_BLACK = image;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Copy constructor for Penguin objects. Copies the penguin color from the passed-in Penguin.
   * @param p Penguin to copy from.
   */
  public Penguin(Penguin p) {
    this.color = p.color;
  }

  /**
   * Returns the color of the penguin.
   * @return the penguin's color
   */
  public PenguinColor getColor() {
    return color;
  }

  public void render(BoardPosition bp, Graphics g) {
    int shift = bp.getRow() % 2;
    int shiftRight = (int) (shift * 2.0 / 3.0 * Tile.WIDTH);

    int xcoord = (int) (Tile.COLUMN_WIDTH * bp.getCol() + 1.0 / 3.0 * Tile.WIDTH + shiftRight)
            + Tile.R_OFFSET;
    int ycoord = (int) (bp.getRow() / 2.0 * Tile.HEIGHT + Tile.D_OFFSET) + Tile.HEIGHT / 2;

    switch (this.color) {
      case WHITE:
        g.drawImage(PENGUIN_WHITE, xcoord, ycoord, null);
        break;
      case BLACK:
        g.drawImage(PENGUIN_BLACK, xcoord, ycoord, null);
        break;
      case RED:
        g.drawImage(PENGUIN_RED, xcoord, ycoord, null);
        break;
      case BROWN:
        g.drawImage(PENGUIN_BROWN, xcoord, ycoord, null);
        break;
      default:
    }
  }

  /**
   * Enum for colors of a penguin.
   * The enums are associated with a "tie code", which breaks ties in the event that two players
   * are the same age. The lower the tie code, the earlier the player is meant to go. P1, age 21
   * and assigned black, will go before P2, age 21 and assigned brown.
   */
  public enum PenguinColor {
    BLACK(0),
    BROWN(1),
    RED(2),
    WHITE(3);

    int tieCode;

    /**
     * Constructor for PenguinColor items, which are associated with a tie-code, as explained in
     * the PenguinColor enum description.
     * @param code The tie code of the color.
     */
    PenguinColor(int code) {
      tieCode = code;
    }

    /**
     * Returns a color at random from the colors represented by this enum.
     * @return The color (one of black, brown, red, and white).
     */
    public static PenguinColor getRandomColor() {
      Random rng = new Random();
      int randnum = rng.nextInt(PenguinColor.values().length);
      return PenguinColor.values()[randnum];
    }
  }
}
