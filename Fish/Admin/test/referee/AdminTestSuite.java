package referee;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tmanager.TournamentManagerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RefereeTest.class,
        TournamentManagerTest.class

})

public class AdminTestSuite {
}
