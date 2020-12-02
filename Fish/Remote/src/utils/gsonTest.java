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


