package utils;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class JsonUtilsTest {

    String startMsg = "[\"start\",[true]]";
    String playAsRedMsg = "[\"playing-as\",[\"red\"]]";
    String playAsBlackMsg = "[\"playing-as\",[\"black\"]]";
    String playingWithOneMsg = "[\"playing-with\",[\"black\"]]";
    String playingWithTwoMsg = "[\"playing-with\",[\"black\",\"brown\"]]";
    String playingWithThreeMsg = "[\"playing-with\",[\"black\",\"brown\",\"white\"]]";
    String setUpMsg = "[\"setup\", [{\n" +
            "        \"players\": [\n" +
            "        { \"color\": \"red\",\"score\": 15, \"places\": [[0,0]]},\n" +
            "        {\"color\": \"black\",\"score\": 0,\"places\": [[0,3],[2,0]]}\n" +
            "        ],\n" +
            "        \"board\": [[4,2,1,5],[1],[4,0,5,1],[1,1,1]]\n" +
            "        }]\n" +
            "    ]";
    String takeTurnMsg = "[\"take-turn\", [{\n" +
            "        \"players\": [\n" +
            "        { \"color\": \"red\",\"score\": 15, \"places\": [[0,0]]},\n" +
            "        {\"color\": \"black\",\"score\": 0,\"places\": [[0,3],[2,0]]}\n" +
            "        ],\n" +
            "        \"board\": [[4,2,1,5],[1],[4,0,5,1],[1,1,1]]\n" +
            "        }, [[1,2],[1,2]]]\n" +
            "    ]";
    String endMsg = "[\"end\",[false]]";

    DataOutputStream voidWritable;
    DataInputStream voidReadable;

    DataOutputStream falseWritable;
    DataInputStream falseReadable;

    @Before
    public void init() throws IOException {
        this.voidWritable = new DataOutputStream(new FileOutputStream("Fish/Remote/test/utils/voidWritable.txt"));
        this.voidReadable = new DataInputStream(new FileInputStream("Fish/Remote/test/utils/voidWritable.txt"));

        this.falseWritable = new DataOutputStream(new FileOutputStream("Fish/Remote/test/utils/falseWritable.txt"));
        this.falseReadable = new DataInputStream(new FileInputStream("Fish/Remote/test/utils/falseWritable.txt"));

    }

    @Test
    public void startType() {
        assertEquals("start", JsonUtils.getFishMessageType(startMsg));
    }

    @Test
    public void playingAsType() {
        assertEquals("playing-as", JsonUtils.getFishMessageType(playAsRedMsg));
        assertEquals("playing-as", JsonUtils.getFishMessageType(playAsBlackMsg));
    }

    @Test
    public void playingWithType() {
        assertEquals("playing-with", JsonUtils.getFishMessageType(playingWithOneMsg));
        assertEquals("playing-with", JsonUtils.getFishMessageType(playingWithTwoMsg));
        assertEquals("playing-with", JsonUtils.getFishMessageType(playingWithThreeMsg));
    }

    @Test
    public void setUpType() {
        assertEquals("setup", JsonUtils.getFishMessageType(setUpMsg));
    }

    @Test
    public void takeTurnType() {
        assertEquals("take-turn", JsonUtils.getFishMessageType(takeTurnMsg));
    }

    @Test
    public void endType() {
        assertEquals("end", JsonUtils.getFishMessageType(endMsg));
    }

    @Test
    public void parseStateFromMsg(){
        assertEquals(2, JsonUtils.parseStateFromMessage(takeTurnMsg).getPlayers().size());
        assertEquals(4, JsonUtils.parseStateFromMessage(takeTurnMsg).getBoard().getCols());
    }

    @Test
    public void sendVoidReply() throws IOException {
        JsonUtils.sendVoidReply(voidWritable);
        assertEquals("void", voidReadable.readUTF());
    }

    @Test
    public void sendSkipReply() throws IOException {
        JsonUtils.sendSkipReply(falseWritable);
        assertEquals("false", falseReadable.readUTF());
    }






}