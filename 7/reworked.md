# List of Reworked Sections

## Tile
Feedback: For the TileStatus enum in Tile class: The tiles all have the same color, it is the penguin which
 goes on the tile has different colors. The tile cannot be none, it is a place on the board that is none. The tile
  cannot be a hole. The hole is an attribute of the board, but not of the tile.
- Approach: Large reworking of the entire Tile class was performed. Tile objects after the
 reworking represent actual tiles, with their only knowledge being the number of fish they have. 
 Due to the size of the changes, specific lines are not highlighted in this commit link.
- Commit: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/bd771b0dde4b8a09a9d8b0c09c6088f3b35b53b8#diff-1efc0a0b264deae6b06270fb0d16cfd9

## Board
Feedback: for a data (or type) definition for a representation of boards/interpretation 
the interpretation must include how `row` and `column` from the game board world are represented/interpreted
for hexagon-based game board, what the origin is relative to. Or if it's 0-indexed
   (You just specified what is row and column not how it is represented for the hexagon-based game board)
- Approach: Added an ASCII diagram to clarify the interpretation of a (row, column) pair.
- Commit: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/378bd53821f9d13a91cc8eba1cc8ba20ce10e0c7#diff-036ecebb80e1c70a2eef34e0122ff3e4L11-R35

Feedback:  Need to fix the getValidMoves function in Board class: The function can be simplified, since it would not be easy to fix six copies of code when bugs are found. 
The function would get the starting position and add it to the validTiles ArrayList six time
 due to the for loop in helper methods. The helper methods are not guarantee to stop when
  they reach the edge of the board.
- Approach: Abstracted the six different helper methods into one new helper method, addPath, using
 2 enums as parameters. We also prevent duplicate additions by changing how this helper
  method is organized, and explicitly check for duplicates before adding as well. Additionally, we
  updated the valid position checking function, isValidPosn, to account for board edges.
- Commit: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/cacf65fb490494eb31c7ff107877ee9f4b76bdab#diff-036ecebb80e1c70a2eef34e0122ff3e4
(See Board.java for the described improvements; they are in several different methods.)

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

## GameState
Feedback: no signature for functionality to create game states, no purpose statement for functionality to create game states
- Approach: Added purpose statement and signature to the GameState constructor, explaining that
 the constructor takes a set of players (playerSet parameter) and a board (b parameter), and
  states that the board holds the state of the board for the game of Fish represented by a
   GameState.
- Commit: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/951cdacfdd7788add8127197b239e7e5c684c76d#diff-9d435e785720105cf495c1843387619aL21-R28

Feedback: insufficient coverage of unit tests for turn-taking functionality
- Approach: Added unit tests for cases of multiple moves being performed in a row on the same
 GameState, cycling through two full rounds of player turns to check the current player is
  appropriately updated.
- Commit: https://github.ccs.neu.edu/CS4500-F20/fritch/blob/master/Fish/Common/test/game/model/GameStateTest.java#L166-L212

## GameTree
Feedback: the game tree definition does not have a recursive tree shape, it is unclear that the
 generation of a tree is suspended, it is unclear if the game tree node can represent all three kinds of nodes:
 game-is-over, current-player-is-stuck, and current-player-can-move;  only current-player-can-move
  is obvious, signature/purpose statement does not explain the first query+functionality clearly
- Approach: 
    - Added a mapping of possible actions in the GameTree that is initially null and
 generated once at the first time it was needed, and reused thereon, creating a recursive tree
  shape (has access to its children). 
    - Clarified in the GameTree data interpretation that all
   three kinds of GameState(s) are valid nodes in the tree representation. current-player-is-stuck
    is treated as a state where the current player can only pass as their turn. game-is-over is
     represented as a leaf node of the tree. 
    - Additional documentation was added to clarify the
      query functionality - the passed-in function object that implements the appropriate interface
       is called on each of the children of the current node.
- Commit: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/f6835672e803c21ccc1b5c74cc23c587547b919b

Feedback: Renaming of GameTree to GameTreeNode and IPlayer to IPlayerComponent in order to
 improve readability. 
- Approach: GameTree as an object recursively represents the entire tree, but a single
  instance of the object is a single node with children nodes, so this name better reflects it. 
  IPlayer was confusing when seen next to Player (definitely noticed this during onboarding/observed
   code walks), so it was renamed to IPlayerComponent. 
- Commit: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/7231bd1c1b359839ab9d4d8d3553124ec9a91388

## Strategy
Feedback: Change Strategy to improve readability, pull functionality out of large methods into
 smaller ones where possible to make things easier later if changes are necessary.
- Approach: Moved the board position comparison into its own method, compareBoardPositions, and
 moved the functionality for populating the mapping of moves to expected fish values to a new
  method, fillActionToFish.
- Commit: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/417ad8d33a3ea2ada41a90f21e5c1b695fd8a550

Feedback: choosing turn action: purpose statement doesn't specify what happens when the current player does not have valid moves
- Approach: Added to documentation in IStrategy.java for the purpose statement of the
 getMinMaxAction function, explaining that the Action returned is either a Move (when valid moves
  are present) or a Pass (when no valid moves are present).
- Commit: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/c6270277a97a9c3d1449fd71207f769f5703213d#diff-fe94b8b2dcabdee1fe6fef3473e370ccL19-R22

## Referee
Feedback: Changes to Referee relating to interacting with components. Also, the Referee
documentation only mentions one abnormal player condition that is addressed. It is missing
calls to player methods that result in exceptions.
- Approach: Added Future(s) to Referee enabling timeouts and catching exceptions within the player
 components in order to prevent failing player components from interfering with the referee's
  operations. 
- Commit: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/895dfffb7a4bfb645c6cc367de9a20ce0a6731d7
- Second commit (missed some interactions in first commit): https://github.ccs.neu.edu/CS4500-F20/fritch/commit/3ffe709be5c21c4c20952683cba5fcf97ba89d91

Feedback: Changes to Referee to do with too much functionality being implemented in one place in
 running phases of the game.
- Approach: Moved the functionality involving the running of a single round of each phase into a
 new method for each phase, so now running a phase is a very short method looping rounds and
  checking for end conditions (phases run being penguin placement and penguin movement).
- Commit:  https://github.ccs.neu.edu/CS4500-F20/fritch/commit/efbe45ee1f55161968d5a28328d6adab5a308f58

Feedback: Referee does not document cases where the player mutates referee's trusted data 
structures.
- Approach: Give player components a copy of the GameTreeNode instead of the actual relied-upon
 data. Added documentation explaining that the Action from the player component is checked
  against the trusted data, which could not have been modified. 
- Commit: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/efbe45ee1f55161968d5a28328d6adab5a308f58

Feedback: The functionality for single-turn handling is missing, as well as its tests.
- Approach: Make the newly abstracted single-turn method public, as well as other necessary classes
including doPlayingPhase(), doPlacingPhase() and GamePhase enum. Added tests for the single-turn handling.
- Commit: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/ea23748cd297b91675760e9ce533b5dada0c76dd

Feedback: unit tests only cover one abnormal condition
- Approach: Added unit tests for player timing out when communicating with referee and
 player throwing exception when referee asks it for an action. Also added unit tests for player
  having these problems when startPlaying, getAge, and finishPlaying are called on the player.
- Commit: 