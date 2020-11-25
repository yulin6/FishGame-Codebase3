package game.view;

import java.awt.Graphics;
import java.awt.event.KeyListener;

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
  public FishPanel() { }

  @Override
  protected void paintComponent(Graphics g) {
    FishFrame frame = (FishFrame) SwingUtilities.windowForComponent(this);
    frame.getController().getState().render(g);

  }

  @Override
  public void addKeyListener(KeyListener kl) {
    System.out.println("Adding key listener");
    super.addKeyListener(kl);
  }
}
