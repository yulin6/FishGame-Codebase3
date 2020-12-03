package server;

import client.FishClient;
import game.model.Board;
import game.model.GameState;
import game.model.Penguin;
import game.model.Player;
import org.junit.Before;
import org.junit.Test;

import javax.xml.transform.Result;
import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class FishServerTest {


    private FishClient client1;
    private FishClient client2;
    private FishClient client3;
    private FishClient client4;
    private FishClient client5;
    private FishClient client6;
    private FishServer server;
//    private HashSet<Player> players;
//    private Player p1;
//    private Player p2;
//    private Board board;
//    private GameState gameState;

    @Before
    public void init() throws IOException {
        this.server = new FishServer(12345, 300);
        this.client1 = new FishClient("127.0.0.1", 12345);
        this.client2 = new FishClient("127.0.0.1", 12345);
        this.client3 = new FishClient("127.0.0.1", 12345);
        this.client4 = new FishClient("127.0.0.1", 12345);
        this.client5 = new FishClient("127.0.0.1", 12345);
        this.client6 = new FishClient("127.0.0.1", 12345);


//        this.players = new HashSet<>();
//        this.p1 = new Player(17, Penguin.PenguinColor.BLACK);
//        this.p2 = new Player(14, Penguin.PenguinColor.BROWN);
//        this.players.add(p1);
//        this.players.add(p2);
//        this.board = new Board(4, 3, 4);
//        this.gameState = new GameState(this.players, this.board);
    }

    @Test
    public void startSignupPhaseTest() throws IOException {
        ServerSocket serverSocket = server.getServerSocket();

        ArrayList<Socket> connectedClients = server.startSignupPhase(serverSocket, new ArrayList<>());
        assertEquals(6, connectedClients.size());

        }

    @Test
    public void runServerTest() throws IOException {

        List<Thread> threads = new ArrayList<>();
//        server.runServer();
        threads.add(makeServerThread(server));
        threads.add(makeClientThread(client1));
        threads.add(makeClientThread(client2));
        threads.add(makeClientThread(client3));
        threads.add(makeClientThread(client4));
        threads.add(makeClientThread(client5));
        threads.add(makeClientThread(client6));

        for(Thread thread: threads){
            thread.start();
        }

        assertEquals(6, server.getClients().size());
//        TournamentManagerAdapter adapter = server.getAdapter();
//
//        assertEquals(12345, adapter.getWinners().size());
    }

    private Thread makeClientThread(
            FishClient tClient) {
        return new Thread(() -> {
            try {
                tClient.joinTournament();
            } catch (IOException ioe) {
                throw new RuntimeException("IOException caught: " + ioe.getMessage());
            }
        });
    }

    private Thread makeServerThread(
            FishServer tServer) {
        return new Thread(() -> {
            try {
                tServer.runServer();
            } catch (IOException ioe) {
                throw new RuntimeException("IOException caught: " + ioe.getMessage());
            }
        });
    }
    }






