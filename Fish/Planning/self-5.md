## Self-Evaluation Form for Milestone 5

Under each of the following elements below, indicate below where your
TAs can find:

- the data definition, including interpretation, of penguin placements for setups 

The placePenguin functionality in Strategy.java returns a BoardPosition, the data definition for which can be found at
https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/04f57901b71243e582cddb47315bd2b9e9a55f7f/Fish/Common/src/game/model/BoardPosition.java#L3-L10

Additionally, a visualization of BoardPosition coordinates corresponding to a Board can be found at

https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/04f57901b71243e582cddb47315bd2b9e9a55f7f/Fish/Common/src/game/model/Board.java#L15-L35

- the data definition, including interpretation, of penguin movements for turns

The data definition and interpretation for moves can be found at
https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/04f57901b71243e582cddb47315bd2b9e9a55f7f/Fish/Common/src/game/model/Move.java#L5-L15

- the unit tests for the penguin placement strategy 

https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/04f57901b71243e582cddb47315bd2b9e9a55f7f/Fish/Player/test/player/StrategyTest.java#L163-L176

- the unit tests for the penguin movement strategy; 
  given that the exploration depth is a parameter `N`, there should be at least two unit tests for different depths 
  
https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/04f57901b71243e582cddb47315bd2b9e9a55f7f/Fish/Player/test/player/StrategyTest.java#L178-L243
  
- any game-tree functionality you had to add to create the `xtest` test harness:
  - where the functionality is defined in `game-tree.PP`
  - where the functionality is used in `xtree`
  - you may wish to submit a `git-diff` for `game-tree` and any auxiliary modules 
  
We did not add any functionality to our game tree representation specifically for the xtree test harness; we performed a refactor of GameTree.java during this milestone to better represent a lazy-generating recursive data structure, but the methods used in xtree (getGameState() and lookAhead(Action a)) did not need modification from their initial implementation. 

**Please use GitHub perma-links to the range of lines in specific
file or a collection of files for each of the above bullet points.**

  WARNING: all perma-links must point to your commit "04f57901b71243e582cddb47315bd2b9e9a55f7f".
  Any bad links will result in a zero score for this self-evaluation.
  Here is an example link:
    <https://github.ccs.neu.edu/CS4500-F20/oakwood/tree/04f57901b71243e582cddb47315bd2b9e9a55f7f/Fish>

