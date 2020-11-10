

Feedback:  Need to fix the getValidMoves function in Board class: The function can be simplified, since it would not be easy to fix six copies of code when bugs are found. [TODO]The function would get the starting position and add it to the validTiles ArrayList six time due to the for loop in helper methods. The helper methods are not guarantee to stop when they reach the edge of the board.
Approach: Abstracted the six helper methods of getValidMove into one single method which can determine the six different direction. The abstracted method uses recursion instead of for-loop and it calls isValidPosn method to prevent infinite recursion.
Commit: <https://github.ccs.neu.edu/CS4500-F20/fritch/commit/cacf65fb490494eb31c7ff107877ee9f4b76bdab>


