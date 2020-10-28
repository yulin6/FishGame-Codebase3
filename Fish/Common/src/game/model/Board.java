package game.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a board of Fish.
 * A Board contains a grid of BoardSpaces, which are "filled" either with a Hole or
 * a Tile. A Hole object represents a hole in the board, while a Tile object represents a tile in
 * the board, with a number of fish on it.
 * A Board also contains the dimensions of its grid.
 * See the below ASCII diagram for a reference of the coordinate system of the board.
 *
 *     XXXXXXXXX             XXXXXXXXX
 *    X         X           X         X
 *   X           X         X           X
 *  X     0,0     X       X      0,1    X
 *   X           XXXXXXXXXXX           XXXXXXXXXX
 *    X         XX         XX         XX         X
 *     XXXXXXXXXX           XXXXXXXXXXX           X
 *     XXXXXXXXX      1,0    XXXXXXXXX     1,1     X
 *    X         X           X         X           X
 *   X           X         X           X         X
 *  X     2,0     XXXXXXXXX     2,1     XXXXXXXXX
 *   X           XXXXXXXXXXX           XXXXXXXXXX
 *    X         XX         XX         XX         X
 *     XXXXXXXXXX           XXXXXXXXXXX           X
 *      XXXXXXXXX     3,0    XXXXXXXXXX    3,1     X
 *     X        XX          XX        XX          XX
 *    X          XX        XX          XX        XX
 *   X     4,0    XXXXXXXXXX    4,1     XXXXXXXXX
 *    X           X       XX           XX
 *     X         XX         XX         XX
 *      XXXXXXXXXX           XXXXXXXXXXX
 */
public class Board implements IBoard {
  public static final int MAX_FISH = 5;
  public static final int MIN_FISH = 1;

  final int rows;
  final int cols;
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
    checkArguments(rows, columns, holes, minTiles);
    this.rows = rows;
    this.cols = columns;
    this.boardSpaces = new BoardSpace[this.rows][this.cols];
    int numOneFish = initTilesRandom();

    numOneFish -= removeHoles(holes);

    setOneFishTiles(numOneFish, minTiles);
  }

  /**
   * Given the input board parameters, throws an exception if the board cannot be
   * constructed with the given parameters.
   * @param rows Number of rows the board would be constructed with
   * @param columns Number of columns the board would be constructed with
   * @param holes The list of holes in the board
   * @param minTiles The minimum number of 1-fish tiles the board would be constructed with
   */
  private void checkArguments(int rows, int columns,
                              ArrayList<BoardPosition> holes, int minTiles) {
    if (rows <= 0 || columns <= 0) {
      throw new IllegalArgumentException("Cannot have a board dimension of size 0 or less.");
    }
    else if (minTiles > (rows * columns) - holes.size()) {
      throw new IllegalArgumentException("Insufficient tiles to generate board with the" +
              "specified number of 1-fish tiles and the given holes.");
    }
    // validate that all holes are within the board
    for (BoardPosition bp : holes) {
      int holeR = bp.getRow();
      int holeC = bp.getCol();
      if (holeR < 0 || holeC < 0) {
        throw new IllegalArgumentException("Cannot have a hole with position parameter" +
                " less than 0.");
      }
      else if (holeR >= rows || holeC >= columns) {
        throw new IllegalArgumentException("A hole is specified outside the board limits.");
      }
    }
  }

  /**
   * Creates a full uniform board with each tile having fishNum fish
   * @param rows the number of rows of tiles on the board
   * @param columns the number of columns of tiles on the board
   * @param fishNum the number of fish on each tile
   */
  public Board (int rows, int columns, int fishNum) {
    // check input arguments with no holes & no minimum count of 1-fish tiles
    checkArguments(rows, columns, new ArrayList<>(), 0);
    if (fishNum < MIN_FISH || fishNum > MAX_FISH) {
      throw new IllegalArgumentException("Cannot construct a board with the specified" +
              "amount of fish on every tile.");
    }
    this.rows = rows;
    this.cols = columns;
    this.boardSpaces = new BoardSpace[this.rows][this.cols];

    initTiles(fishNum);
  }

  /**
   * Constructs a board with the specified amount of rows and columns
   * as appropriate for the given board spaces.
   * Board representation is the same as above; places the board spaces into the 2D array
   * at the same row in each column for every pass.
   * This is exclusively used for the xboard integration tests and makes the assumption
   * that the input is always well-formed and valid, as specified in homework descriptions.
   * @param entries The board spaces (tiles/holes) to place on the board.
   */
  public Board(int rows, int columns, List<List<Integer>> entries) {
    this.rows = rows;
    this.cols = columns;
    this.boardSpaces = new BoardSpace[this.rows][this.cols];

    for (int i = 0; i < rows; i++) {
      List<Integer> row = entries.get(i);
      for (int j = 0; j < columns; j++) {
        if (j >= row.size()) {
          boardSpaces[i][j] = new Hole();
          continue;
        }
        int numFish = row.get(j);
        if (numFish == 0) {
          boardSpaces[i][j] = new Hole();
        }
        else {
          boardSpaces[i][j] = new Tile(numFish);
        }
      }
    }
  }

  /**
   * Copy constructor for Board objects. Copies the 2D array of BoardSpaces from the given Board
   * as input, as well as other fields.
   * @param b The Board to make a copy of.
   */
  public Board(Board b) {
    this.rows = b.rows;
    this.cols = b.cols;

    this.boardSpaces = new BoardSpace[this.rows][this.cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        BoardSpace space = b.boardSpaces[i][j];
        if (space instanceof Hole) {
          this.boardSpaces[i][j] = new Hole();
        }
        else if (space instanceof Tile) {
          this.boardSpaces[i][j] = new Tile((Tile) space);
        }
      }
    }
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
   * Builds a uniform board with the same number of fish on every tile.
   * @param numFish number of fish on each tile
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
  public void render(Graphics g) {
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
  public int getRows() {
    return rows;
  }

  @Override
  public int getCols() {
    return cols;
  }

  @Override
  public ArrayList<BoardPosition> getValidMoves(BoardPosition p,
                                                ArrayList<BoardPosition> invalidPosns) {
    if (!isValidPosn(p)) {
      throw new IllegalArgumentException("Position to check from is not within board bounds.");
    }
    for (BoardPosition bp : invalidPosns) {
      if (!isValidPosn(bp)) {
        throw new IllegalArgumentException(("An invalid position is outside the " +
                "board bounds."));
      }
    }

    ArrayList<BoardPosition> validPosns = new ArrayList<>();

    addPath(p, validPosns, invalidPosns, VERTICAL.UP, HORIZONTAL.ZERO);
    addPath(p, validPosns, invalidPosns, VERTICAL.DOWN, HORIZONTAL.ZERO);
    addPath(p, validPosns, invalidPosns, VERTICAL.UP, HORIZONTAL.LEFT);
    addPath(p, validPosns, invalidPosns, VERTICAL.UP, HORIZONTAL.RIGHT);
    addPath(p, validPosns, invalidPosns, VERTICAL.DOWN, HORIZONTAL.LEFT);
    addPath(p, validPosns, invalidPosns, VERTICAL.DOWN, HORIZONTAL.RIGHT);

    return validPosns;
  }

  @Override
  public BoardSpace getSpace(BoardPosition p) {
    if (isValidPosn(p)) {
      return boardSpaces[p.getRow()][p.getCol()];
    }
    throw new IllegalArgumentException("Board space is not accessible with given position.");
  }

  /*
   * Math for next board position for pathing based on direction.
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
   * a given list of any additional invalid positions on the board
   *
   * The comment above represents the next row (NR) and next column (NC) that you would move to
   * in a straight line, adjacent movement based on the direction of the movement in terms of the
   * current row (R) and the current column (C)
   *
   * @param p The starting position of a player
   * @param validPosns The current list of valid positions to move to
   * @param invalidPosns The list of invalid positions on the board
   * @param vertical The vertical direction (up/down) to move in
   * @param horizontal The horizontal direction (left/right/no horizontal) to move in
   */
  private void addPath(BoardPosition p, ArrayList<BoardPosition> validPosns,
                       ArrayList<BoardPosition> invalidPosns,
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
        nextRow += (nextRow - p.getRow()) / -2;
        break;
      case RIGHT:
        nextCol = (p.getRow() % 2 == 1) ? p.getCol() + 1 : p.getCol();
        nextRow += (nextRow - p.getRow()) / -2;
        break;
      default:
        break;
    }

    BoardPosition nextPosn = new BoardPosition(nextRow, nextCol);
    if (isValidPosn(nextPosn) && !invalidPosns.contains(nextPosn)
            && !validPosns.contains(nextPosn) && !getSpace(nextPosn).isHole()) {
      validPosns.add(nextPosn);
      addPath(nextPosn, validPosns, invalidPosns, vertical, horizontal);
    }
  }

  @Override
  public boolean isValidPosn(BoardPosition p) {
    int row = p.getRow();
    int col = p.getCol();
    return (row >= 0 && row < this.rows && col >= 0 && col < this.cols);
  }

  /**
   * An enum that represents the vertical direction of a move from a given hexagon tile on the
   * board. An avatar can be moved either in a downward vertical direction or an upward vertical
   * direction.
   *
   */
  private enum VERTICAL {
    DOWN,
    UP
  }

  /**
   * An enum that represents the horizontal direction of a move from a given hexagon tile on the
   * board. An avatar can be moved either in an leftward horizontal direction, a rightward
   * horizontal direction, or in no horizontal direction.
   */
  private enum HORIZONTAL {
    LEFT,
    ZERO,
    RIGHT
  }
}
