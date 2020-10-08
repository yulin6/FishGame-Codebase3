package gamestate.model;

import java.awt.*;
import java.util.ArrayList;

import gamestate.controller.FishController;
import gamestate.view.BoardPanel;

/**
 * Class to represent a board of Fish.
 */
public class Board implements IBoard {
  final int MAX_FISH = 5;
  final int MIN_FISH = 1;

  FishController controller;

  int rows;
  int cols;
  Tile [][] tiles;

  /**
   * Creates a board in which there are holes in given spaces, tiles have a random number of fish,
   * and there is a minimum number of 1-fish tiles guaranteed
   * @param rows number of rows of tiles on the board
   * @param columns the number of columns of tiles on the board
   * @param holes the spaces where tiles will be holes
   * @param minTiles the minimum number of 1-fish tiles
   */
  public Board (int rows, int columns, ArrayList<BoardPosition> holes, int minTiles) {
    this.rows = rows;
    this.cols = columns;
    this.tiles = new Tile[this.rows][this.cols];
    int numOneFish = initTilesRandom();

    numOneFish -= removeHoles(holes);

    setOneFishTiles(numOneFish, minTiles);
  }

  /**
   * Creates a full uniform board with each tile having fishNum fish
   * @param rows the number of rows of tiles on the board
   * @param columns the number of columns of tiles on the board
   * @param fishNum the number of fish on each tile
   */
  public Board (int rows, int columns, int fishNum) {
    this.rows = rows;
    this.cols = columns;
    this.tiles = new Tile[this.rows][this.cols];

    initTiles(fishNum);
  }

  /**
   * Sets tiles on the board randomly to 1-fish tiles if the amount is insufficient.
   * @param numOneFish the amount of tiles that are currently 1-fish tiles.
   * @param minTiles the minimum amount of tiles that need to be 1-fish tiles.
   */
  private void setOneFishTiles(int numOneFish, int minTiles) {
    Tile.TileBuilder tileBuilder = new Tile.TileBuilder();

    while (numOneFish <= minTiles) {
      int randRow = (int)(Math.random() * this.rows);
      int randCol = (int)(Math.random() * this.cols);

      Tile candidateTile = tiles[randRow][randCol];
      if(!(candidateTile.isHole()) && (candidateTile.getNumFish() != 1)) {
        tiles[randRow][randCol] = tileBuilder.setFish(MIN_FISH)
                .setPosition(new BoardPosition(randRow, randCol)).build();
        numOneFish++;
      }
    }
  }

  /**
   * Removes the holes in the board
   * @param holes a list of positions on the board where tiles will become holes
   * @return the number of 1-fish tiles that are holes
   */
  private int removeHoles(ArrayList<BoardPosition> holes) {
    //Take holes out of board and ensure we still have min number of 1-fish tiles
    int numOneFishHoles = 0;
    for(BoardPosition hole: holes) {
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
        tiles[r][c] = tileBuilder.setFish(fish).setPosition(new BoardPosition(r, c)).build();
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
        tiles[r][c] = tileBuilder.setFish(numFish).setPosition(new BoardPosition(r,c)).build();
      }
    }
  }

  @Override
  public void removeTile(BoardPosition p) {
    int row = p.getRow();
    int col = p.getCol();

    if (row >= rows || col >= cols || row < 0 || col < 0) {
      throw new IllegalArgumentException("Cannot remove tile out of bounds.");
    }

    tiles[row][col].setHole();
  }

  @Override
  public void renderBoard(BoardPanel bp, Graphics g) {
    for (int r = 0; r < this.rows; r++) {
      for (int c = 0; c < this.cols; c++) {
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
  public FishController getController() {
    return controller;
  }

  @Override
  public int getRows() {
    return rows;
  }

  @Override
  public int getCols() {
    return cols;
  }

  @Override
  public ArrayList<Tile> getValidMoves(BoardPosition p) {
    ArrayList<Tile> validTiles = new ArrayList<>();
    addUpPath(p, validTiles);
    addDownPath(p, validTiles);
    addTopRightPath(p, validTiles);
    addBotRightPath(p, validTiles);
    addTopLeftPath(p, validTiles);
    addBotLeftPath(p, validTiles);

    return validTiles;
  }

  /**
   * Adds all the tiles that can be moved to via an upwards movement to the list of valid tiles.
   * @param p The position from which the move will begin.
   * @param validTiles The list to add valid tiles for movement to.
   */
  private void addUpPath(BoardPosition p, ArrayList<Tile> validTiles) {
    int col = p.getCol();
    for (int i = p.getRow(); i >= 0; i-=2) {
      if (tiles[i][col].isHole()) {
        return;
      } else {
        validTiles.add(tiles[i][col]);
      }
    }
  }

  /**
   * Adds all the tiles that can be moved to via a downwards movement to the list of valid tiles.
   * @param p The position from which the move will begin.
   * @param validTiles The list to add valid tiles for movement to.
   */
  private void addDownPath(BoardPosition p, ArrayList<Tile> validTiles) {
    int col = p.getCol();
    for (int i = p.getRow(); i < rows; i+=2) {
      if (tiles[i][col].isHole()) {
        return;
      } else {
        validTiles.add(tiles[i][col]);
      }
    }
  }

  /**
   * Adds all the tiles that can be moved to via movement upwards and right to the list of valid
   * tiles.
   * @param p The position from which the move will begin.
   * @param validTiles The list to add valid tiles for movement to.
   */
  private void addTopRightPath(BoardPosition p, ArrayList<Tile> validTiles) {
    int nextRow = p.getRow() - 1;
    if (nextRow < 0) {
      return;
    }
    // if the current row # is odd, moving into a different column
    int nextCol;
    if (p.getRow() % 2 == 1) {
      nextCol = p.getCol() + 1;
      if (nextCol >= cols) {
        return;
      }
    } else {
      nextCol = p.getCol();
    }

    if (!tiles[nextRow][nextCol].isHole()) {
      validTiles.add(tiles[nextRow][nextCol]);
      addTopRightPath(new BoardPosition(nextRow, nextCol), validTiles);
    }
  }

  /**
   * Adds all the tiles that can be moved to via movement downwards and right to the list of valid
   * tiles.
   * @param p The position from which the move will begin.
   * @param validTiles The list to add valid tiles for movement to.
   */
  private void addBotRightPath(BoardPosition p, ArrayList<Tile> validTiles) {
    int nextRow = p.getRow() + 1;
    if (nextRow >= rows) {
      return;
    }
    // if the current row # is odd, moving into a different column
    int nextCol;
    if (p.getRow() % 2 == 1) {
      nextCol = p.getCol() + 1;
      if (nextCol >= cols) {
        return;
      }
    } else {
      nextCol = p.getCol();
    }

    if (!tiles[nextRow][nextCol].isHole()) {
      validTiles.add(tiles[nextRow][nextCol]);
      addBotRightPath(new BoardPosition(nextRow, nextCol), validTiles);
    }
  }

  /**
   * Adds all the tiles that can be moved to via movement upwards and left to the list of valid
   * tiles.
   * @param p The position from which the move will begin.
   * @param validTiles The list to add valid tiles for movement to.
   */
  private void addTopLeftPath(BoardPosition p, ArrayList<Tile> validTiles) {
    int nextRow = p.getRow() - 1;
    if (nextRow < 0) {
      return;
    }
    // if the current row # is even, moving into a different column
    int nextCol;
    if (p.getRow() % 2 == 0) {
      nextCol = p.getCol() - 1;
      if (nextCol < 0) {
        return;
      }
    } else {
      nextCol = p.getCol();
    }

    if (!tiles[nextRow][nextCol].isHole()) {
      validTiles.add(tiles[nextRow][nextCol]);
      addTopLeftPath(new BoardPosition(nextRow, nextCol), validTiles);
    }
  }

  /**
   * Adds all the tiles that can be moved to via movement downwards and left to the list of valid
   * tiles.
   * @param p The position from which the move will begin.
   * @param validTiles The list to add valid tiles for movement to.
   */
  private void addBotLeftPath(BoardPosition p, ArrayList<Tile> validTiles) {
    int nextRow = p.getRow() + 1;
    if (nextRow >= rows) {
      return;
    }
    // if the current row # is even, moving into a different column
    int nextCol;
    if (p.getRow() % 2 == 0) {
      nextCol = p.getCol() - 1;
      if (nextCol < 0) {
        return;
      }
    } else {
      nextCol = p.getCol();
    }

    if (!tiles[nextRow][nextCol].isHole()) {
      validTiles.add(tiles[nextRow][nextCol]);
      addBotLeftPath(new BoardPosition(nextRow, nextCol), validTiles);
    }
  }











}
