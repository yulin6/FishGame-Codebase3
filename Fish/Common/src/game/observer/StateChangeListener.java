package game.observer;

import game.model.Action;
import game.model.GameState;

/**
 * an interface that will be implemented for listening to the change of a GameState, and perform certain actions.
 */
public interface StateChangeListener {

    /**
     * will be called when a game start for performing certain action with the given updated GameState.
     * @param gs a GameState
     */
    void gameStarted(GameState gs);

    /**
     * will be called when a game state changed for performing certain action with the given updated GameState.
     * @param gs a GameState
     */
    void gameStateUpdated(GameState gs);
}
