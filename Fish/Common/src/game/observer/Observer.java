package game.observer;

import game.model.Action;
import game.model.GameState;
import java.util.ArrayList;
import java.util.List;

/**
 * An observer used for maintaining StateChangeListeners and notify them when a GameState has changed.
 */
public class Observer {
    private final List<StateChangeListener> listeners = new ArrayList<StateChangeListener>();

    /**
     * add the given StateChangeListener to the list of listeners.
     * @param toAdd the given StateChangeListener
     */
    public void addListener(StateChangeListener toAdd) {
        listeners.add(toAdd);
    }

    /**
     * notify the Listeners and call their gameStarted method.
     */
    public void notifyListenersGameStarted(GameState gs) {
        for(StateChangeListener sl: listeners){
            sl.gameStarted(gs);
        }
    }

    /**
     * notify the Listeners and call their actionPerformed method.
     */
    public void notifyListenersActionPerformed(Action action) {
        for(StateChangeListener sl: listeners){
            sl.actionPerformed(action);
        }
    }

    /**
     * notify the Listeners and call their newGameState method.
     */
    public void notifyListenersNewGameState(GameState gs) {
        for(StateChangeListener sl: listeners){
            sl.newGameState(gs);
        }
    }
}
