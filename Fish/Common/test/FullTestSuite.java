import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import game.model.ModelTestSuite;
import player.PlayerComponentTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ModelTestSuite.class,
        PlayerComponentTestSuite.class,
        AdminTestSuite.class
})

public class FullTestSuite {
}