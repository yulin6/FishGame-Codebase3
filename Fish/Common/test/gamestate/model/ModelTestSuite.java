package gamestate.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BoardPositionTest.class,
        BoardTest.class,
        TileTest.class,
        PixelPositionTest.class,
})

/**
 * Blank class. To be run with JUnit, letting all tests run together.
 */
public class ModelTestSuite {
}