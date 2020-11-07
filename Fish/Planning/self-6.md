## Self-Evaluation Form for Milestone 6

Indicate below where your TAs can find the following elements in your strategy and/or player-interface modules:

The implementation of the "steady state" phase of a board game
typically calls for several different pieces: playing a *complete
game*, the *start up* phase, playing one *round* of the game, playing a *turn*, 
each with different demands. The design recipe from the prerequisite courses call
for at least three pieces of functionality implemented as separate
functions or methods:

- the functionality for "place all penguins"

https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/2f6f225db494431aeb259e39ff6b6bed49b9518f/Fish/Admin/src/referee/Referee.java#L200-L228

- a unit test for the "place all penguins" funtionality 

https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/2f6f225db494431aeb259e39ff6b6bed49b9518f/Fish/Admin/test/referee/RefereeTest.java#L84-L92

We do not have direct unit tests for any of our private methods, but this test tests the runGame() public method, which utilizes our "place all penguins" functionality. The instructor answer in [this Piazza post](https://piazza.com/class/kevisd7ggfb502?cid=356) suggests that we should mark down a test for a function that utilizes the one we want to test in this case.

- the "loop till final game state"  function

https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/2f6f225db494431aeb259e39ff6b6bed49b9518f/Fish/Admin/src/referee/Referee.java#L237-L265

- this function must initialize the game tree for the players that survived the start-up phase

The instructor answer in [this Piazza post](https://piazza.com/class/kevisd7ggfb502?cid=355) suggests that the game tree may be initialized elsewhere. We initialize the game tree in the Referee constructor: https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/2f6f225db494431aeb259e39ff6b6bed49b9518f/Fish/Admin/src/referee/Referee.java#L70-L84

- a unit test for the "loop till final game state"  function

https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/2f6f225db494431aeb259e39ff6b6bed49b9518f/Fish/Admin/test/referee/RefereeTest.java#L94-L104

We do not have direct unit tests for any of our private methods, but this test tests the runGame() public method, which utilizes our "loop till final game state" functionality. The instructor answer in [this Piazza post](https://piazza.com/class/kevisd7ggfb502?cid=356) suggests that we should mark down a test for a function that utilizes the one we want to test in this case.

- the "one-round loop" function

We do not have a function for a "one-round loop"; the entire game loop functionality rests in the "loop till final game state" function.

- a unit test for the "one-round loop" function

N/A, see above.

- the "one-turn" per player function

We do not have a function for "one-turn" per player; the entire game loop functionality rests in the "loop till final game state" function.

- a unit test for the "one-turn per player" function with a well-behaved player 

N/A, see above

- a unit test for the "one-turn" function with a cheating player

N/A, see above. However, we do test the full game with a cheating player: https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/2f6f225db494431aeb259e39ff6b6bed49b9518f/Fish/Admin/test/referee/RefereeTest.java#L106-L120

- a unit test for the "one-turn" function with an failing player 

N/A, see above. However, we do test the full game with a failing player: https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/2f6f225db494431aeb259e39ff6b6bed49b9518f/Fish/Admin/test/referee/RefereeTest.java#L122-L136

- for documenting which abnormal conditions the referee addresses 

https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/2f6f225db494431aeb259e39ff6b6bed49b9518f/Fish/Admin/src/referee/Referee.java#L21-L33

- the place where the referee re-initializes the game tree when a player is kicked out for cheating and/or failing 

https://github.ccs.neu.edu/CS4500-F20/oakwood/blob/2f6f225db494431aeb259e39ff6b6bed49b9518f/Fish/Admin/src/referee/Referee.java#L267-L283

This function modifies the game state in the game tree to reflect that the cheating/failing player has been kicked out of the game.


**Please use GitHub perma-links to the range of lines in specific
file or a collection of files for each of the above bullet points.**

  WARNING: all perma-links must point to your commit "2f6f225db494431aeb259e39ff6b6bed49b9518f".
  Any bad links will be penalized.
  Here is an example link:
    <https://github.ccs.neu.edu/CS4500-F20/oakwood/tree/2f6f225db494431aeb259e39ff6b6bed49b9518f/Fish>

