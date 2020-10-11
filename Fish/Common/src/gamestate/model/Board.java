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
  BoardSpace [][] boardSpaces;

  /**
   * Creates a board in which there are holes in given spaces, tiles have a random number of fish,
   * and there is a minimum number of 1-fish tiles guaranteed
   * @param rows number of rows of tiles on the board
   * @param columns the number of columns of tiles on the board
   * @param holes the spaces where tiles will be holes
   * @param minTiles the minimum number of 1-fish tiles
   */
  public Board (int rows, int columns, ArrayList<BoardPosition> holes, int minTiles) {
    //checkArguments(rows, columns, holes, minTiles);
    this.rows = rows;
    this.cols = columns;
    this.boardSpaces = new BoardSpace[this.rows][this.cols];
    int numOneFish = initTilesRandom();

    numOneFish -= removeHoles(holes);

    setOneFishTiles(numOneFish, minTiles);
  }

  /*
  private void checkArguments(int rows, int columns,
                              ArrayList<BoardPosition> holes, int minTiles) {

  }
  */

  /**
   * Creates a full uniform board with each tile having fishNum fish
   * @param rows the number of rows of tiles on the board
   * @param columns the number of columns of tiles on the board
   * @param fishNum the number of fish on each tile
   */
  public Board (int rows, int columns, int fishNum) {
    //checkArgumentsUniform();
    this.rows = rows;
    this.cols = columns;
    this.boardSpaces = new BoardSpace[this.rows][this.cols];

    initTiles(fishNum);
  }

  /**
   * Sets tiles on the board randomly to 1-fish tiles if the amount is insufficient.
   * @param numOneFish the amount of tiles that are currently 1-fish tiles.
   * @param minTiles the minimum amount of tiles that need to be 1-fish tiles.
   */
  private void setOneFishTiles(int numOneFish, int minTiles) {
    while (numOneFish <= minTiles) {
      int randRow = (int)(Math.random() * this.rows);
      int randCol = (int)(Math.random() * this.cols);

      BoardSpace candidateTile = boardSpaces[randRow][randCol];
      if(!(candidateTile.isHole()) && (candidateTile.getNumFish() != 1)) {
        boardSpaces[randRow][randCol] = new Tile(MIN_FISH);
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
      if(boardSpaces[row][col].getNumFish() == 1) {
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
    //Build whole board (no holes)
    for(int r = 0; r < this.rows; r++) {
      for(int c = 0; c < this.cols; c++) {
        int fish = (int)(Math.random() * range) + MIN_FISH;
        if(fish == 1) {
          numOneFish++;
        }
        boardSpaces[r][c] = new Tile(fish);
      }
    }

    return numOneFish;
  }

  /**
   * Builds a uniform board with the same number of fish
   * @param numFish nubmer of fish on each tile
   */
  private void initTiles(int numFish) {
    //Build whole board with same number of fish
    for(int r = 0; r < this.rows; r++) {
      for(int c = 0; c < this.cols; c++) {
        boardSpaces[r][c] = new Tile(numFish);
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

    boardSpaces[row][col] = new Hole();
  }

  @Override
  public void renderBoard(Graphics g) {
    for (int r = 0; r < this.rows; r++) {
      for (int c = 0; c < this.cols; c++) {
        if(boardSpaces[r][c].isHole()) {
          continue;
        }
        BoardPosition p = new BoardPosition(r, c);
        boardSpaces[r][c].render(p, g);
      }
    }
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

  /*
  @Override
  public void placePenguin(Penguin p) {
    this.penguins.add(p);
    int row = p.getPosition().getRow();
    int col = p.getPosition().getCol();
    Penguin.PenguinColor color = p.getColor();
    switch (color) {
      case RED:
        boardSpaces[row][col].setStatus(Tile.TileStatus.RED);
        break;
      case BLACK:
        boardSpaces[row][col].setStatus(Tile.TileStatus.BLACK);
        break;
      case BROWN:
        boardSpaces[row][col].setStatus(Tile.TileStatus.BROWN);
        break;
      case WHITE:
        boardSpaces[row][col].setStatus(Tile.TileStatus.WHITE);
        break;
      default:
    }
  }

   */
/*
  @Override
  public ArrayList<BoardPosition> getValidMoves(BoardPosition p) {
    ArrayList<BoardPosition> validPosns = new ArrayList<>();
    addUpPath(p, validPosns);
    addDownPath(p, validPosns);
    addTopRightPath(p, validPosns);
    addBotRightPath(p, validPosns);
    addTopLeftPath(p, validPosns);
    addBotLeftPath(p, validPosns);

    return validPosns;
  }
  */

  @Override
  public ArrayList<BoardPosition> getValidMoves(BoardPosition p,
                                                ArrayList<BoardPosition> penguins) {
    //TODO: Check inputs

    ArrayList<BoardPosition> validPosns = new ArrayList<>();

    addPath(p, validPosns, penguins, VERTICAL.UP, HORIZONTAL.ZERO);
    addPath(p, validPosns, penguins, VERTICAL.DOWN, HORIZONTAL.ZERO);
    addPath(p, validPosns, penguins, VERTICAL.UP, HORIZONTAL.LEFT);
    addPath(p, validPosns, penguins, VERTICAL.UP, HORIZONTAL.RIGHT);
    addPath(p, validPosns, penguins, VERTICAL.DOWN, HORIZONTAL.LEFT);
    addPath(p, validPosns, penguins, VERTICAL.DOWN, HORIZONTAL.RIGHT);

    return validPosns;
  }


  /**
   * UP: NR = R - 2; NC = C
   * DOWN: NR = R + 2; NC = C
   * UP-LEFT: NR = R - 1; NC = (R % 2 == 0) ? C - 1 : C
   * UP-RIGHT: NR = R - 1; NC = (R % 2 == 1) ? C + 1 : C
   * DOWN-LEFT: NR = R + 1; NC = (R % 2 == 0) ? C - 1 : C
   * DOWN-RIGHT: NR = R + 1; NC = (R % 2 == 1) ? C + 1 : C
   */

  /**
   * Adds the valid positions for a player to move to in a given position to validPosns.
   * We check against the holes on the board, going beyond the boundaries of the board, and
   * a given list of positions of penguins on the board
   *
   * The comment above represents the next row (NR) and next column (NC) that you would move to
   * in a straight line, adjacent movement based on the direction of the movement in terms of the
   * current row (R) and the current column (C)
   *
   * @param p The starting position of a player
   * @param validPosns The current list of valid positions to move to
   * @param penguins The list of positions of penguins on the board
   * @param vertical The vertical direction (up/down) to move in
   * @param horizontal The horizontal direction (left/right/no horizontal) to move in
   */
  private void addPath(BoardPosition p, ArrayList<BoardPosition> validPosns,
                       ArrayList<BoardPosition> penguins,
                       VERTICAL vertical, HORIZONTAL horizontal) {

    int nextRow = 0, nextCol = 0;
    switch (vertical) {
      case UP:
        nextRow = p.getRow() - 2;
        break;
      case DOWN:
        nextRow = p.getRow() + 2;
        break;
      default:
        break;
    }
    switch(horizontal) {
      case ZERO:
        nextCol = p.getCol();
        break;
      case LEFT:
        nextCol = (p.getRow() % 2 == 0) ? p.getCol() - 1 : p.getCol();
        nextRow += (p.getRow() - nextRow) / -2;
        break;
      case RIGHT:
        nextCol = (p.getRow() % 2 == 1) ? p.getCol() + 1 : p.getCol();
        nextRow += (p.getRow() - nextRow) / -2;
        break;
      default:
        break;
    }

    BoardPosition nextPosn = new BoardPosition(nextRow, nextCol);
    if(isValidPosn(nextPosn) && !penguins.contains(nextPosn) && !validPosns.contains(nextPosn)) {
      validPosns.add(nextPosn);
      addPath(nextPosn, validPosns, penguins, vertical, horizontal);
    }
  }

  private boolean isValidPosn(BoardPosition p) {
    int row = p.getRow();
    int col = p.getCol();
    if(row >= 0 && row < this.rows
      && col >= 0 && col < this.cols) {
      return (!boardSpaces[row][col].isHole());
    }
    return false;
  }

  private enum VERTICAL {
    DOWN (1),
    UP (-1);

    private final int value;

    VERTICAL(int value) {
      this.value = value;
    }

    public int getValue() {
      return this.value;
    }
  }

  private enum HORIZONTAL {
    LEFT (-1),
    ZERO(0),
    RIGHT (1);

    private final int value;

    HORIZONTAL(int value) {
      this.value = value;
    }

    public int getValue() {
      return this.value;
    }
  }










}
