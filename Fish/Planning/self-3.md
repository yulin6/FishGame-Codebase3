## Self-Evaluation Form for Milestone 3

Under each of the following elements below, indicate below where your
TAs can find:

- the data description of states, including an interpretation:

https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/ae39b98161dffabda2b7e3045edf0deda7e9e73b/Fish/Common/src/game/model/GameState.java#L8-L15

The interpretation of the GameState object we created is present here, but there are some details missing about the actual literal data description (objects that it composes) from what is written here.

- a signature/purpose statement of functionality that creates states 

We did not write a signature for the constructor of our GameState while working on this milestone, which is a problem we will need to resolve before the next milestone, especially since it has some function calls inside it that may be confusing to a reader.

- unit tests for functionality of taking a turn 

https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/ae39b98161dffabda2b7e3045edf0deda7e9e73b/Fish/Common/test/game/model/GameStateTest.java#L119-L192

There are tests here for both correctly taking turns and various exception cases relating to taking turns.

- unit tests for functionality of placing an avatar 

https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/ae39b98161dffabda2b7e3045edf0deda7e9e73b/Fish/Common/test/game/model/GameStateTest.java#L67-L117

The unit tests here test both correctly and incorrectly placing avatars (in holes, out of board bounds, etc.).

- unit tests for functionality of final-state test

https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/ae39b98161dffabda2b7e3045edf0deda7e9e73b/Fish/Common/test/game/model/GameStateTest.java#L194-L221

Unit testing for both cases of moves being possible (not-final state) and no moves being possible (final state) are written here.

The ideal feedback is a GitHub perma-link to the range of lines in specific
file or a collection of files for each of the above bullet points.

  WARNING: all such links must point to your commit "ae39b98161dffabda2b7e3045edf0deda7e9e73b".
  Any bad links will result in a zero score for this self-evaluation.
  Here is an example link:
    <https://github.ccs.neu.edu/CS4500-F20/oakwood/tree/ae39b98161dffabda2b7e3045edf0deda7e9e73b/Fish>

A lesser alternative is to specify paths to files and, if files are
longer than a laptop screen, positions within files are appropriate
responses.

In either case you may wish to, beneath each snippet of code you
indicate, add a line or two of commentary that explains how you think
the specified code snippets answers the request.

## Partnership Eval 

Select ONE of the following choices by deleting the other two options.

A) My partner and I contributed equally to this assignment. 

If you chose C, please give some further explanation below describing
the state of your partnership and whether and how you have been or are
addressing this disparity. Describe the overall trajectory of your
partnership from the beginning until now. Be honest with your answer
here, and with each other. Even if it's uncomfortable reading this
together right now.

If you chose one of the other two options, you should feel free to
also add some explanation if you wish. 
