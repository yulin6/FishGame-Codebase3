import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import game.model.ModelTestSuite;
import player.PlayerTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ModelTestSuite.class,
        PlayerTestSuite.class,
        AdminTestSuite.class
})

public class FullTestSuite {
}