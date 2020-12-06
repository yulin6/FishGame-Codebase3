package state;

import java.util.ArrayList;
import java.util.List;

import game.model.BoardPosition;
import game.model.Penguin;

/**
 * Class to represent the Player JSON input after being deserialized from JSON. A Player is a
 * JSON Object in the form of { "color" : Color, "score" : Natural, "places" : [Position ...
 * Position]}. Color is one of "red", "white", "brown", "black".
 */
public class TestPlayer {
  private String color;
  private int score;
  private List<List<Integer>> places;

  public TestPlayer(Penguin.PenguinColor col, int fish, List<BoardPosition> ps) {
    switch (col) {
      case RED:
        this.color = "red";
        break;
      case BLACK:
        this.color = "black";
        break;
      case BROWN:
        this.color = "brown";
        break;
      case WHITE:
        this.color = "white";
        break;
      default:
        throw new IllegalArgumentException("check cases");
    }

    this.score = fish;

    this.places = new ArrayList<>();
    for (BoardPosition bp : ps) {
      ArrayList<Integer> penPos = new ArrayList<>();
      penPos.add(bp.getRow());
      penPos.add(bp.getCol());
      places.add(penPos);
    }
  }

  public Penguin.PenguinColor getColor() {
    switch (color) {
      case "red":
        return Penguin.PenguinColor.RED;
      case "black":
        return Penguin.PenguinColor.BLACK;
      case "brown":
        return Penguin.PenguinColor.BROWN;
      case "white":
        return Penguin.PenguinColor.WHITE;
      default:
        throw new IllegalArgumentException("check cases");
    }
  }

  public int getScore() {
    return score;
  }

  public List<BoardPosition> getPlaces() {
    ArrayList<BoardPosition> posns = new ArrayList<>();
    for (List<Integer> l: places) {
      posns.add(new BoardPosition(l.get(0), l.get(1)));
    }
    return posns;
  }
}
