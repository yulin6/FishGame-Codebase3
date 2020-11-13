package game.model;

/**
 * Class representing a player's choice to place their initial penguin.
 * (only valid if the player can place a penguin, the validity of which is checked in GameState.).
 * Contains the board positions of where the penguin will be placed, as well as the player making the placement.
 */
public class Place implements Action{

    private BoardPosition position;
    private Player player;


    /**
     * Constructs a Place action, which composes the position of where the penguin will be placed,
     * as well as the Player making the placement.
     * @param position the position of where the penguin will be placed.
     * @param player the Player making the placement.
     */
    public Place(BoardPosition position, Player player){
        this.position = position;
        this.player = player;
    }
    @Override
    public void perform(GameState g) {
        g.placeAvatar(position, player);
        g.setNextPlayer();
    }

    /**
     * get the position of where the penguin will be placed in the action
     * @return the position of where the penguin will be placed.
     */
    public BoardPosition getPosition() {
        return position;
    }
}