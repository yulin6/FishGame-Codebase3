
To: Prof. Felleisen

From: Derek Feng, Micheal Reveliotis

Subject: Data representations for entire games of Fish and external interface

Date: 10/15/20

  

#### Data Representation:

The representation of the entire game will allow players and referees to check the legality of certain moves, and players will be able to plan their next move by looking ahead at possible moves in the game. The representation of the entire Game takes the form of a composition of StateNodes into a tree. All the children of a given StateNode will correspond to the state of the game after making one of the possible moves of the parent StateNode, and a parent should have as many children as there are possible moves in order to represent the complete list of possible game states. The root of the Game tree will be the StateNode that contains the GameState that results from the initial placement of all the players’ penguins.

In order to represent transitions between StateNodes, we will create an additional data representation for a given move in a game of Fish, called Move. A Move contains the source BoardPosition, the destination BoardPosition, and the Player making the move. The source position and destination position will be useful for looking ahead at how fish may be distributed to players depending on what upcoming sequence of moves occur; Players collect the fish from the tile they leave, and they are guaranteed to get the fish from the tile they are moving their penguin to when that penguin is moved elsewhere in a future turn. Even if that penguin cannot move elsewhere and collect the fish, the fish will be available to no other player since the occupied tile cannot be moved to by any other player. Additionally, referees and players may simulate a Move via the external interface on a given StateNode to determine whether it is legal to perform.

#### External Interface:

The external interface for this game data representation will include functionality for checking rules for move legality as well as querying future states of the game with a list of moves to be executed between the current state and the future state. The function signatures of the StateNode interface will appear as follows (subject to change):

  

-   boolean isLegalMove(Move m)
    

    -   This function will check the legality of a potential move, returning true if it can be performed from the current state of the game.
    

-   StateNode checkFutureState(List<Move> futureMoves)
    

    -   This function will return the future state of the Fish game after the passed-in list of moves is executed from the current state. In the event that moves in the list are invalid, the function will have to indicate that they are not possible, which likely will be performed by throwing an exception.
    

-   List<Move> getPossibleMoves()
    

    -   This function will return a list of all possible Move(s) of the current state.
    

  

Additionally, we will be creating a Move class in order to represent moves passed in by players or referees as parameters to these StateNode functions. The external interface of StateNode will be simple, as the interactions that need to be performed are mostly only related to extracting data from its fields.

  

-   BoardPosition getFrom()
    

    -   This function will return the BoardPosition corresponding to the board space the penguin moves from. (A BoardPosition has two fields - row and column - that correspond to index numbers of our 2D array of board spaces, and is zero-indexed.)
    

-   BoardPosition getTo()
    

    -   This function will return the BoardPosition corresponding to the board space the penguin is being moved to in this move.
    

-   Player getPlayer()
    

    -   Returns the player who is making this move.
