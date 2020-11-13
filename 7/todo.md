# To-Do List

Sorted by priority within the relevant milestone.

## Tile
[X] Large changes in Tile to separate out data that should not be part of its definition
- Need to change (possibly entirely remove) TileStatus enum in Tile class.
    - The tile cannot be a hole/none, a hole is a place on the board that is not a tile.
    - The tiles all have the same color, it is the penguin which goes on the tile has different colors.

## Board
[X] Perform correction of board for data definition and interpretation, then correct its methods
- Need sufficient interpretation of the board, more elaboration on Hole and Tile within a board.
- Need to fix the Board Constructor which creates a board with given holes has too many spaces. 
- Need to fix the purpose statement for reachable-tiles functionality, which didn't explain that
 valid moves are the moves to tiles reachable via straight lines.
- Need a warning about the potential for non-termination of the setOneFishTiles function in Board class.
- Need to fix the getValidMoves function in Board class.

## GameState
[X] Add to the data interpretation of GameState(s) and add more testing to cover more turn-taking
 scenarios.
- Need a signature for functionality to create game states.
- Need a purpose statement for functionality to create game states.
- Need more unit tests for turn-taking functionality.

[] Inspect the interpretation of how turns are currently handled in the GameState. The current
 approach is that "the GameState exists and is manipulated by the Referee", where placing an
  avatar and moving an avatar don't advance the current turn, and the referee is the one who
adjusts the queue by calling setNextPlayer.

[X] Inspect failing integration tests for Milestone 3. Look into after relevant milestone code is
fixed. Ensure the executable is appropriately runnable on the delivery server.
- Our executable failed vs staff tests.

## GameTree
[X] Change definition and implementation of GameTree in order to better represent a recursive
 data structure.
- Need to elaborate and correct the game tree definition, which did not have a recursive tree shape.
- Need to be clear about how the generation of a tree is suspended.
- Need to be clear about if the game tree node can represent all three kinds of nodes: game-is
-over, current-player-is-stuck, and current-player-can-move;  only current-player-can-move is
 obvious.
- Need to explain first query functionality in signature/purpose statement clearly.

[X] Rename GameTree to clarify that a given instance of the type is only a single node with
 connections to child nodes. Rename IPlayer to clarify to readers that it is the interface
  specifically for player components, to avoid confusion between it and internal players.
  
[] Edit GameTreeNode to remove the use of the isLegal function where it's unnecessary, as
 legality checking is already performed in doAction.
 
[X] Inspect failing integration tests for Milestone 4. Look into after relevant milestone code is
 fixed, then correct the test harness to solve the JSON serialization issues where present.
 - Investigate our exe failing against staff tests.
 - Investigate our tests against staff executable.


## Strategy
[X] Inspect code in Strategy - it works, but is difficult to read due to the length of individual
 methods, making it hard to understand all of the functionality performed by a single method. Try
  to move things into the smallest possible piece of functionality as a single method, and
   compose methods. 
   
[X] Add documentation to Strategy for an unmentioned case where a player lacks moves.
- Need to specify what happens when the current player does not have valid moves in the purpose
 statement of choosing turn action.
 
[X] Inspect failing integration tests for Milestone 5. Look into after relevant milestone code is
 fixed. Correct the JSON tests that did not work against the staff exe.
- Find out why tests failed against the staff executable.

## Referee
Note: Priority listing is a bit strange here because some changes were made before we received
 feedback from Milestone 7, so the items are listed in order they were completed.

[X] Changes to Referee relating to interacting with components and separating functionality into
 smaller pieces.
- Referee does not currently account for infinite loops/exceptions, etc. when calling methods on
 the player components that may cause the referee to break. Add behavior for and document.
- Break functionality of running phases (penguin placing or penguin moving) into smaller pieces; 
run rounds in a loop, where a round is each player making one placement/movement each.

[X] Abstract functionality in Referee where similar behavior is present. Specifically, try to
 abstract between the round-running functionality and the player-communication Future
  functionality, now that it's implemented. Will likely require a new type of Action for placing
   penguins.

[] Correct issue where player may mutate referee's trusted data component (GameTreeNode passed to
 the player).

[] Add functionality for single-turn handling (further abstraction from single-round handling).
Add unit tests for new functionality.

[] After abstraction of functionality in Referee, add unit tests to cover missed cases. Need unit
 tests for placement only (without making moves phase), and tests for new abnormal conditions
  (timeout and exceptions).
 
## Other
[] Review the use of class-based constants and file paths to resources (images for rendering, etc.)
to make sure they're in logical locations.