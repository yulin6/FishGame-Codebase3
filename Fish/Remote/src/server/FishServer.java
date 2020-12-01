package server;

import client.FishClient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a signup server for a game of Fish.
 * The server listens for connections, waiting for up to 30 seconds for 5-10 players to connect.
 * If fewer than 5 players sign up, it will wait 30 additional seconds for connections.
 * If fewer than 5 players are signed up at the end of the signup period, it will shut down without
 * running a tournament.
 * If 10 players sign up, the signup phase will immediately end and a tournament will be run.
 */
public class FishServer {
    private static final int MIN_PLAYERS = 5;
    private static final int MAX_PLAYERS = 10;
    private final int WAIT_TIME = 30000;

    private int port;
    private ServerSocket serverSocket;
    private List<Thread> clients;


    // the class is in progress
    public FishServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.clients = new ArrayList<Thread>();
    }

    public void startServer() throws IOException {
        this.signUp();
    }

    private void signUp() throws IOException {

        Socket socket = null;
        this.serverSocket.setSoTimeout(WAIT_TIME);
        socket = this.serverSocket.accept();
        // new thread for a client
        Thread t = new FishClient(socket);
        t.start();
        clients.add(t);

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