To: Prof. Felleisen

From: Derek Feng, Micheal Reveliotis

Subject: Design of a protocol for a tournament manager

Date: 11/5/2020

### Tournament manager protocol:

Creating a tournament manager
- In order to create a tournament manager, its constructor should be called. A constructor does
 not take a list of players as players to be organized are passed in as lists at the time of
  tournament setup. Construction of a tournament manager must happen before any of its
   functionality is usable.
   
Setting up tournaments with the tournament manager
- To set up a tournament with the manager, setupTournament should be called with a list of
 players to participate in the tournament and a number of rounds to organize the tournament into. 
 Players handed to this tournament manager should not be placed in multiple tournaments at once. 
 setupTournament returns an ID number which must be used to specify tournaments to interact 
 with in the manager. This method is called once per tournament as the first method after manager 
 construction, and must be performed prior to running the tournament. It is called frequently, 
 assuming the tournament manager is handling a significant volume of games. 
  
Running tournaments with the tournament manager
- To run tournaments with the manager, runTournament must be called with a tournament's number
 after it has been set up. runTournament will handle the running of all the games in the
  tournament, completing each round in the tournament before beginning the next, and handles the
   progression of winning players from one game to the next. This should only be called once per
    tournament, ever; but is called repeatedly on the manager, assuming it is handling multiple
     tournaments.
     
Getting the winner of a tournament
- To get the winner of a given tournament, getWinningPlayer must be called on a completed
 tournament (a tournament is completed after it has been run and the running ends). It will
  provide the winning player component, which can be used to award the player as
   appropriate for winning the tournament. This can be called as many times as necessary for a
    given tournament, as long as the tournament has concluded.
    
Getting player statistics from the manager
- To get information about the gameplay statistics for a given player, getPlayerStats should be
 called with the age of the player (the age of the player corresponds to their numbering in the
  order which they signed up at the communication layer, and is unique). It will return an object
   composing statistics such as number of games played, number of games won, average score per
    game, and highest score in a single game. This can be called at any time on the tournament
     manager, for any player and any number of times, so long as the player is registered with the
      tournament manager.
     
Registering observers with the tournament manager
- For an observer to receive updates about the status of a given tournament, subscribeForUpdates
 should be called with the given tournament number to receive broadcasted updates about as well
  as the identification number of the observer. This will register the observer with that
   tournament for information. This can be called once per observer per tournament; registering
    twice for a tournament will not perform additional behavior. This can be called at any time
     before or during the runtime of the tournament; registering for updates on a completed
      tournament will do nothing. This may be called frequently on the manager depending on the
       amount of interested observers.