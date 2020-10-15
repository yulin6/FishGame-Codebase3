package game.view;

import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Class to represent the visual panel for a board of Fish.
 * TODO: rework into a GamePanel
 */
public class FishPanel extends JPanel {
  /**
   * Creates a new BoardPanel, which determines size based on the board size.
   */
  public FishPanel() {
    JPanel panel = new JPanel();
  }

  @Override
  protected void paintComponent(Graphics g) {
    FishFrame frame = (FishFrame) SwingUtilities.windowForComponent(this);
    frame.getController().getState().render(g);
  }
}
