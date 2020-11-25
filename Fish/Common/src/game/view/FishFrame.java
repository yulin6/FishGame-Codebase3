package game.view;

import java.awt.*;

import javax.swing.*;

import game.controller.FishController;
import game.model.Tile;

/**
 * Class to wrap JFrame to represent the board's frame.
 */
public class FishFrame extends JFrame  {
  private FishController controller;
  private FishPanel gamePanel;
  private JPanel scorePanel;
  private final int windowWidth;
  private final int windowHeight;
  private static final double TILE_HEIGHTS_PER_ROW = 0.5;

  private final int scorePanelWidth = 200;

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
    windowWidth = (int) (cols * Tile.COLUMN_WIDTH + Tile.R_OFFSET + Tile.WIDTH + scorePanelWidth);
    windowHeight =
            (int) (TILE_HEIGHTS_PER_ROW * rows * Tile.HEIGHT + Tile.HEIGHT + Tile.D_OFFSET);

    scorePanel = new JPanel();
    this.add(scorePanel, BorderLayout.EAST);
  }

  /**
   * Adds a BoardPanel to the BoardFrame for display.
   * @param bp BoardPanel to add that belongs to this BoardFrame
   */
  public void addPanel(FishPanel bp) {
    this.gamePanel = bp;
    this.add(this.gamePanel);
    this.gamePanel.requestFocusInWindow();
  }


  /**
   * the method take in all the scores of players in a String format and display it in the GUI as a JLabel.
   * @param playerScore the scores of players in a String format.
   */
  public void updateScorePanel(String playerScore){
    scorePanel.removeAll();
    JLabel instruction = new JLabel(playerScore);
    scorePanel.add(instruction);
  }

  /**
   * Gets the BoardPanel associated with this BoardFrame.
   * @return the BoardPanel belonging to this BoardFrame
   */
  public FishPanel getGamePanel() {
    return this.gamePanel;
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


}
