package xjson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonStreamParser;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class for the xjson program, accepting arbitrary sequences of JSON values/objects/arrays
 * and outputting 2 JSON values based on it.
 */
public class xjson {

  /**
   * Main method of the xjson class, run when the compiled class is run.
   * @param args The arguments provided to the code at runtime. For the intended purpose
   *             of this code, there should not actually be any.
   */
  public static void main(String[] args) {
    // Open a scanner to accept all the JSON values from STDIN into one string
    // to give to a parser
    Scanner input = new Scanner(System.in);
    StringBuilder rawJson = new StringBuilder();
    while (input.hasNextLine()) {
      rawJson.append(input.nextLine());
      rawJson.append("\n");
    }
    input.close();
    String jsonInput = rawJson.toString();

    // Create a Gson object to convert JSON into Java objects
    Gson gson = new GsonBuilder().create();
    // Open a parser taking the String generated by inputs into STDIN
    JsonStreamParser parser = new JsonStreamParser(jsonInput);

    int jsonValues = 0;
    ArrayList<JsonElement> jElements = new ArrayList<>();

    // As long as input is available on the parser, interact with each JSON value
    while (parser.hasNext()) {
      JsonElement element = parser.next();
      jsonValues++;
      jElements.add(element);
    }

    // Generate the JSON list from the individual JSON elements in the ArrayList we obtained
    JsonArray out1array = new JsonArray();
    for (JsonElement e : jElements) {
      out1array.add(e);
    }

    // Create the first output, a JSON object
    JsonObject out1 = new JsonObject();
    // add "count", (# of values read)
    out1.add("count", new JsonPrimitive(jsonValues));
    // add "seq", (array of JSON values in order)
    out1.add("seq", out1array);

    // Create the second output, a JSON array
    JsonArray out2 = new JsonArray();
    // add first element - number of JSON values read
    out2.add(jsonValues);
    // add remaining elements - sequence of JSON values in reverse order
    for (int i = jElements.size() - 1; i >= 0; i--) {
      out2.add(jElements.get(i));
    }

    // Print the two JSON values (object and array) to STDOUT
    System.out.println(out1.toString());
    System.out.println(out2.toString());
  }

}