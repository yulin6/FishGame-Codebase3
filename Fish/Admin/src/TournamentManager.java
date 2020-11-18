import player.IPlayerComponent;
import referee.Referee;

import java.util.ArrayList;
import java.util.List;

public class TournamentManager {

    private List<IPlayerComponent> players;
    private List<Referee> referees;
    private TournamentPhase phase;
    private final int multiple;

    private final int MAX_PLAYERS = 4;
    private final int MIN_PLAYERS = 2;


    public TournamentManager(List<IPlayerComponent> players, int multiple) {
        if (players.size() < MIN_PLAYERS) {
            throw new IllegalArgumentException("Not enough players to form a tournament.");
        }
        this.players = players;
        informPlayers(InformType.START);
        this.referees = generateGames();
        this.multiple = multiple;
        this.phase = TournamentPhase.RUNNING;
    }

    private List<Referee> generateGames() {
        // Requirement:
        // "starts by assigning them to games with the maximal number of participants permitted in ascending order of age."
        // Question: how do we get their ages?

        List<Referee> referees = new ArrayList<>();
        List<IPlayerComponent> oneGamePlayers = new ArrayList<>();

        int playerNumPerGame = findPlayerNumPerGame();

        for (IPlayerComponent pc : players) {
            oneGamePlayers.add(pc);
            if (oneGamePlayers.size() == playerNumPerGame) {
                Referee referee = new Referee(oneGamePlayers, oneGamePlayers.size() * multiple, oneGamePlayers.size() * multiple);
                referees.add(referee);
                oneGamePlayers.clear();
            }
        }

        if (oneGamePlayers.size() >= MIN_PLAYERS) {
            int penguinsPerPlayer = oneGamePlayers.size() == 2 ? 4 : 3;
            Referee referee = new Referee(oneGamePlayers, penguinsPerPlayer * multiple, penguinsPerPlayer * multiple);
            referees.add(referee);
        }

        return referees;
    }

    private int findPlayerNumPerGame() {
        int playersSize = players.size();
        int maxSize = MAX_PLAYERS;
        int remainder = playersSize % maxSize;
        while (maxSize >= MIN_PLAYERS && maxSize <= MAX_PLAYERS) {
            if (remainder == MIN_PLAYERS) {
                --maxSize;
                remainder = playersSize % maxSize;
            } else {
                return maxSize;
            }
        }

        throw new IllegalArgumentException("Should not be here");
    }


    public void runTournament() {
//        if (phase == TournamentPhase.READY) {
//            phase = TournamentPhase.RUNNING;
//        }
        while (phase == TournamentPhase.RUNNING) {
            runTournamentRound();
        }
    }

    public void runTournamentRound() {
        if (phase == TournamentPhase.RUNNING) {
            List<IPlayerComponent> winners = runGames();
            phase = isTournamentEnd(winners) ? TournamentPhase.END : TournamentPhase.RUNNING;
            players = winners;
            if (phase == TournamentPhase.RUNNING) {
                referees = generateGames();
            } else {
                informPlayers(InformType.END);
            }
        }
    }


    private List<IPlayerComponent> runGames() {
        List<IPlayerComponent> winners = new ArrayList<>();
        for (Referee referee : referees) {
            referee.notifyGameStart();
            referee.runGame();
            referee.notifyGameEnd();
            winners.addAll(referee.getWinners());
        }
        return winners;
    }

    private boolean isTournamentEnd(List<IPlayerComponent> winners) {
        return players.containsAll(winners) || winners.size() < MIN_PLAYERS;
    }

    public List<IPlayerComponent> getWinners() {
        if (phase == TournamentPhase.END) {
            informPlayers(InformType.END);
            return players;
        } else {
            throw new IllegalArgumentException("Tournament has not end."); //should it throw?
        }
    }

    private void informPlayers(InformType type) {
        if (type == InformType.START) {
            for (IPlayerComponent player : players) {
                //remove the players who failed to communicate
            }
        } else {
            for (IPlayerComponent player : players) {
                //remove the players who failed to communicate
            }
        }
    }

    public enum TournamentPhase {
        //        READY,
        RUNNING,
        END
    }

    private enum InformType {
        START,
        END
    }

}




