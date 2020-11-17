import player.IPlayerComponent;
import referee.Referee;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TournamentManager {


//pseudocode

//    List<IPlayerComponent> playerComponents;
//    IPlayerComponent leftOutPlayer;
//    List<GameObserver> observers;
//    List<Referee> referees;
//    public TournamentManager(List<IPlayerComponent> playerComponents, List<GameObserver> observers){
//        this.playerComponents = playerComponents;
//        this.observers = observers;
//        this.referees = generateGames();
//    }
//
//
//
//    private List<Referee> generateGames(){
//        //Requirement:
//        // starts by assigning them to games with the maximal number of participants permitted in ascending order of age.
//        // Question: how do we get their ages?
//
//        int counter = 0;
//        List<Referee> referees = new ArrayList<>();
//        List<IPlayerComponent> tmp = new ArrayList<>();
//        for(IPlayerComponent pc: playerComponents){
//            tmp.add(pc);
//            if(tmp.size() == 4){
//                Referee referee = new Referee(tmp, ..., ...);
//                referees.add(referee);
//                tmp.clear();
//                counter = 0;
//            } else {
//                tmp.add(pc);
//                ++counter;
//            }
//        }
//
//        if (tmp.size() == 1){
//            leftOutPlayer = tmp.get(0);
//        } else {
//            for(IPlayerComponent pc: tmp){
//                Referee referee = new Referee(pc, ..., ...);
//                referees.add(referee);
//            }
//        }
//        return referees;
//    }
//
//
//
//    public List<Referee> runTournamentRound(){
//        // if ( 1> remaining players  size <5 ), this is the final game.
//        // if ( remaining players  size == 1 ), here is the winner
//
//        //1. referees run games till the end. (including informing players game is start)
//        //2. get winning players
//        //3. add leftOutPlayer if not null
//        //4. generateGame()
//    }
//
//
//
//    public void informPlayers() {
//        //
//    }
//
//
//
//    public void informObservers(){
//        //mutate fields in GameObserver
//    }
//
//


}




