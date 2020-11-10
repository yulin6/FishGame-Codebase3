## List of Reworked Sections
Feedback: For the TileStatus enum in Tile class: The tiles all have the same color, it is the penguin which
 goes on the tile has different colors. The tile cannot be none, it is a place on the board that is none. The tile
  cannot be a hole. The hole is an attribute of the board, but not of the tile.
- Approach: Large refactor of the entire Tile class was performed. Tile objects after the
 refactor represent actual tiles, with their only knowledge being the number of fish they have
 . Due to the size of the refactor, specific lines are not highlighted in this commit link.
- Commit: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/bd771b0dde4b8a09a9d8b0c09c6088f3b35b53b8#diff-1efc0a0b264deae6b06270fb0d16cfd9

Feedback: for a data (or type) definition for a representation of boards/interpretation 
the interpretation must include how `row` and `column` from the game board world are represented/interpreted
for hexagon-based game board, what the origin is relative to. Or if it's 0-indexed
   (You just specified what is row and column not how it is represented for the hexagon-based game board)
- Approach: Added an ASCII diagram to clarify the interpretation of a (row, column) pair.
- Commit: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/378bd53821f9d13a91cc8eba1cc8ba20ce10e0c7#diff-036ecebb80e1c70a2eef34e0122ff3e4L11-R35

Feedback:  Need to fix the getValidMoves function in Board class: The function can be simplified, since it would not be easy to fix six copies of code when bugs are found. [TODO]The function would get the starting position and add it to the validTiles ArrayList six time due to the for loop in helper methods. The helper methods are not guarantee to stop when they reach the edge of the board.
Approach: Abstracted the six helper methods of getValidMove into one single method which can determine the six different direction. The abstracted method uses recursion instead of for-loop and it calls isValidPosn method to prevent infinite recursion.
Commit: <https://github.ccs.neu.edu/CS4500-F20/fritch/commit/cacf65fb490494eb31c7ff107877ee9f4b76bdab>

Feedback: For the Board Constructor which creates a board with given holes spaces:
          It missed to check if the passing rows and columns are negative numbers before passing them to Tile.
          It missed to check if the passing holes are more than the actual spaces of the board.
- Approach: Added argument checking at the beginning of the constructor to throw an exception in
 the event that the board with the passed in parameters is not a legal board.
 - Commit: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/cacf65fb490494eb31c7ff107877ee9f4b76bdab#diff-036ecebb80e1c70a2eef34e0122ff3e4L31-R73

Feedback: purpose for reachable-tiles functionality doesn't explain that valid moves
            are the ones reachable via straight lines
- Approach: Added documentation to the signature of the method explaining that valid moves are
 tiles reachable via straight-line movement and are not blocked by holes.
- Commit: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/79cff9ad116cca02af505ae2a70857f54d9743c8#diff-fedce09cfce4714634a2e92d5140ecd7L10-R20

Feedback: The setOneFishTiles function in Board class should come with a warning about the potential for non-termination.
- Approach: Added documentation explaining that termination will occur, now that the constructor
 appropriately performs input validity checking, since eventually enough tiles will be selected
  randomly to be 1-fish tiles.
- Commit: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/22fb207c57e633642f84fe1fdced46bb657dd15e#diff-ac9d71ba0e24b5fae062c12740d1fde7L172-R175

Feedback: no signature for functionality to create game states, no purpose statement for functionality to create game states
- Approach: Added purpose statement and signature to the GameState constructor, explaining that
 the constructor takes a set of players (playerSet parameter) and a board (b parameter), and
  states that the board holds the state of the board for the game of Fish represented by a
   GameState.
- Commit: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/951cdacfdd7788add8127197b239e7e5c684c76d#diff-9d435e785720105cf495c1843387619aL21-R28

Feedback: insufficient coverage of unit tests for turn-taking functionality
- Approach: Added unit tests for cases of multiple moves being performed in a row on the same
 GameState.
- Commit: 