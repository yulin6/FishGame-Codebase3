To: Prof. Felleisen

From: Derek Feng, Micheal Reveliotis

Subject: Design and API of a referee component for Fish

Date: 10/28/20

### Referee Component Concept/Explanation

The purpose of a referee component is to manage and run a given game of Fish for a given number of players. In addition to functionality referees provide for players in the player-referee protocol (can be found in player-protocol.md), referees need to start the game (handle all of the pieces), run the rounds (from penguin-placement to penguin-movement to game-end phases), and end the game, reporting back to the tournament administrator that set up a given referee. Specifically, it handles requests from players to interact with the game (place penguins/move penguins), both valid and invalid (cheating/failing). The tournament administrator should receive information about winning player(s) moving forward in the tournament and any cheating/failing players that should be removed from the tournament entirely. 

To create a referee, a tournament administrator calls the following public constructor:
	referee()
This constructor takes no parameters; the game pieces (board, its spaces, and their number of fish) are randomly generated (possibly within certain parameters to be determined as within standard game rulings at a later time). This is the first method called, and should be followed by setting up the game with gameSetUp.

To set up a game, a tournament administrator would need to call the following function:
void gameSetUp(ArrayList<TournamentPlayer> players).
This function will take in a list of TournamentPlayers, which consist of an age and a unique username. The referee will create an internal representation of these players, using randomly assigned unique colors (white, red, brown, or black) and the players’ respective ages, and order them based on ascending age. After creating the internal representation, the referee will create a map between the TournamentPlayers and their respective internal representations. The referee will then randomly generate a board, where the fish values on board tiles and positions of holes are random. At this point, a GameState can be constructed with the players and board to finish the game set-up. It should then notify the first player in the playing order that it is their turn to place a penguin. This should be called immediately after construction, once.

In the penguin-placement phase of the game, the referee will place penguins on behalf of players when called with a location
	void placePenguin(BoardPosition bp)
which will, in the event that the placement is valid, notify the next player to place a penguin (or, if this was the last penguin to be placed, notify the next player to move). Illegal penguin placements prompt removals of the player from the game, notifying them and reporting them to the tournament administrator at game’s end. This is called once for every penguin in the game (6 - N per player * N players), during the penguin-placement phase.

During the game, the referee will have to check the actions being performed by the players. The referee will have the function
	boolean isValid(Action a)
to validate a given Action (one of Move or Pass). This function only checks if an Action is valid, regardless of whether a player is performing it. This is called by querying players as well as internally for rule checking.

Referees make moves on behalf of players with a call 
	void doAction(Action a)
which first checks the legality with a call to isValid, then actually performs the move on the game representation held by the referee if it is legal, granting the performing player the number of fish that were on the tile they moved their penguin from. In the case that the move is illegal, the offending player is removed from the game (by removing all of their penguins), and at the end of the game, is reported to the tournament administrator. This method is frequently used, and should be used only during the penguin-movement phase of the game. It should notify the next player that it is their turn, prompting them to take an action. This method should also notify a removed player via its interface that it has been removed from the game. If a game-ending action is taken (no actions are possible in the state after the action is performed), the referee notifies the tournament administrator that the game has ended.

At the end of the game, when players can no longer move their penguins, the referee will declare winners based on which players have the highest number of fish. The referee will be able to notify the tournament administrator of the winners with the function
ArrayList<TournamentPlayer> getWinners(),
which uses the internal interpretation of the player with the most fish to construct a TournamentPlayer. The referee will also be able to convey the list of cheaters and failing players to the tournament administrator with
ArrayList<TournamentPlayer> getCheaters()
and
ArrayList<TournamentPlayer> getFailingPlayers().


