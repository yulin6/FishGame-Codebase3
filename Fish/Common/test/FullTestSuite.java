import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import player.PlayerTestSuite;
import game.model.ModelTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ModelTestSuite.class,
        PlayerTestSuite.class,
        AdminTestSuite.class
})

public class FullTestSuite {
}