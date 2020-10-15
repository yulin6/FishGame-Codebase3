package game.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BoardPositionTest.class,
        BoardTest.class,
        TileTest.class,
        PixelPositionTest.class,
        PenguinTest.class,
        HoleTest.class,
        PlayerTest.class,
        GameStateTest.class
})

public class ModelTestSuite {
}