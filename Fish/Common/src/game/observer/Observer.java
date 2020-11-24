package game.observer;

import java.util.ArrayList;
import java.util.List;

public class Observer {
    private final List<StateChangeListener> listeners = new ArrayList<StateChangeListener>();

    public void addListener(StateChangeListener toAdd) {
        listeners.add(toAdd);
    }

    public void notifyListener() {
        for(StateChangeListener sl: listeners){
            sl.actionPerformed();
        }
    }
}
