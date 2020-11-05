/**
 * Class composing information about performance across games of Fish for a player component.
 * Contains information on number of games played, number of games won, maximum fish obtained in
 * a single game, and an average number of fish scored per game.
 *
 * A player component has a persistent statistics object associated with them that is modified as
 * it continues to play games.
 */
public class PlayerStatistics {
  private int gamesPlayed;
  private int gamesWon;
  private int maxSingleGameFish;
  private double averageScore;

  /**
   * Gets the number of games played by the player component this is associated with.
   * @return Integer number of games played.
   */
  public int getGamesPlayed() {
    return gamesPlayed;
  }

  /**
   * Gets the number of games won by the player component this is associated with.
   * @return Integer number of games won.
   */
  public int getGamesWon() {
    return gamesWon;
  }

  /**
   * Gets the highest number of fish scored in a single gameby the player component this is
   * associated with.
   * @return Integer number of highest scoring amount of fish.
   */
  public int getHighestScore() {
    return maxSingleGameFish;
  }

  /**
   * Gets the average number of fish scored by the player component this is associated with.
   * @return Average quantity of fish earned per game.
   */
  public double getAverageScore() {
    return averageScore;
  }

  /**
   * Sets the number of games played by the player component.
   * @param gamesPlayed Number of games to set the value to.
   */
  public void setGamesPlayed(int gamesPlayed) {
    this.gamesPlayed = gamesPlayed;
  }

  /**
   * Sets the number of games won by the player component.
   * @param gamesWon Number of games to set the value to.
   */
  public void setGamesWon(int gamesWon) {
    this.gamesWon = gamesWon;
  }

  /**
   * Sets the highest number of fish earned in a single game by the player component.
   * @param highestScore Highest score to set the value to.
   */
  public void setHighestScore(int highestScore) {
    this.maxSingleGameFish = highestScore;
  }

  /**
   * Sets the average number of fish earned in all games of the player component.
   * @param averageScore Average score earned by the player to set the value to.
   */
  public void setAverageScore(double averageScore) {
    this.averageScore = averageScore;
  }
}
