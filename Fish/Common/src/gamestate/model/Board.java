package gamestate.model;

import java.awt.*;
import java.util.ArrayList;

import gamestate.controller.FishController;
import gamestate.view.BoardPanel;

/**
 *
 */
public class Board implements IBoard {
  final int MAX_FISH = 5;
  final int MIN_FISH = 1;
  //final int TILE_SIZE = 50;

  FishController controller;

  int rows;
  int cols;
  Tile [][] tiles;

  /**
   * Creates a board in which there are hoels in given spaces, tiles have a random number of fish,
   * and there is a minimum number of 1-fish tiles guaranteed
   * @param rows number of rows of tiles on the board
   * @param columns the number of columns of tiles on the board
   * @param holes the spaces where tiles will be holes
   * @param minTiles the minimum number of 1-fish tiles
   */
  public Board (int rows, int columns, ArrayList<Position> holes, int minTiles) {
    // TODO: create a board as specified
    this.rows = rows;
    this.cols = columns;

    int numOneFish = initTilesRandom();

    numOneFish -= removeHoles(holes);

    setOneFishTiles(numOneFish, minTiles);
  }

  /**
   * Creates a full uniform board with each tile having fishNum fish
   * @param fishNum the number of fish on each tile
   * @param rows the number of rows of tiles on the board
   * @param columns the number of columns of tiles on the board
   */
  public Board (int fishNum, int rows, int columns) {
    // TODO: create a board as specified
    this.rows = rows;
    this.cols = columns;

    initTiles(fishNum);
  }

  /**
   *
   * @param numOneFish
   * @param minTiles
   */
  private void setOneFishTiles(int numOneFish, int minTiles) {
    Tile.TileBuilder tileBuilder = new Tile.TileBuilder();

    while(numOneFish <= minTiles) {
      int randRow = (int)(Math.random() * this.rows);
      int randCol = (int)(Math.random() * this.cols);

      Tile candidateTile = tiles[randRow][randCol];
      if(!(candidateTile.isHole()) && (candidateTile.getNumFish() != 1)) {
        tiles[randRow][randCol] = tileBuilder.setFish(MIN_FISH)
                .setPosition(new Position(randRow, randCol)).build();
        numOneFish++;
      }
    }
  }

  /**
   * Removes the holes in the board
   * @param holes a list of positions on the board where tiles will become holes
   * @return the number of 1-fish tiles that are holes
   */
  private int removeHoles(ArrayList<Position> holes) {
    //Take holes out of board and ensure we still have min number of 1-fish tiles
    int numOneFishHoles = 0;
    for(Position hole: holes) {
      int row = hole.getRow();
      int col = hole.getCol();

      removeTile(hole);
      if(tiles[row][col].getNumFish() == 1) {
        numOneFishHoles++;
      }
    }

    return numOneFishHoles;
  }
  /**
   * Builds a full board with no holes. Used in initial step of building board with holes
   * @return the number of 1-fish tiles
   */
  private int initTilesRandom() {
    int range = MAX_FISH - MIN_FISH + 1;
    int numOneFish = 0;
    Tile.TileBuilder tileBuilder = new Tile.TileBuilder();
    //Build whole board (no holes)
    for(int r = 0; r < this.rows; r++) {
      for(int c = 0; c < this.cols; c++) {
        int fish = (int)(Math.random() * range) + MIN_FISH;
        if(fish == 1) {
          numOneFish++;
        }
        tiles[r][c] = tileBuilder.setFish(fish).setPosition(new Position(r, c)).build();
      }
    }

    return numOneFish;
  }

  /**
   * Builds a uniform board with the same number of fish
   * @param numFish nubmer of fish on each tile
   */
  private void initTiles(int numFish) {
    Tile.TileBuilder tileBuilder = new Tile.TileBuilder();
    //Build whole board with same number of fish
    for(int r = 0; r < this.rows; r++) {
      for(int c = 0; c < this.cols; c++) {
        tiles[r][c] = tileBuilder.setFish(numFish).setPosition(new Position(r,c)).build();
      }
    }
  }



  @Override
  public void removeTile(Position p) {
    int row = p.getRow();
    int col = p.getCol();

    tiles[row][col].setHole();
  }


  @Override
  public void renderBoard(BoardPanel bp, Graphics g) {
    for(int r = 0; r < this.rows; r++) {
      for(int c = 0; c < this.cols; c++) {
        tiles[r][c].render(bp, g);
      }
    }
    // return bp;
  }

  @Override
  public void setController(FishController controller) {
    this.controller = controller;
  }

  @Override
  public ArrayList<Tile> getValidMoves(Position p) {
    // TODO: algorithm to validate moves and add to list
    return null;
  }

}
