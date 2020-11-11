package game.model;

public class Place implements Action{

    BoardPosition position;
    Player player;


    public Place(BoardPosition position, Player player){
        this.position = position;
        this.player = player;
    }
    @Override
    public void perform(GameState g) {
        g.placeAvatar(position, player);
        g.setNextPlayer();
    }
}
