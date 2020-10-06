package gamestate.view;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 *
 */
public class BoardPanel extends JPanel {

  // TODO: delete before submission - note - tiles have fixed sizes
  /**
   * Creates a new BoardPanel, which determines size based on the board size.
   */
  public BoardPanel() {
    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension(300, 300)); // TODO fix sizing
  }

  @Override
  protected void paintComponent(Graphics g) {
    // TODO
  }

}
