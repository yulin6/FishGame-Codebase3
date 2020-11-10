## To-Do List
Sorted by priority, highest to lowest being top to bottom. Generally, lower-level data
 definitions are prioritized because higher-level fixes may be dependent on changes at lower levels.

[X] Refactor in Tile to separate out data that should not be part of its definition
- Need to refactor (possibly entirely remove) TileStatus enum in Tile class.
    - The tile cannot be a hole/none, a hole is a place on the board that is not a tile.
    - The tiles all have the same color, it is the penguin which goes on the tile has different colors.

[ ] Perform refactor of board for data definition and interpretation, then correct its methods
- Need sufficient interpretation of the board, more elaboration on Hole and Tile within a board.
    - The interpretation need to include how `row` and `column` from the game board world are
 represented/interpreted.
    - Need to include what the origin is relative to for hexagon-based game board, if it's 0
    indexed. 
- Need to fix the Board Constructor which creates a board with given holes has too many spaces. 
    - It did not check if the passing rows and columns are negative numbers before passing
     them to Tile.
    - It did not check if the passing holes are more than the actual spaces of the board.
- Need to fix the purpose statement for reachable-tiles functionality, which didn't explain that
 valid moves are the moves to tiles reachable via straight lines.
- Need a warning about the potential for non-termination of the setOneFishTiles function in Board class.
- Need to fix the getValidMoves function in Board class:
    - The function can be simplified, since it would not be easy to fix six copies of code when
     bugs are found.
    - The function would get the starting position and add it to the validTiles ArrayList six time due to the for loop in helper methods.
    - The helper methods are not guarantee to stop when they reach the edge of the board.

[ ] Inspect failing integration tests for Milestone 3.
- Our executable failed vs staff tests.

[ ] Add to the data interpretation of GameState(s) and add more testing to cover more turn-taking
 scenarios.
- Need a signature for functionality to create game states.
- Need a purpose statement for functionality to create game states.
- Need more unit tests for turn-taking functionality.

[ ] Inspect failing integration tests for Milestone 4.
- Investigate our exe failing against staff tests.
- Investigate our tests against staff executable.

[ ] Refactor definition and implementation of GameTree in order to better represent a recursive data structure.
- Need to elaborate and correct the game tree definition, which did not have a recursive tree shape.
- Need to be clear about how the generation of a tree is suspended.
- Need to be clear about if the game tree node can represent all three kinds of nodes: game-is
-over, current-player-is-stuck, and current-player-can-move;  only current-player-can-move is
 obvious.
- Need to explain first query functionality in signature/purpose statement clearly.

[ ] Inspect failing integration tests for Milestone 5.
- Our tests failed against the staff executable.

[ ] Add documentation to Strategy for an unmentioned case.
- Need to specify what happens when the current player does not have valid moves in the purpose
 statement of choosing turn action.