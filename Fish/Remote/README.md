## Roadmap

    | Remote
        | src
          | client
          |  | badClients
          |  |  | FishClientBadWinner.java
          |  |  | FishClientException.java
          |  |  | FishClientIllogicalPlayer.java
          |  |  | FishClientNullReturn.java
          |  |  | FishClientTimeout.java
          |  |  | FishClientBadWinner.java
          |  | FishClient.java
          | server
          |  | FishClientProxy.java
          |  | FishServer.java
          |  | TournamentManagerAdapter.java
          | utils
          |  | JsonUtils.java
        | test
        
## Files Purposes 

- badClients/
    - contains a bunch of invalid clients only for testing.
- FishClient.java
    - The client side of code which is used for connecting to the server and respond accordingly for game play.
- FishServer.java
    - A signup server for a Fish game. When 5 - 10 players signed up with 1 minute, a list of FishClientProxy and a TournamentManagerAdapter can be created for running the tournament.
- FishClientProxy.java
    - An IPlayerComponent for remote players. The component serves as a proxy for communicating with the remote client and playing the tournament.  
- TournamentManagerAdapter.java
    - A wrapper of TournamentManager, which is used for creating a TournamentManager and running the tournament for FishClientProxies. It also implements StateChangeListener for receiving all the player colors in each game.
- JsonUtils.java
    - A class that handles all the serialization and deserialization of the JSON messages between clients and server. 
          
## Previous Milestones Modifications      
- Added one method in both Observer.java and StateChangeListener.java for performing certain action when games start.
- Modifications in TournamentManager.java
    - For creating uniform boards (fixed width, height and fish number) in each game.
        - Added a constructor and local fields including isUniformBoard, boardRows, boardCols and fishNum.
        - Modified addNewReferee method for creating a uniform board when isUniformBoard is true.
        - Modified runTournament method for informing loser when they lose in a game.
        - Added lists of cheaters and failures for storing invalid players in the tournament.
        - Modified runGames method for storing cheaters and failures in each game.
- Modifications in Referee.java
    - Added a constructor in Referee for creating a uniform board (fixed width, height and fish number) in the game. 
    - Calls Observer.notifyListenersGameStarted() when a game starts.
- Moved State and TestPlayer from 4/ into a package called state so that we can reuse the code.

