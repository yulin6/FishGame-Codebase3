## Self-Evaluation Form for Milestone 8

Indicate below where your TAs can find the following elements in your strategy and/or player-interface modules:

1. did you organize the main function/method for the manager around
the 3 parts of its specifications --- point to the main function
    - We did not integrate the tournament preparation part with the other two parts.
    - (Preparation) Inform players and assign them to games: <https://github.ccs.neu.edu/CS4500-F20/fritch/blob/01c1b4c3807aa2bb6cf00ca972d979d9a657a94d/Fish/Admin/src/tmanager/TournamentManager.java#L56-L57>
    - (Running) Run the tournament until it's over: <https://github.ccs.neu.edu/CS4500-F20/fritch/blob/01c1b4c3807aa2bb6cf00ca972d979d9a657a94d/Fish/Admin/src/tmanager/TournamentManager.java#L125-L143>
    - (Ending) Inform remaining active players: <https://github.ccs.neu.edu/CS4500-F20/fritch/blob/01c1b4c3807aa2bb6cf00ca972d979d9a657a94d/Fish/Admin/src/tmanager/TournamentManager.java#L142>

2. did you factor out a function/method for informing players about
the beginning and the end of the tournament? Does this function catch
players that fail to communicate? --- point to the respective pieces
    - InformPlayers method: <https://github.ccs.neu.edu/CS4500-F20/fritch/blob/01c1b4c3807aa2bb6cf00ca972d979d9a657a94d/Fish/Admin/src/tmanager/TournamentManager.java#L217-L249>
    - Inform players in the beginning: <https://github.ccs.neu.edu/CS4500-F20/fritch/blob/01c1b4c3807aa2bb6cf00ca972d979d9a657a94d/Fish/Admin/src/tmanager/TournamentManager.java#L56>
    - Inform players in the end: <https://github.ccs.neu.edu/CS4500-F20/fritch/blob/01c1b4c3807aa2bb6cf00ca972d979d9a657a94d/Fish/Admin/src/tmanager/TournamentManager.java#L142>
    - Remove player when failed to communicate: <https://github.ccs.neu.edu/CS4500-F20/fritch/blob/01c1b4c3807aa2bb6cf00ca972d979d9a657a94d/Fish/Admin/src/tmanager/TournamentManager.java#L231-L248>

3. did you factor out the main loop for running the (possibly 10s of
thousands of) games until the tournament is over? --- point to this
function.
    - <https://github.ccs.neu.edu/CS4500-F20/fritch/blob/01c1b4c3807aa2bb6cf00ca972d979d9a657a94d/Fish/Admin/src/tmanager/TournamentManager.java#L125-L143>

**Please use GitHub perma-links to the range of lines in specific
file or a collection of files for each of the above bullet points.**


  WARNING: all perma-links must point to your commit "01c1b4c3807aa2bb6cf00ca972d979d9a657a94d".
  Any bad links will be penalized.
  Here is an example link:
    <https://github.ccs.neu.edu/CS4500-F20/fritch/tree/01c1b4c3807aa2bb6cf00ca972d979d9a657a94d/Fish>

