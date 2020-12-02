package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Represents a signup server for a game of Fish. The server listens for connections, waiting for up
 * to 30 seconds for 5-10 players to connect. If fewer than 5 players sign up, it will wait 30
 * additional seconds for connections. If fewer than 5 players are signed up at the end of the
 * signup period, it will shut down without running a tournament. If 10 players sign up, the signup
 * phase will immediately end and a tournament will be run.
 */
public class FishServer {
  private static final int MIN_PLAYERS = 5;
  private static final int MAX_PLAYERS = 10;
  private final int WAIT_MILLIS = 30000;

  private int port;
  private ServerSocket serverSocket;
  private ArrayList<Socket> clients;


  // TODO the class is in progress
  public FishServer(int port) throws IOException {
    this.serverSocket = new ServerSocket(port);
    this.clients = new ArrayList<>();
    this.clients = this.startSignupPhase(this.serverSocket, this.clients);
    if (!this.isSignupComplete(this.clients)) {
      this.startSignupPhase(this.serverSocket, this.clients);
    }
    if (this.isSignupComplete(this.clients)) {
      this.runTournament(this.clients);
    }
    for (Socket s : this.clients) {
      s.close();
    }
  }

  /**
   * Is the provided list of clients within the length bounds for a Fish tournament?
   * @param clients
   * @return
   */
  private boolean isSignupComplete(ArrayList<Socket> clients) {
    return clients.size() >= MIN_PLAYERS && clients.size() <= MAX_PLAYERS;
  }

  /**
   * Loop for up to WAIT_MILLIS, accepting up to 10 socket connections on the given server socket.
   * Starts with the provided list, and returns the new list with all new connections added at the
   * end without mutation.
   *
   * @param ssocket
   * @param clients
   * @return
   * @throws IOException
   */
  private ArrayList<Socket> startSignupPhase(ServerSocket ssocket, ArrayList<Socket> clients)
      throws IOException {
    ArrayList<Socket> outputClients = new ArrayList<>(clients);
    long startSignupTime = System.currentTimeMillis();
    int remainingTime = WAIT_MILLIS;
    while (remainingTime >= 0 && clients.size() < MAX_PLAYERS) {
      ssocket.setSoTimeout(remainingTime);
      try {
        outputClients.add(ssocket.accept());
        remainingTime = remainingTime - (int)(System.currentTimeMillis() - startSignupTime);
      }
      catch(SocketTimeoutException ste) {
        break; // If a timeout has been reached, the full WAIT_MILLIS has passed
      }
    }
    return outputClients;
  }

  private void runTournament(ArrayList<Socket> clients) {
    // TODO: Build tournament manager adapter
    // TODO: Run tournament with adapted tournament manager
    throw new NotImplementedException();
  }

// Main: void -> void
  // - Create empty list of remote connections (FishClients?)
  // - Call signup loop function
  // - If signup loop function returns list with fewer than 5 players, call signup loop function
  //   again with initial list
  // - If connections list has 5-10 players, call run tournament helper function
  // - End all connections then exit

  // Signup loop: List<FishClient> -> List<FishClient>
  // - Start a loop running for 30 seconds or until 10 players have signed up <-- maybe a separate helper?
  //   - If a FishClient connects, create RemotePlayerComponent connected to it and add it to the input list
  // - Return the final list

  // Run tournament: List<FishClient> -> List<FishClient>
  // - Construct tournament manager
  // - Tell tournament manager to run tournament
  // - Return length of winners returned from tournament

}



/*
FishServer accepts all FishClient connections and makes a socket for each
FishServer creates FishClientProxy for each socket
FishServer creates TournamentManagerAdapter with list of FishClientProxies


TournamentManagerAdapter extends TournamentManager and is a listener to all games
TournamentManagerAdapter stores up-to-date GameTree for current game

When TournamentManagerAdapter observes GameStarted:
- send playing-with message to all players
- clear list of actions

When TournamentManagerAdapter observes Action:
- store action, list of actions sent to each player

TournamentManagerAdapter will send all Actions not already sent to a player when takeTurn is called
on that player
 */