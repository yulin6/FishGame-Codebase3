## Self-Evaluation Form for Milestone 2

A fundamental guideline of Fundamentals I, II, and OOD is to design
methods and functions systematically, starting with a signature, a
clear purpose statement (possibly illustrated with examples), and
unit tests.

Under each of the following elements below, indicate below where your
TAs can find:

- the data description of tiles, including an interpretation:
<https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/b9878e2eb56ea3997b47755e1c1c7a9f0ce2d617/Fish/Common/src/gamestate/model/Tile.java>
Here, we detail that the Tile class represents a Tile in a game of fish (line 11). We could have used more details regarding what the tile data representation is.

- the data description of boards, include an interpretation:
<https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/b9878e2eb56ea3997b47755e1c1c7a9f0ce2d617/Fish/Common/src/gamestate/model/Board.java>
Here, we detail that the board class represents a board for the Fish game (line 11). Like with Tile, we should go into more detail about the data representation.

- the functionality for removing a tile:
  - purpose:
  <https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/b9878e2eb56ea3997b47755e1c1c7a9f0ce2d617/Fish/Common/src/gamestate/model/IBoard.java#L18>
  
  - signature:
  <https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/b9878e2eb56ea3997b47755e1c1c7a9f0ce2d617/Fish/Common/src/gamestate/model/IBoard.java#L19-L21>
  
  - unit tests:
  <https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/b9878e2eb56ea3997b47755e1c1c7a9f0ce2d617/Fish/Common/test/gamestate/model/BoardTest.java#L30-L42>

- the functiinality for reaching other tiles on the board:
  - purpose:
  <https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/b9878e2eb56ea3997b47755e1c1c7a9f0ce2d617/Fish/Common/src/gamestate/model/IBoard.java#L11>
  
  - signature:
 <https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/b9878e2eb56ea3997b47755e1c1c7a9f0ce2d617/Fish/Common/src/gamestate/model/IBoard.java#L12-L15>
  
  - unit tests:
  <https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/b9878e2eb56ea3997b47755e1c1c7a9f0ce2d617/Fish/Common/test/gamestate/model/BoardTest.java#L70-L80>

The ideal feedback is a GitHub perma-link to the range of lines in specific
file or a collection of files for each of the above bullet points.

  WARNING: all such links must point to your commit "b9878e2eb56ea3997b47755e1c1c7a9f0ce2d617".
  Any bad links will result in a zero score for this self-evaluation.
  Here is an example link:
    <https://github.ccs.neu.edu/CS4500-F20/oakwood/tree/b9878e2eb56ea3997b47755e1c1c7a9f0ce2d617/Fish>

A lesser alternative is to specify paths to files and, if files are
longer than a laptop screen, positions within files are appropriate
responses.

In either case you may wish to, beneath each snippet of code you
indicate, add a line or two of commentary that explains how you think
the specified code snippets answers the request.
