package utils;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import static utils.JsonUtils.parseStateFromMessage;


// just used for testing gson,
// can delete this class
public class gsonTest {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }
        String jsonString = builder.toString();

        new DataOutputStream(new FileOutputStream("writable")).writeUTF("hello");
        System.out.println(parseStateFromMessage(jsonString).getBoard().getRows());
    }
}


