
import game.controller.FishController;
import game.model.BoardPosition;
import game.model.Penguin;
import game.model.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Xgui program which takes in a number which stands for the number of players is participating in the game, a valid
 * number is ranged from 2 to 4 inclusively. Once the number is verified to be valid, the program will pop up a
 * visualized game on a six by six board, and it will run continuously until no more players can move.
 */
public class Xgui {

    /**
     * The main method of the xgui program, which will visualize a game on a GUI. The number of players is determined
     * by the input argument, and a valid input should be a number ranged from 2 to 4 inclusively. The program will
     * build a six by six board with four holes and run the game until no more players can move.
     * @param args the input argument list.
     */
    public static void main(String[] args) {

        int playerSize = parsePlayerSize(args);

        ArrayList<BoardPosition> holes = new ArrayList<>();
        holes.add(new BoardPosition(0, 0));
        holes.add(new BoardPosition(3, 1));
        holes.add(new BoardPosition(2, 1));
        holes.add(new BoardPosition(1, 2));

        Player p1 = new Player(17, Penguin.PenguinColor.BLACK);
        Player p2 = new Player(14, Penguin.PenguinColor.BROWN);
        Player p3 = new Player(10, Penguin.PenguinColor.RED);
        Player p4 = new Player(21, Penguin.PenguinColor.WHITE);

        List<Player> players = new ArrayList<>(Arrays.asList(p1, p2, p3, p4));

        HashSet<Player> playersToAdd = new HashSet<>();
        for (int i = 0; i < playerSize; ++i){
            playersToAdd.add(players.get(i));
        }

        FishController fc = new FishController(6, 6, playersToAdd, holes, 3);

        fc.runGame();
    }


    /**
     * check if the size of input arguments is one, the only valid input is a number ranged between 2 to 4 exclusively.
     * @param args input argument list
     * @return a number of players will be used for generating a game.
     */
    private static int parsePlayerSize(String[] args){
        int size;

        System.out.println(args);
        if(args.length != 1){
            throw new IllegalArgumentException("Invalid number of arguments.");
        }
        else {
           String sizeString = args[0];
            try {
                size = Integer.parseInt(sizeString);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid argument.");
            }
            if(size < 2 || size > 4) {
                throw new IllegalArgumentException("Valid number of players should be 2 to 4.");
            }
        }
        return size;
    }

}
