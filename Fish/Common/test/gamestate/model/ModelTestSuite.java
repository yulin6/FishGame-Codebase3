package gamestate.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BoardPositionTest.class,
        BoardTest.class,
        TileTest.class,
        PixelPositionTest.class,
        PenguinTest.class,
        HoleTest.class
})

public class ModelTestSuite {
}