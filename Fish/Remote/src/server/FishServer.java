package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import utils.JsonUtils;


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

  private final int waitMillis;
  private ServerSocket serverSocket;
  private ArrayList<Socket> clients;
  private List<FishClientProxy> proxies;
  private TournamentManagerAdapter adapter;

  /**
   * TODO
   */
  public FishServer(int port) throws IOException {
    this.serverSocket = new ServerSocket(port);
    this.clients = new ArrayList<>();
    this.proxies = new ArrayList<>();
    this.waitMillis = 30000;
  }

  public FishServer(int port, int wait_millis) throws IOException {
    this.serverSocket = new ServerSocket(port);
    this.clients = new ArrayList<>();
    this.proxies = new ArrayList<>();
    this.waitMillis = wait_millis;
  }

  /**
   * TODO
   * @throws IOException
   */
  public void runServer() throws IOException {
    this.clients = this.startSignupPhase(this.serverSocket, this.clients, this.waitMillis);
    if (!this.isSignupComplete(this.clients)) {
      this.startSignupPhase(this.serverSocket, this.clients, this.waitMillis);
    }
    this.serverSocket.close();
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
  public ArrayList<Socket> startSignupPhase(
      ServerSocket ssocket,
      ArrayList<Socket> clients,
      int waitMillis
  ) throws IOException {
    ArrayList<Socket> outputClients = new ArrayList<>(clients);
    long startSignupTime = System.currentTimeMillis();
    int remainingTime = waitMillis;
    while (remainingTime >= 0 && clients.size() < MAX_PLAYERS) {
      ssocket.setSoTimeout(remainingTime);
      try {
        Socket clientSocket = ssocket.accept();
        DataInputStream readable = new DataInputStream(clientSocket.getInputStream());
        String name = readable.readUTF();
        if(name.length() >= 1 && name.length() <= 12) { //Only sign up those who provided a name
          outputClients.add(clientSocket);
          remainingTime = remainingTime - (int) (System.currentTimeMillis() - startSignupTime);
        }
      }
      catch(SocketTimeoutException ste) {
        break; // If a timeout has been reached, the full WAIT_MILLIS has passed
      }
    }
    return outputClients;
  }

  /**
   * TODO
   * @param clients
   * @throws IOException
   */
  public void runTournament(ArrayList<Socket> clients) throws IOException {
    this.proxies = new ArrayList<>();
    for(int i = 0; i < clients.size(); ++i){
      FishClientProxy proxy = new FishClientProxy(clients.get(i), i);
      this.proxies.add(proxy);
    }
    this.adapter = new TournamentManagerAdapter(proxies);
    this.adapter.runTournament();
  }

  /**
   * TODO
   * @return
   */
  public ServerSocket getServerSocket() {
    return serverSocket;
  }

  /**
   * TODO
   * @return
   */
  public ArrayList<Socket> getClients() {
    return clients;
  }


  /**
   * TODO
   * @return
   */
  public List<FishClientProxy> getProxies() {
    return proxies;
  }

  /**
   * TODO
   * @return
   */
  public TournamentManagerAdapter getAdapter() {
    return adapter;
  }
}