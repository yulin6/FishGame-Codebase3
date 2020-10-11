package gamestate.model;

import java.awt.*;

import gamestate.view.BoardPanel;

public class Hole implements BoardSpace {
  @Override
  public int getNumFish() {
    return 0;
  }

  @Override
  public boolean isHole() {
    return true;
  }

  @Override
  public void render(BoardPosition p, Graphics g) {
    return;
  }
}
