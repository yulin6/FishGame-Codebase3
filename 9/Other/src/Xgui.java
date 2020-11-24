import game.controller.FishController;
import game.model.BoardPosition;
import game.model.Penguin;
import game.model.Player;

import java.util.ArrayList;
import java.util.HashSet;

public class Xgui {

    public static void main(String [] args) {

        ArrayList<BoardPosition> holes = new ArrayList<>();

        holes.add(new BoardPosition(0, 0));
        holes.add(new BoardPosition(3, 1));
        holes.add(new BoardPosition(2, 1));
        holes.add(new BoardPosition(1, 2));

        Player p1 = new Player(17, Penguin.PenguinColor.BLACK);
        Player p2 = new Player(14, Penguin.PenguinColor.BROWN);
        Player p3 = new Player(10, Penguin.PenguinColor.RED);
        Player p4 = new Player(21, Penguin.PenguinColor.WHITE);

        HashSet<Player> players = new HashSet<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        FishController fc = new FishController(8, 8, players, holes, 3);

        fc.runGame();

    }

    /**
     * Returns an integer from the range, start and end inclusive.
     * @param min minimum value
     * @param max maximum value
     * @return integer in the range
     */
    private static int randomInt(int min, int max) {
        int range = max - min + 1;
        return (int) (Math.random() * range) + min;
    }

}
