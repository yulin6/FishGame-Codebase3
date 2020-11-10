## Todo List

### Milestone 2 

[ ] Need a data (or type) definition for a representation of boards/interpretation:

- The interpretation need to include how `row` and `column` from the game board world are represented/interpreted

-  Need to includ what the origin is relative to for hexagon-based game board, if it's 0-indexed
   
[ ] Need to fix the purpose for reachable-tiles functionality, which didn't explain that valid moves are the ones reachable via straight lines


[ ] Need to fix the Board Constructor which creates a board with given holes spaces:
- It missed to check if the passing rows and columns are negative numbers before passing them to Tile.
- It missed to check if the passing holes are more than the actual spaces of the board.


[ ] Need to fix TileStatus enum in Tile class:
- The tiles all have the same color, it is the penguin which goes on the tile has different colors.
- The tile cannot be none, it is a place on the board that is none.
- The tile cannot be a hole. The hole is an attribute of the board, but not of the tile.

[ ] Need a warning about the potential for non-termination of the setOneFishTiles function in Board class.

[ ] Need to fix the getValidMoves function in Board class:
- The function can be simplified, since it would not be easy to fix six copies of codes when bugs are founded out.
- The function would get the starting position and add it to the validTiles ArrayList six time due to the for loop in helper methods.
- The helper methods did not guarantee to stop when they reached the edge of the board.


### Milestone 3 
[ ] Inspect failing tests from our executable against staff tests:
- Issue: Was missing “#!/bin/bash" as first line
- https://github.ccs.neu.edu/CS4500-F20/fritch/commit/b165f46fade8b2c91b83c45ffe6f86fb3eeaed8f

[ ] Need a signature for functionality to create game states

[ ] Need a purpose statement for functionality to create game states

[ ] Need more unit tests for turn-taking functionality



### Milestone 4 

[ ] Inspect failing tests from our executable against staff tests:
- We misunderstood how the Player JSON array should be returned - we didn’t update the array ordering to reflect whose turn it was, left it as it was passed in

[ ] Inspect failing tests from our tests against staff executable:
- Output files were not correct due Player JSON array based misunderstanding
- Updated at some commit

[ ] Need sufficient interpretation of the board, more elobration on Hole and title within a board 

[ ] Need to add public piece of functionality that creates a game tree

[ ] Need to elaborate the game tree definition, which did not have a recursive tree shape

[ ] Need to be clear about when the generation of a tree is suspended

[ ] Need to be clear about if the game tree node can represent all three kinds of nodes: game-is-over, current-player-is-stuck, and current-player-can-move;  only current-player-can-move is obvious
 
[ ] Need to explain first query functionality in signature/purpose statement clearly

[ ] (extra points) Need explanation of a lazy ("caching") scheme of the game+tree generation
 
### Milestone 5 

[ ] Inspect failing tests from our tests vs staff exe:
- Submitted invalid JSON tests; insufficient number of players

[ ] Need to specify what happens when the current player does not have valid moves in the purpose statement of choosing turn action

### Milestone 6

