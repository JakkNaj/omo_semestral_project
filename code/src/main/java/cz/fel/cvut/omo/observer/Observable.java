package cz.fel.cvut.omo.observer;

import cz.fel.cvut.omo.activities.Activity;
import cz.fel.cvut.omo.events.Event;

/**
 * Interface for observable objects
 */
public interface Observable {

    /**
     * Subscribe new observer
     * @param observer to be subscribed
     */
    void subscribe(Observer observer);

    /**
     * Subscribe observer
     * @param observer to be unsubscribed
     */
    void unsubscribe(Observer observer);

    /**
     * Updates all subscribed observers
     * @param event to be notified with
     */
    void notifyAll(Event event);

}
