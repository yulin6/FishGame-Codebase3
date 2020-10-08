To: Prof. Felleisen
From: Derek Feng, Micheal Reveliotis
Subject: Fish game state data representation and external interface
Date: 10/8/20


Data representation:

In order for players and referees in our Fish game to function properly, we will require a game
state that acts as a data representation for them to access and manipulate as needed through an
external interface. This game state contains the game board, which is made up of multiple tiles,
each holding information about itself. The game state also contains information about the players
of the Fish game, as well as their avatars (penguins). 


The representation of the game board is held in the Board object in the current implementation, 
which stores the number of rows and columns that comprise the board, the individual tiles of the 
board in a 2D array of Tile objects, and the Penguins on the board. The Board object also contains a 
FishController, which represents the controller aspect of the game state from the MVC perspective. The 
FishController is currently very basic, and is used to handle simple board rendering by generating a board 
with a fixed number of rows and columns as well as fixed hole positions, though the number of fish on each 
tile is randomized. Boards also contain a list of Penguin objects that are on the board.


Each Tile object contains the number of fish, a TileStatus Enum that represents what is occupying the tile 
(a Penguin from a specific Player, nothing, or the Tile is a hole), and the position of the Tile.


Penguin objects, as currently implemented, store a color (as an enum) as well as a board position, 
both of which are used when rendering the penguins onto the board. The color of the Penguin determines 
the player to which it belongs.


The Player objects in the game state, while not implemented yet, will hold information such as a list 
of their Penguins, which will primarily keep track of each of their positions on the board, the number 
of fish collected, the color of their Penguins, and their age (the referee needs this to determine the turn order).


External interface:

The external interface for the game state should enable players to plan and make moves, and for referees to 
check the legality of players’ moves and otherwise manage the game. An external interface for the game state 
includes methods to check the status of the board - dimensions of the whole board, number of fish on a given 
tile, which tiles are holes, and where players are located. Referees will be able to manage the board by having 
the capability to remove tiles from the board, as well as validate player moves by checking the positions reachable 
from a given position. A method to render the board is also available for any individual who wants a 
visual representation of the board.