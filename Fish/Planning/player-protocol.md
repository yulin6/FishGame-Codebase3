To: Prof. Felleisen

From: Derek Feng, Micheal Reveliotis

Subject: Design of a protocol for an API for players to communicate with referees

Date: 10/22/20

The API for players to communicate with referees will provide them with the proper functionality required to play the game. The functionalities of the API will be as follows, and the protocol for their use is specified under each. Any illegal actions that a player attempts to perform will result in them being kicked out of the game, though querying if an illegal action is legal is allowed. See the JSON object definitions below the following section as necessary.

## Player-referee protocol:

Placing an avatar
- In order to place an avatar, a player should first verify the Phase of the game is “placing
” (see “Getting phase”) and that it is currently their turn, then retrieve the current state of the board to find a position to place an avatar at. Once a valid position is located, the player places the avatar by specifying a Posn to place it at. 

Making a move
- To make a move, a player should first verify the state of the game is in the “playing” stage and
 that it is currently their turn. After, they should retrieve the current state of the game to find a position to move one of their avatars to. After verifying their move is valid (see “Getting move legality”), the player specifies two Posn(s), a start position representing one of their penguins’ positions and an end position that represents where they would like to move that penguin. 

Making a pass
- To make a pass, a player should check the state, phase, and current player to ensure that their
 pass is valid. Afterwards, they make the pass using this functionality. 

Getting information about the game
- Various information about the game can be queried for, including the current status of all
  players, the location of the penguins on the board, and the state of the board. This can be requested through the API, and will be provided as a State JSON object.

Getting phase
- The phase of the game can be queried for, and is returned to the player as a Phase.

Getting the current player
- The API can be used by a player to query the current player of the game, which will be indicated
 by a Color being returned, which corresponds to the current player.

Getting move legality
- Much like making a move, a player will specify a start position and an end position, which will
 be used to verify whether their move is legal. If the move is legal, the player will receive the
  resulting game state in the form of a State JSON object. Otherwise, they will receive a false
   boolean value. The player component must also specify the color of the player making the move.
   
Getting pass legality
- A player can query the results of a given player (specified by color) passing their turn, and if
 the pass is legal, the resulting State will be returned; otherwise they will receive a false value.

Performing function on all possible next game states
- To perform a function on all possible next game states, the player must provide a function
 implementing the Java Consumer interface that operates on a List<GameTree> so that it may be applied to all resulting game states possible from a move in the current game state. (This may change due to concerns about exposing the GameTree object.)

Receiving information about the end of a game
- If the game being played is ended by some means (most likely by a victory, but possibly
 terminated from above), any of the querying/action functionality will return a JSON value, specifically a string “game is over”, which will indicate the game being played has ended.

Receiving tournament information 
- If the game is over, the player can send a query about the tournament info, which may contain
 information about which player won this game, whether a player will advance to the next game, etc. If the query is sent during a still-running game, a response of “cannot query now” will be sent, but the player will not be ejected from the game, as it is a query and not an action.
 
For all actions (as opposed to queries) being performed, a JSON value ("true"/"false") representing the success or failure of the action is returned; if it was a failure, the player has been ejected.

## JSON object definitions:

Phase:
- A Phase is a JSON value, specifically a string, one of “placing”, “playing”, and “over
”, representing the possible states a game can be in. More may be added as necessary later depending on the implementation of later components (for example, to represent a game still being filled with players, etc.).

Color:
- A Color is a JSON value, specifically a string representing the color of a player (one of “red
”, “black”, “brown”, or “white”).

Posn: 
- A Posn is a JSON array of 2 natural numbers, where the first represents the row and the second
 the column of a given position in the board (corresponding directly to our board coordinate system).

Board: 
- A Board is an array of arrays of numbers, where each internal array is a row of tiles by number
 of fish, 0 to 5, where 0 represents a hole.

Player:
- A Player is composed of a Color, a natural number counting their fish, and an array of Posn
 objects representing their penguins.
 
State: 
- A State is a JSON object composing a JSON array of Player(s) and a Board, representing the given state of a game. The player order is captured in the array of Player objects, with the first going first, second going second, etc.
