package cz.fel.cvut.omo.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * Class representing an Event in the simulation.
 * The event is keeping track about where and why it happened.
 */

@Getter
@Setter
public class Event {



    private String where;

    private String reason;

    /**
     * @param where is place, where Event happened
     * @param reason is why Event occurred
     */

    public Event(String where, String reason) {
        this.where = where;
        this.reason = reason;
    }
}
