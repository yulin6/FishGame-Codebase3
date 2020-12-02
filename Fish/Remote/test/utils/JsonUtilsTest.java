package utils;

import static org.junit.Assert.*;

public class JsonUtilsTest {

    String startMsg = "[\"start\",[true]]";
    String playAsRedMsg = "[\"playing-as\",[\"red\"]]";
    String playAsBlackMsg = "[\"playing-as\",[\"black\"]]";
    String playingWithOneMsg = "[\"playing-with\",[\"black\"]]";
    String playingWithTwoMsg = "[\"playing-with\",[\"black\",\"brown\"]]";
    String playingWithThreeMsg = "[\"playing-with\",[\"black\",\"brown\",\"white\"]]";
    String setUpMsg = "[[\"setup\", [{\n" +
            "        \"players\": [\n" +
            "        {\n" +
            "        \"color\": \"red\",\n" +
            "        \"score\": 15,\n" +
            "        \"places\": [\n" +
            "        [0,0]\n" +
            "        ]\n" +
            "        },\n" +
            "        {\n" +
            "        \"color\": \"black\",\n" +
            "        \"score\": 0,\n" +
            "        \"places\": [\n" +
            "        [0,3],\n" +
            "        [2,0]\n" +
            "        ]\n" +
            "        }\n" +
            "        ],\n" +
            "        \"board\": [[4,2,1,5],[1],[4,0,5,1],[1,1,1]]\n" +
            "        }]]]";
    String takeTurnMsg = "[\"take-\"]";
    String endMsg;



}