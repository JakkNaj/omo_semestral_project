package cz.fel.cvut.omo.observer;

import cz.fel.cvut.omo.events.Event;

import java.io.IOException;

/**
 * Interface for observer
 */
public interface Observer {

    /**
     * Processes the event and logs it
     * @param event to be processed
     * @throws IOException if writer can't write to EventReport
     */
    void update(Event event) throws IOException;

    /**
     * Create a reaction to an event
     * @param event to be processed
     */
    void reactToEvent(Event event);

    /**
     * Logs event
     * @param event to be logged
     * @throws IOException if writer can't write to EventReport
     */
    void logEvent(Event event) throws IOException;
}
