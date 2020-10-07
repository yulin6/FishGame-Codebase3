package gamestate.view;

import java.awt.Dimension;

import javax.swing.JFrame;

import gamestate.controller.FishController;

/**
 * Class to wrap JFrame to represent the board's frame.
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
    //TODO: Set resizable?
    this.setSize(new Dimension(300, 300)); // TODO fix sizing
  }

  /**
   * Adds a BoardPanel to the BoardFrame for display
   * @param bp BoardPanel to add that belongs to this BoardFrame
   */
  public void addPanel(BoardPanel bp) {
    this.panel = bp;
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
    this.setVisible(true);
  }

  public void setController(FishController controller) {
    this.controller = controller;
  }

}
