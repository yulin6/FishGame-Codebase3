# Milestone 2 Feedback

Code Inspection 75/90

 -10  for a data (or type) definition for a representation of boards/interpretation
   the interpretation must include how `row` and `column` from the game board world are represented/interpreted
   for hexagon-based game board, what the origin is relative to. Or if it's 0-indexed
   (You just specified what is row and column not how it is represented for the hexagon-based game board)
   
-5 purpose for reachable-tiles functionality doesn't explain that valid moves
  are the ones reachable via straight lines


## Milestone 2 Code Walk Feedback
Problems discovered:
- For the Board Constructor which creates a board with given holes spaces:
    - It missed to check if the passing rows and columns are negative numbers before passing them to
 Tile.
    - It missed to check if the passing holes are more than the actual spaces of the board.
    - It is necessary to give interpretation for the board representation.
- For the TileStatus enum in Tile class:
    - The tiles all have the same color, it is the penguin which goes on the tile has different colors.
    - The tile cannot be none, it is a place on the board that is none.
    - The tile cannot be a hole. The hole is an attribute of the board, but not of the tile.
- The setOneFishTiles function in Board class should come with a warning about the potential for
 non-termination.
- For the getValidMoves function in Board class:
    - The function can be simplified, since it would not be easy to fix six copies of codes when bugs
 are founded out.
    - The function would get the starting position and add it to the validTiles ArrayList six
     time due
 to the for loop in helper methods.
    - The helper methods did not guarantee to stop when they reached the edge of the board.


# Milestone 3 Feedback
Autograde comments:
- +25/25 delivery
- +10/10 xboard executable exists
- +0/15 exe vs staff tests
- +15/15 tests vs staff exe
- +15/0 bonus for bugs found in other submissions

- Inspect failing tests from our executable against staff tests
    - Issue: Was missing “#!/bin/bash" as first line
    - https://github.ccs.neu.edu/CS4500-F20/fritch/commit/b165f46fade8b2c91b83c45ffe6f86fb3eeaed8f

Code Inspection 43/60
+10 self-evaluation accurate

+10 good data definition/interpretation for game states

-2 no signature for functionality to create game states

-8 no purpose statement for functionality to create game states

-7 insufficient coverage of unit tests for turn-taking functionality

+10 good unit tests for avatar placement functionality

+10 good unit tests for functionality that checks if no move is possible


# Milestone 4 Feedback
Autograde comments:
- +30/30 delivery
- +10/10 xstate executable exists
- +10/30 exe vs staff tests
- +10/30 tests vs staff exe
- +0/0 bonus for bugs found in other submissions

- Inspect failing tests from our executable against staff tests
    - We misunderstood how the Player JSON array should be returned - we didn’t update the array
 ordering to reflect whose turn it was, left it as it was passed in
- Inspect failing tests from our tests against staff executable
    - Output files were not correct due Player JSON array based misunderstanding
    - Updated at some commit

Refactored 25/30
+10 insufficient interpretation of the board, more elobration on Hole
+and title within a board needed
+15 sufficient interpretation of the game state

Code Inspection 55/85 + Extra 0/10

+15  self-evaluation

+10 no public piece of functionality that creates a game tree

+10 the game tree definition does not have a recursive tree shape

0 it is unclear that the generation of a tree is suspended

+0 it is unclear if the game tree node can represent all three kinds of nodes:
 game-is-over, current-player-is-stuck, and current-player-can-move;  only current-player-can-move is obvious
 
+10 signature/purpose statement does not explain the first query
+functionality clearly

+10 unit tests for the first query functionality

+0 (extra points) explanation of a lazy ("caching") scheme of the game+tree generation
 
# Milestone 5 Feedback

Milestone 5 autograde score: 80/140
Autograde comments:
- +20/20 delivery
- +10/10 xtree executable exists
- +50/50 exe vs staff tests
- +0/60 tests vs staff exe
- +0/0 bonus for bugs found in other submissions

- Inspect failing tests from our tests vs staff exe
    - Submitted invalid JSON tests; insufficient number of players


Code Inspection 75/80

-5 choosing turn action: purpose statement doesn't specify what happens  when the current player does not have valid moves

Milestone 6 Feedback
(Not yet received)

