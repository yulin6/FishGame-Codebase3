package xserver;

import com.google.gson.Gson;
import server.FishServer;

import java.io.IOException;

public class XServer {

    public static void main(String[] args) throws IOException {
        int port = parsePortNum(args);
        FishServer server = new FishServer(port);
        server.runServer();
        if(server.isTournamentRan()){
            int winnerSize = server.getAdapter().getWinners().size();
            int cheaterAndFailureSize = server.getAdapter().getCheatersAndFailures().size();
            int[] resultArr = new int[]{winnerSize, cheaterAndFailureSize};
            String result = new Gson().toJson(resultArr);
            System.out.println(result);
        }
        System.exit(0);
    }

    private static int parsePortNum(String[] args){
        int port;

        if(args.length != 1){
            throw new IllegalArgumentException("Invalid number of arguments.");
        }
        else {
            String sizeString = args[0];
            try {
                port = Integer.parseInt(sizeString);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Valid argument should be an integer.");
            }
        }
        return port;
    }
}
