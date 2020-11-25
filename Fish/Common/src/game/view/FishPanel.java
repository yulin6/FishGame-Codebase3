package game.view;

import java.awt.Graphics;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Class to represent the visual panel for a board of Fish.
 */
public class FishPanel extends JPanel {

  /**
   * Creates a new BoardPanel, which determines size based on the board size.
   */
  public FishPanel() { }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    FishFrame frame = (FishFrame) SwingUtilities.windowForComponent(this);
    frame.getController().getState().render(g);
  }

}
