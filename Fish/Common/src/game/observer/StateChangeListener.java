package game.observer;

import game.model.Action;
import game.model.GameState;

/**
 * an interface that will be implemented for listening to the change of a GameState, and perform certain actions.
 */
public interface StateChangeListener {

    /**
     * TODO
     * @param gs
     */
    void gameStarted(GameState gs);

    /**
     * TODO
     * @param action
     */
    void actionPerformed(Action action);

    /**
     * TODO
     * @param gs
     */
    void newGameState(GameState gs);
}
