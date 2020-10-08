package gamestate.view;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Class to represent the visual panel for a board of Fish.
 */
public class BoardPanel extends JPanel {
  /**
   * Creates a new BoardPanel, which determines size based on the board size.
   */
  public BoardPanel() {
    JPanel panel = new JPanel();
  }

  @Override
  protected void paintComponent(Graphics g) {
    BoardFrame frame = (BoardFrame) SwingUtilities.windowForComponent(this);
    frame.getController().getBoard().renderBoard(this, g);
  }
}
