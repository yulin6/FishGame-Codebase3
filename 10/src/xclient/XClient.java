package xclient;

import client.FishClient;
import client.badClients.FishClientIllogicalPlayer;
import client.badClients.FishClientTimeout;
import java.io.IOException;
import java.util.ArrayList;

public class XClient {
    public static void main(String[] args) throws InterruptedException {
        int[] playersAndPortNum = parsePlayersAndPortNum(args);
        int playerNum = playersAndPortNum[0];
        int port = playersAndPortNum[1];
        String ip = "127.0.0.1";
        if(args.length == 3) {
            ip = args[2];
        }

        ArrayList<Thread> clientThreads = new ArrayList<>();
        for(int i = 0; i < playerNum; ++i){
            final String finalIp = ip;
//            final int j = i;
            Thread clientThread = new Thread(() -> {
                try {
                    /*   Testing block with cheating and failing players.
                    switch (j) {
                        case 0:
                            new FishClientTimeout(port, false, false).joinTournament();
                            break;
                        case 1:
                            new FishClientIllogicalPlayer(port).joinTournament();
                            break;
                        default:
                            new FishClient(finalIp, port).joinTournament();
                    }*/
                    new FishClient(finalIp, port).joinTournament();
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe.getMessage());
                }
            });
            clientThread.start();
            clientThreads.add(clientThread);
        }

        for (Thread clientThread : clientThreads) {
            clientThread.join();
        }

    }

    private static int[] parsePlayersAndPortNum(String[] args){
        if(args.length < 2 || args.length > 3){
            throw new IllegalArgumentException("Invalid number of arguments.");
        }
        int playerNum;
        int port;
        try{
            playerNum = Integer.parseInt(args[0]);
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("First and second argument should be integers.");
        }
        return new int[] {playerNum, port};
    }
}
