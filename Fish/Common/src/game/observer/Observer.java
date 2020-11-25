package game.observer;

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
     * notify the Listeners and call their actionPerformed method.
     */
    public void notifyListener() {
        for(StateChangeListener sl: listeners){
            sl.actionPerformed();
        }
    }
}
