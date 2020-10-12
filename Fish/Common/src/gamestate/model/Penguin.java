package gamestate.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class to represent a penguin.
 */
public class Penguin {
  private final PenguinColor color;
  private BoardPosition position;
  static Image PENGUIN_RED = null;
  static Image PENGUIN_WHITE = null;
  static Image PENGUIN_BROWN = null;
  static Image PENGUIN_BLACK = null;

  /**
   * Basic constructor for a penguin with color and position.
   * @param pc Color of the penguin.
   * @param bp Position to initialize the penguin at.
   */
  public Penguin(PenguinColor pc, BoardPosition bp) {
    this.color = pc;
    this.position = bp;

    BufferedImage image;

    if(PENGUIN_RED == null || PENGUIN_WHITE == null || PENGUIN_BLACK == null
            || PENGUIN_BROWN == null) {
      // Fish/Common/
      File pathRed = new File("resources/redpenguin.png");
      File pathWhite = new File("resources/whitepenguin.png");
      File pathBrown = new File("resources/brownpenguin.png");
      File pathBlack = new File("resources/blackpenguin.png");

      try {
        image = ImageIO.read(pathRed);
        PENGUIN_RED = image;
        image = ImageIO.read(pathWhite);
        PENGUIN_WHITE = image;
        image = ImageIO.read(pathBrown);
        PENGUIN_BROWN = image;
        image = ImageIO.read(pathBlack);
        PENGUIN_BLACK = image;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
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
  /*
   private void drawPenguin(Graphics g) {
    int shift = p.getRow() % 2;
    int shiftRight = (int) (shift * 2.0/3.0 * WIDTH);

    int xcoord = (int) (COLUMN_WIDTH * p.getCol() + 1.0/3.0 * WIDTH + shiftRight) + R_OFFSET;
    int ycoord = (int) (p.getRow() / 2.0 * HEIGHT + D_OFFSET) + HEIGHT / 2;

    switch (this.occupant) {
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
   */


  /*
  @Override
  public void placePenguin(Penguin p) {
    this.penguins.add(p);
    int row = p.getPosition().getRow();
    int col = p.getPosition().getCol();
    Penguin.PenguinColor color = p.getColor();
    switch (color) {
      case RED:
        boardSpaces[row][col].setStatus(Tile.TileStatus.RED);
        break;
      case BLACK:
        boardSpaces[row][col].setStatus(Tile.TileStatus.BLACK);
        break;
      case BROWN:
        boardSpaces[row][col].setStatus(Tile.TileStatus.BROWN);
        break;
      case WHITE:
        boardSpaces[row][col].setStatus(Tile.TileStatus.WHITE);
        break;
      default:
    }
  }

   */
}
