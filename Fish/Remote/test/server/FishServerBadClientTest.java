package server;

import static org.junit.Assert.assertTrue;

import client.FishClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import server.badClients.FishClientBadWinner;
import server.badClients.FishClientTimeout;

public class FishServerBadClientTest {
  private final int PORT = 12345;

  private void runWithBadClients(int nPlayers, ArrayList<FishClient> badClients)
      throws IOException, InterruptedException {
    FishServer server = new FishServer(this.PORT);
    ArrayList<FishClient> clients = new ArrayList<>();
    clients.addAll(badClients);

    for (int i = clients.size(); i < nPlayers; i++) {
      clients.add(new FishClient("127.0.0.1", this.PORT));
    }
    Collections.shuffle(clients);

    ArrayList<Thread> threads = new ArrayList<>();
    for (int i = 0; i < clients.size(); i++) {
      final FishClient fc = clients.get(i);
      final int waitTime = i * 125;
      Thread thread = new Thread(() -> {
        try {
          TimeUnit.MILLISECONDS.sleep(waitTime);
          boolean won = fc.joinTournament();
          System.out.println(won);
        } catch (Exception e) {
          throw new RuntimeException(e.getMessage());
        }
      });
      threads.add(thread);
      thread.start();
    }

    server.runServer();

    for (Thread thread : threads) {
      if (thread.isAlive()) {
        thread.interrupt();
      } else {
        thread.join();
      }
    }
    assertTrue(true);
  }

  @Test
  public void testBadWinnerClient() throws IOException, InterruptedException {
    /*
     * Tests a full tournament of Fish where one client causes an exception on being informed of a
     * tournament's end
     */

    ArrayList<FishClient> badClients = new ArrayList<>();
    badClients.add(new FishClientBadWinner(this.PORT));

    this.runWithBadClients(8, badClients);
  }

  @Test
  public void testBadClientTimeout() throws IOException, InterruptedException {
    ArrayList<FishClient> badClients = new ArrayList<>();
    badClients.add(new FishClientTimeout(this.PORT, true, false));
    badClients.add(new FishClientTimeout(this.PORT, false, true));

    this.runWithBadClients(8, badClients);
  }
}
