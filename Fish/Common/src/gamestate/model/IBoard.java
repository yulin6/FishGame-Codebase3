package gamestate.model;

import java.awt.*;
import java.util.ArrayList;

import gamestate.controller.FishController;
import gamestate.view.BoardPanel;

public interface IBoard {
  /**
   *
   * @param p
   * @return
   */
  ArrayList<Tile> getValidMoves(Position p);

  /**
   *
   * @param p
   */
  void removeTile(Position p);

  /**
   *
   */
  void renderBoard(BoardPanel bp, Graphics g);

  /**
   *
   * @param controller
   */
  void setController(FishController controller);

}