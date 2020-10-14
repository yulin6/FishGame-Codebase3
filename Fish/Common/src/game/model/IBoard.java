package game.model;

import java.awt.*;
import java.util.ArrayList;

import game.controller.FishController;

public interface IBoard {
  /**
   * Gets the list of valid Tiles that can be moved to from a given board position.
   * Valid positions are positions that can be reached via a straight-line movement
   * from one hexagon to another.
   * Holes in the board prevent movement through them; functionality is also
   * available for specifying additional unmovable tiles, but an empty list
   * can be given.
   * @param p The position on the board to begin a move from.
   * @param invalidPosns the list of any additional invalid positions on the board
   * @return An ArrayList of valid Tile objects that can be moved to.
   */
  ArrayList<BoardPosition> getValidMoves(BoardPosition p, ArrayList<BoardPosition> invalidPosns);

  /**
   * Removes a tile from the board. If already a hole, does nothing.
   * @param p The tile to be removed from the board.
   */
  void removeTile(BoardPosition p);

  /**
   * Renders the current state of the board.
   */
  void render(Graphics g);

  /**
   * Assigns a controller for the board.
   * @param controller The controller to assign to the board.
   */
  void setController(FishController controller);

  /**
   * Gets the FishController associated with this board.
   * @return the controller of the board.
   */
  FishController getController();

  /**
   * Returns the amount of rows in the board.
   * @return integer number of rows
   */
  int getRows();

  /**
   * Returns the amount of columns in the board.
   * @return integer number of columns
   */
  int getCols();

  /**
   * Returns the BoardSpace corresponding to the passed-in BoardPosition.
   * @param p The BoardPosition to get the BoardSpace at.
   * @return BoardSpace (Tile/Hole) at the given position.
   */
  BoardSpace getSpace(BoardPosition p);

  /**
   * Checks whether the given BoardPosition is within the bounds of the board.
   * @param bp BoardPosition to check validity of.
   * @return True if the BoardPosition falls within the bounds of the board, else false.
   */
  boolean isValidPosn(BoardPosition bp);


  /*
   * Places a penguin onto the board.
   * @param p The penguin to add to the board
   */
  /*
  void placePenguin(Penguin p);

   */

}