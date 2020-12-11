package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


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

  private final int signupWaitMillis;
  private final int playerWaitMillis;
  private ServerSocket serverSocket;
  private ArrayList<Socket> clients;
  private List<FishClientProxy> proxies;
  private TournamentManagerAdapter adapter;
  private boolean tournamentRan = false;

  /**
   * The constructor of FishServer takes in a port number for building a ServerSocket.
   * @param port a port number
   * @throws IOException I/O exception thrown by ServerSocket
   */
  public FishServer(int port) throws IOException {
    this(port, 30000);
  }

  /**
   * The constructor of FishServer takes in a port number for building a ServerSocket, and an int for defining
   * the wait period for each sign-up phase.
   * @param port a port number
   * @param wait_millis int for defining the wait period for each sign-up phase.
   * @throws IOException I/O exception thrown by ServerSocket
   */
  public FishServer(int port, int wait_millis) throws IOException {
    this.serverSocket = new ServerSocket(port);
    this.clients = new ArrayList<>();
    this.proxies = new ArrayList<>();
    this.signupWaitMillis = wait_millis;
    this.playerWaitMillis = this.signupWaitMillis / 10;
  }

  /**
   * The method for running the server, signing up remote players and running tournament.
   * @throws IOException I/O exception thrown by ServerSocket
   */
  public void runServer() throws IOException {
    this.clients = this.startSignupPhase();
    if (!this.isSignupComplete()) {
      this.clients.addAll(this.startSignupPhase());
    }
    this.serverSocket.close();
    if (this.isSignupComplete()) {
      this.runTournament();
      this.tournamentRan = true;
    }
    for (Socket s : this.clients) {
      s.close();
    }
  }

  /**
   * Is the provided list of clients within the length bounds for a Fish tournament?
   * @return a boolean represents whether the sign-up phase is over
   */
  private boolean isSignupComplete() {
    return this.clients.size() >= MIN_PLAYERS && this.clients.size() <= MAX_PLAYERS;
  }

  /**
   * Loop for up to WAIT_MILLIS, accepting up to 10 socket connections on the given server socket.
   * Starts with the provided list, and returns the new list with all new connections added at the
   * end without mutation.
   *
   * @return a list of client Sockets
   * @throws IOException
   */
  public ArrayList<Socket> startSignupPhase() throws IOException {

    ArrayList<Socket> outputClients = new ArrayList<>();
    Instant start = Instant.now();
    long remainingMillis = this.signupWaitMillis;

    while (remainingMillis >= 0 && outputClients.size() + this.clients.size() < MAX_PLAYERS) {
      this.serverSocket.setSoTimeout((int) remainingMillis);
      try {
        Socket clientSocket = this.serverSocket.accept();
        boolean acceptClient = this.expectClientSentName(
            new DataInputStream(clientSocket.getInputStream()));

        if (acceptClient) {
          outputClients.add(clientSocket);
        } else {
          clientSocket.close();
        }

        remainingMillis = this.signupWaitMillis - Duration.between(start, Instant.now()).toMillis();
      }
      catch(SocketTimeoutException ste) {
        break; // If a timeout has been reached, the full WAIT_MILLIS has passed
      }
    }
    return outputClients;
  }

  /**
   * The method for creating a TournamentManagerAdapter and running the tournament in it.
   * @throws IOException I/O exception thrown by ServerSocket
   */
  public void runTournament() throws IOException {
    this.proxies = new ArrayList<>();
    for(int i = 0; i < this.clients.size(); ++i){
      FishClientProxy proxy = new FishClientProxy(this.clients.get(i), i);
      this.proxies.add(proxy);
    }
    this.adapter = new TournamentManagerAdapter(proxies);
    this.adapter.runTournament();
  }

  /**
   * check if a client provided a name within playerWaitMillis.
   * @param inputStream the DataInputStream
   * @return a boolean represents whether a client provided a name within playerWaitMillis
   * @throws IOException I/O exception thrown by reading DataInputStream
   */
  public boolean expectClientSentName(DataInputStream inputStream) throws IOException {
    Long startTime = System.currentTimeMillis();
    while (inputStream.available() <= 0) {
      if ((System.currentTimeMillis() - startTime) >= this.playerWaitMillis) {
        return false;
      }
    }
    String name = inputStream.readUTF();
    return name.length() >= 1 && name.length() <= 12; //Only sign up those who provided a name
  }

  /**
   * getter method of serverSocket.
   * @return the local serverSocket.
   */
  public ServerSocket getServerSocket() {
    return serverSocket;
  }

  /**
   * getter method of connected clients list.
   * @return the list of connected clients.
   */
  public ArrayList<Socket> getClients() {
    return clients;
  }

  /**
   * getter method of FishClientProxy list.
   * @return the list of player components for remote players.
   */
  public List<FishClientProxy> getProxies() {
    return proxies;
  }

  /**
   * getter method of the TournamentManagerAdapter.
   * @return the TournamentManagerAdapter.
   */
  public TournamentManagerAdapter getAdapter() {
    return adapter;
  }


  public boolean isTournamentRan() {
    return tournamentRan;
  }
}