package game.view;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import game.controller.FishController;
import game.model.Tile;

/**
 * Class to wrap JFrame to represent the board's frame.
 */
public class FishFrame extends JFrame implements KeyListener {
  private FishController controller;
  private FishPanel panel;
  private final int windowWidth;
  private final int windowHeight;
  private static final double TILE_HEIGHTS_PER_ROW = 0.5;

  /**
   * Constructs a new BoardFrame. The two integer values passed in are used to size the window based
   * on the dimensions of the game board.
   *
   * @param rows The number of rows on the game board
   * @param cols The number of columns on the game board
   */
  public FishFrame(int rows, int cols) {
    super();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    windowWidth = (int) (cols * Tile.COLUMN_WIDTH + Tile.R_OFFSET + Tile.WIDTH);
    windowHeight =
            (int) (TILE_HEIGHTS_PER_ROW * rows * Tile.HEIGHT + Tile.HEIGHT + Tile.D_OFFSET);
  }

  /**
   * Adds a BoardPanel to the BoardFrame for display.
   * @param bp BoardPanel to add that belongs to this BoardFrame
   */
  public void addPanel(FishPanel bp) {
    this.panel = bp;
    this.add(this.panel);
    this.panel.addKeyListener(this);
    this.panel.requestFocusInWindow();
  }

  /**
   * Gets the BoardPanel associated with this BoardFrame.
   * @return the BoardPanel belonging to this BoardFrame
   */
  public FishPanel getPanel() {
    return this.panel;
  }

  /**
   * Sets this BoardFrame as visible.
   */
  public void display() {
    this.setSize(new Dimension(windowWidth, windowHeight));
    this.setVisible(true);
  }

  /**
   * Sets the controller of this BoardFrame.
   * @param controller The controller to assign.
   */
  public void setController(FishController controller) {
    this.controller = controller;
  }

  /**
   * Gets the controller of this BoardFrame.
   * @return The controller associated with this object.
   */
  public FishController getController() {
    return controller;
  }

  public void keyPressed(KeyEvent e) { System.out.println("keyPressed"); }

  public void keyReleased(KeyEvent e) { System.out.println("keyReleased"); }

  public void keyTyped(KeyEvent e) { System.out.println("keyTyped"); }
}
