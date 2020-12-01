package utils;

import java.util.Scanner;

import static utils.JsonUtils.parseStateFromMessage;


// just used for testing gson,
// can delete this class
public class gsonTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }
        String jsonString = builder.toString();

        System.out.println(parseStateFromMessage(jsonString).getBoard().getRows());
    }
}


//["setup", [{
//        "players": [
//        {
//        "color": "red",
//        "score": 15,
//        "places": [
//        [0,0]
//        ]
//        },
//        {
//        "color": "black",
//        "score": 0,
//        "places": [
//        [0,3],
//        [2,0]
//        ]
//        },
//        {
//        "color": "white",
//        "score": 5,
//        "places": [
//        [3,2],
//        [3,1]
//        ]
//        }
//        ],
//        "board": [[4,2,1,5],[1],[4,0,5,1],[1,1,1]]
//        }]]