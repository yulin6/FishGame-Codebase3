## Self-Evaluation Form for Milestone 9

You must make an appointment with your grader during his or her office
hour to demo your project. See the end of the self-eval for the assigned
grader. 

Indicate below where your TA can find the following elements in your strategy 
and/or player-interface modules: 

1. for human players, point the TA to
   - the interface (signature) that an AI player implements
   - the interface that the human-GUI component implements
   - the implementation of the player GUI

2. for game observers, point the TA to
   - the `game-observer` interface that observers implement 
        - Observer class: <https://github.ccs.neu.edu/CS4500-F20/panhandle/blob/07d8df830425189d0fde56abf68c83b1631b2500/Fish/Common/src/game/observer/Observer.java#L6-L28>
        - StateChangeListener interface: <https://github.ccs.neu.edu/CS4500-F20/panhandle/blob/07d8df830425189d0fde56abf68c83b1631b2500/Fish/Common/src/game/observer/StateChangeListener.java#L3-L12>
   - the point where the `referee` consumes observers 
        - observer is a variable in Referee: <https://github.ccs.neu.edu/CS4500-F20/panhandle/blob/07d8df830425189d0fde56abf68c83b1631b2500/Fish/Admin/src/referee/Referee.java#L75>
        - the setListener method will be called by FishController: <https://github.ccs.neu.edu/CS4500-F20/panhandle/blob/07d8df830425189d0fde56abf68c83b1631b2500/Fish/Admin/src/referee/Referee.java#L547-L549>
        - FishController class: <https://github.ccs.neu.edu/CS4500-F20/panhandle/blob/07d8df830425189d0fde56abf68c83b1631b2500/Fish/Common/src/game/controller/FishController.java#L23>
   - the callback from `referee` to observers concerning turns
        - In the Referee class, whenever there is a change in the GameState, observer.notifyListener() will be called.
        - <https://github.ccs.neu.edu/CS4500-F20/panhandle/blob/07d8df830425189d0fde56abf68c83b1631b2500/Fish/Admin/src/referee/Referee.java#L266>
        - <https://github.ccs.neu.edu/CS4500-F20/panhandle/blob/07d8df830425189d0fde56abf68c83b1631b2500/Fish/Admin/src/referee/Referee.java#L324>
        - <https://github.ccs.neu.edu/CS4500-F20/panhandle/blob/07d8df830425189d0fde56abf68c83b1631b2500/Fish/Admin/src/referee/Referee.java#L370-L385>
        - <https://github.ccs.neu.edu/CS4500-F20/panhandle/blob/07d8df830425189d0fde56abf68c83b1631b2500/Fish/Admin/src/referee/Referee.java#L509>

3. for tournament observers, point the TA to
   - the `tournament-observer` interface that observers implement 
   - the point where the `manager` consumes observers 
   - the callback to observes concerning the results of rounds 


Do not forget to meet the assigned TA for a demo; see bottom.  If the
TA's office hour overlaps with other obligations, sign up for a 1-1.


**Please use GitHub perma-links to the range of lines in specific
file or a collection of files for each of the above bullet points.**


  WARNING: all perma-links must point to your commit "07d8df830425189d0fde56abf68c83b1631b2500".
  Any bad links will be penalized.
  Here is an example link:
    <https://github.ccs.neu.edu/CS4500-F20/panhandle/tree/07d8df830425189d0fde56abf68c83b1631b2500/Fish>

Assigned grader = Shobana (shobana.m@northeastern.edu)

