import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import game.model.ModelTestSuite;
import player.PlayerComponentTestSuite;
import referee.AdminTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ModelTestSuite.class,
        PlayerComponentTestSuite.class,
        AdminTestSuite.class,
        RemoteTestSuite.class
})

public class FullTestSuite {
}