package utils;

import game.model.*;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class JsonUtilsTest {

    String startMsg = "[\"start\",[true]]";
    String playAsRedMsg = "[\"playing-as\",[\"red\"]]";
    String playAsBlackMsg = "[\"playing-as\",[\"black\"]]";
    String playAsWhiteMsg = "[\"playing-as\",[\"white\"]]";
    String playAsBrownMsg = "[\"playing-as\",[\"brown\"]]";
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

    DataOutputStream writable;
    DataInputStream readable;


    @Before
    public void init() throws IOException {
        this.writable = new DataOutputStream(new FileOutputStream("Fish/Remote/test/writable/writable.txt"));
        this.readable = new DataInputStream(new FileInputStream("Fish/Remote/test/writable/writable.txt"));
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
    public void parseRedFromMsg() {
        assertEquals(Penguin.PenguinColor.RED, JsonUtils.parseColorFromPlayingAsMessage(playAsRedMsg));
    }

    @Test
    public void parseBlackFromMsg() {
        assertEquals(Penguin.PenguinColor.BLACK, JsonUtils.parseColorFromPlayingAsMessage(playAsBlackMsg));
    }

    @Test
    public void parseWhiteFromMsg() {
        assertEquals(Penguin.PenguinColor.WHITE, JsonUtils.parseColorFromPlayingAsMessage(playAsWhiteMsg));
    }

    @Test
    public void parseBrownFromMsg() {
        assertEquals(Penguin.PenguinColor.BROWN, JsonUtils.parseColorFromPlayingAsMessage(playAsBrownMsg));
    }

    @Test
    public void parseStateFromMsg() {
        assertEquals(2, JsonUtils.parseStateFromMessage(takeTurnMsg).getPlayers().size());
        assertEquals(4, JsonUtils.parseStateFromMessage(takeTurnMsg).getBoard().getCols());
    }

    @Test
    public void sendVoidReply() throws IOException {
        JsonUtils.sendVoidReply(writable);
        assertEquals("void", readable.readUTF());
    }

    @Test
    public void sendSkipReply() throws IOException {
        JsonUtils.sendSkipReply(writable);
        assertEquals("false", readable.readUTF());
    }

    @Test
    public void sendPlacementReply() throws IOException {
        JsonUtils.sendPlacementReply(writable, new BoardPosition(1, 2));
        assertEquals("[1,2]", readable.readUTF());
    }

    @Test
    public void sendMoveReply() throws IOException {
        JsonUtils.sendMoveReply(writable, new BoardPosition(1, 2), new BoardPosition(1, 2));
        assertEquals("[[1,2],[1,2]]", readable.readUTF());
    }

    @Test
    public void sendStartMessage() throws IOException {
        JsonUtils.sendStartMessage(writable);
        assertEquals("[\"start\",[true]]", readable.readUTF());
    }

    @Test
    public void sendPlayingAsMessage() throws IOException {
        JsonUtils.sendPlayingAsMessage(writable, Penguin.PenguinColor.BROWN);
        assertEquals("[\"playing-as\",[\"brown\"]]", readable.readUTF());
    }

    @Test
    public void sendSetupMessage() throws IOException {
        HashSet<Player> players = new HashSet<>();
        Player p1 = new Player(17, Penguin.PenguinColor.BLACK);
        Player p2 = new Player(14, Penguin.PenguinColor.BROWN);
        players.add(p1);
        players.add(p2);
        IBoard board = new Board(4, 4, 4);
        GameState gameState = new GameState(players, board);
        JsonUtils.sendSetupMessage(writable, gameState);

        assertEquals("[\"setup\",[\"{\\\"players\\\":" +
                "[{\\\"color\\\":\\\"black\\\",\\\"score\\\":0,\\\"places\\\":[]}," +
                "{\\\"color\\\":\\\"brown\\\",\\\"score\\\":0,\\\"places\\\":[]}]," +
                "\\\"board\\\":[[4,4,4,4],[4,4,4,4],[4,4,4,4],[4,4,4,4]]}\"]]", readable.readUTF());
    }

    @Test
    public void sendTakeTurnMessage() throws IOException {
        HashSet<Player> players = new HashSet<>();
        Player p1 = new Player(17, Penguin.PenguinColor.BLACK);
        Player p2 = new Player(14, Penguin.PenguinColor.BROWN);
        players.add(p1);
        players.add(p2);
        IBoard board = new Board(4, 4, 4);
        GameState gameState = new GameState(players, board);
        List<Action> actions = new ArrayList<>();

        JsonUtils.sendTakeTurnMessage(writable, gameState, actions);
        assertEquals("[\"take-turn\",[\"{\\\"players\\\":" +
                "[{\\\"color\\\":\\\"black\\\",\\\"score\\\":0,\\\"places\\\":[]}," +
                "{\\\"color\\\":\\\"brown\\\",\\\"score\\\":0,\\\"places\\\":[]}]," +
                "\\\"board\\\":[[4,4,4,4],[4,4,4,4],[4,4,4,4],[4,4,4,4]]}\",[]]]", readable.readUTF());
    }


    @Test
    public void sendEndMessage() throws IOException {
        JsonUtils.sendEndMessage(writable, false);
        assertEquals("[\"end\",[false]]", readable.readUTF());
    }

    @Test
    public void parsePositionFromReply() {
        BoardPosition repliedPosition = JsonUtils.parsePositionFromReply("[2,3]");
        int row = repliedPosition.getRow();
        int col = repliedPosition.getCol();

        assertEquals(2, row);
        assertEquals(3, col);
    }

    @Test
    public void parsePassActionFromReply() {
        Player p1 = new Player(17, Penguin.PenguinColor.BLACK);
        Action action = JsonUtils.parseActionFromReply("false", p1);
        assertTrue(action instanceof Pass);
    }

    @Test
    public void parseMoveActionFromReply() {
        Player p1 = new Player(17, Penguin.PenguinColor.BLACK);
        Action action = JsonUtils.parseActionFromReply("[[1,2],[3,4]]", p1);
        assertTrue(action instanceof Move);

        Move move = (Move) action;
        assertEquals(1, move.getStart().getRow());
        assertEquals(2, move.getStart().getCol());
        assertEquals(3, move.getDestination().getRow());
        assertEquals(4, move.getDestination().getCol());
    }

}