package game.observer;

/**
 * an interface that will be implemented for listening to the change of a GameState, and perform certain actions.
 */
public interface StateChangeListener {

    /**
     * A method for performing certain actions when a GameState has changed.
     */
    void actionPerformed();
}
