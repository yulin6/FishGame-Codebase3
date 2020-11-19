package tmanager;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import player.BadWinnerPlayerComponent;
import player.ExceptionPlayerComponent;
import player.IPlayerComponent;
import player.IllogicalPlayerComponent;
import player.InfiniteLoopPlayerComponent;
import player.NullReturnPlayerComponent;
import player.PlayerComponent;

import static org.junit.Assert.*;

/**
 * Class handling unit tests for the TournamentManager class, which handles organization of full
 * tournaments given a list of external player components.
 */
public class TournamentManagerTest {
  PlayerComponent pc1, pc2, pc3, pc4, pc5, pc6, pc7, pc8;
  int seed = 83;

  IllogicalPlayerComponent illogical;
  InfiniteLoopPlayerComponent looperInGetAge, looperInJoinTournament;
  NullReturnPlayerComponent nuller;
  ExceptionPlayerComponent throwerInGetAge, throwerInJoinTournament;
  BadWinnerPlayerComponent badwinner1, badwinner2, badwinner3, badwinner4;

  TournamentManager notEnoughPlayersExceptionTm, singleGameTm, badWinnersTm,
          cleanMultiRoundTm, badMultiRoundTm, noValidPlayersTm;

  @Before
  public void setUp() {
    pc1 = new PlayerComponent(1, seed);
    pc2 = new PlayerComponent(2, seed);
    pc3 = new PlayerComponent(3, seed);
    pc4 = new PlayerComponent(4, seed);
    pc5 = new PlayerComponent(5, seed);
    pc6 = new PlayerComponent(6, seed);
    pc7 = new PlayerComponent(7, seed);
    pc8 = new PlayerComponent(8, seed);
    ArrayList<IPlayerComponent> singleGameComponents = new ArrayList<>(Arrays.asList(pc1, pc2,
            pc3, pc4));
    ArrayList<IPlayerComponent> allPlayersList = new ArrayList<>(Arrays.asList(pc1, pc2, pc3,
            pc4, pc5, pc6, pc7, pc8));

    badwinner1 = new BadWinnerPlayerComponent(1);
    badwinner2 = new BadWinnerPlayerComponent(2);
    badwinner3 = new BadWinnerPlayerComponent(3);
    badwinner4 = new BadWinnerPlayerComponent(4);
    ArrayList<IPlayerComponent> badWinners = new ArrayList<>(Arrays.asList(badwinner1, badwinner2,
            badwinner3, badwinner4));

    illogical = new IllogicalPlayerComponent();
    looperInGetAge = new InfiniteLoopPlayerComponent(true,false);
    looperInJoinTournament = new InfiniteLoopPlayerComponent(false, true);
    nuller = new NullReturnPlayerComponent();
    throwerInGetAge = new ExceptionPlayerComponent(true, false);
    throwerInJoinTournament = new ExceptionPlayerComponent(false, true);

    singleGameTm = new TournamentManager(singleGameComponents);
    badWinnersTm = new TournamentManager(badWinners);
    cleanMultiRoundTm = new TournamentManager(allPlayersList);
    badMultiRoundTm = new TournamentManager(Arrays.asList(pc1, pc2, pc3, pc4, badwinner1,
            badwinner2, nuller, illogical, badwinner3));
    noValidPlayersTm = new TournamentManager(Arrays.asList(badwinner1, badwinner2, nuller,
            illogical, looperInGetAge, looperInJoinTournament, throwerInGetAge,
            throwerInJoinTournament));
  }

  @Test (expected = IllegalArgumentException.class)
  public void notEnoughPlayersException() {
    ArrayList<IPlayerComponent> badList = new ArrayList<>(Arrays.asList(pc1));
    notEnoughPlayersExceptionTm = new TournamentManager(badList);
  }

  @Test
  public void runCleanSingleGameTournament() {
    singleGameTm.runTournament();
    List<IPlayerComponent> winners = singleGameTm.getWinners();
    assertTrue(winners.size() > 0);
  }

  @Test
  public void runCleanMultiRoundTournament() {
    cleanMultiRoundTm.runTournament();
    List<IPlayerComponent> winners = cleanMultiRoundTm.getWinners();
    assertTrue(winners.size() > 0);
  }

  @Test
  public void runFailingCheatingMultiRoundTournament() {
    badMultiRoundTm.runTournament();
    List<IPlayerComponent> winners = badMultiRoundTm.getWinners();
    // Winners may be size 0 if bad-winning players eliminate properly-functioning ones.
    assertTrue(Collections.disjoint(winners, Arrays.asList(badwinner1, badwinner2, nuller,
            illogical, badwinner3)));
  }

  @Test
  public void noGoodPlayersGame() {
    System.out.println("Entering a timeout-based test, a pause will occur.");
    noValidPlayersTm.runTournament();
    List<IPlayerComponent> winners = noValidPlayersTm.getWinners();
    assertTrue(winners.isEmpty());
  }

  @Test
  public void runTournamentRound() {

  }

  @Test
  public void getWinners() {

  }
}