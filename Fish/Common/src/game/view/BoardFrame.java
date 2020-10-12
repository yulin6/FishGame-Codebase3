package game.view;

import java.awt.Dimension;

import javax.swing.JFrame;

import game.controller.FishController;
import game.model.Tile;

/**
 * Class to wrap JFrame to represent the board's frame.
 * TODO: rework into a GameFrame
 */
public class BoardFrame extends JFrame {
  private FishController controller;
  private BoardPanel panel;

  /**
   * Constructs a new BoardFrame.
   */
  public BoardFrame() {
    super();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

  /**
   * Adds a BoardPanel to the BoardFrame for display.
   * @param bp BoardPanel to add that belongs to this BoardFrame
   */
  public void addPanel(BoardPanel bp) {
    this.panel = bp;
    this.add(bp);
  }

  /**
   * Gets the BoardPanel associated with this BoardFrame.
   * @return the BoardPanel belonging to this BoardFrame
   */
  public BoardPanel getPanel() {
    return this.panel;
  }

  /**
   * Sets this BoardFrame as visible.
   */
  public void display() {
    int rows = controller.getBoard().getRows();
    int cols = controller.getBoard().getCols();
    int windowWidth = (int) (cols * Tile.COLUMN_WIDTH + Tile.R_OFFSET + Tile.WIDTH);
    int windowHeight = rows * Tile.HEIGHT + Tile.D_OFFSET;
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

}
