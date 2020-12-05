
import client.FishClientTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import server.FishClientProxyTest;
import server.FishServerTest;
import server.TournamentManagerAdapterTest;
import utils.JsonUtilsTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        FishClientTest.class,
        FishClientProxyTest.class,
        FishServerTest.class,
        TournamentManagerAdapterTest.class,
        JsonUtilsTest.class
})

public class RemoteTestSuite {
}