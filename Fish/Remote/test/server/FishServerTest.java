package server;

import static org.junit.Assert.assertEquals;

import client.FishClient;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import utils.JsonUtils;

public class FishServerTest {
  private final String LOCALHOST = "127.0.0.1";
  private final int PORT = 44444;

  /*
  @Test
  public void startSignupPhaseTest() throws IOException {
    FishServer server = new FishServer(44444);
    ServerSocket serverSocket = server.getServerSocket();
    Thread serverThread = new Thread(() -> {
      try {
        ArrayList<Socket> clientSockets = server.startSignupPhase(serverSocket, new ArrayList<>(), 2000);
        assertEquals(6, clientSockets.size());
        for (Socket s : clientSockets) {
          JsonUtils.sendEndMessage(new DataOutputStream(s.getOutputStream()), false);
        }
      } catch (IOException ioe) {
        throw new RuntimeException("Server IOException caught: " + ioe.getMessage());
      }
    });

    serverThread.start();

    ArrayList<Thread> clientThreads = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      Thread clientThread = new Thread(() -> {
        try {
          try {
            TimeUnit.MILLISECONDS.sleep(250);
          } catch (InterruptedException ie) {
            throw new RuntimeException(ie.getMessage());
          }
          new FishClient("127.0.0.1", 44444).joinTournament();
        } catch (IOException ioe) {
          throw new RuntimeException(ioe.getMessage());
        }
      });
      clientThread.start();
      clientThreads.add(clientThread);
    }

    try {
      serverThread.join();
      for (Thread clientThread : clientThreads) {
        clientThread.join();
      }
    } catch(InterruptedException ie) {
      throw new RuntimeException(ie.getMessage());
    }
  }
  */

  @Test
  public void startSignupPhaseTest() throws IOException, InterruptedException {
    FishServer server = new FishServer(PORT);
    ServerSocket serverSocket = server.getServerSocket();

    Thread serverThread = new Thread(() -> {
      try {
        ArrayList<Socket> clientSockets =
            server.startSignupPhase(serverSocket, new ArrayList<>(), 2000);
        assertEquals(6, clientSockets.size());
        for (Socket s : clientSockets) {
          JsonUtils.sendEndMessage(new DataOutputStream(s.getOutputStream()), false);
        }
      } catch (IOException ioe) {
        throw new RuntimeException("Server IOException caught: " + ioe.getMessage());
      }
    });

    serverThread.start();

    ArrayList<Socket> sockets = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      sockets.add(new Socket(LOCALHOST, PORT));
    }

    serverThread.join();
    for (Socket s : sockets) {
      s.close();
    }
  }

  @Test
  public void runServerTest() throws IOException, InterruptedException {
    FishServer server = new FishServer(PORT, 2000);
    ServerSocket serverSocket = server.getServerSocket();

    Thread serverThread = new Thread(() -> {
      try {
        server.runServer();
        assertEquals(6, server.getProxies().size());
      } catch (IOException ioe) {
        throw new RuntimeException("Server IOException caught: " + ioe.getMessage());
      }
    });

    serverThread.start();


    ArrayList<Thread> clientThreads = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      Thread clientThread = new Thread(() -> {
        try {
          new FishClient("127.0.0.1", 44444).joinTournament();
        } catch (IOException ioe) {
          throw new RuntimeException(ioe.getMessage());
        }
      });
      clientThread.start();
      clientThreads.add(clientThread);
    }

    try {
      serverThread.join();
      for (Thread clientThread : clientThreads) {
        clientThread.join();
      }
    } catch(InterruptedException ie) {
      throw new RuntimeException(ie.getMessage());
    }

    //TODO how to make test stop?
    ArrayList<Socket> connectedClients = server.getClients();
    for(Socket socket: connectedClients){
      socket.close();
    }
    server.getServerSocket().close();

//    assertEquals(6, server.getProxies().size());

  }
}






