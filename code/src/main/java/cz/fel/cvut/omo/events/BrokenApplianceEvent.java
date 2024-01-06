package cz.fel.cvut.omo.events;

import cz.fel.cvut.omo.appliances.Appliance;
import lombok.Getter;

@Getter
public class BrokenApplianceEvent extends Event {

    private final Appliance appliance;

    public BrokenApplianceEvent(String where, String reason, Appliance appliance) {
        super(where, reason);
        this.appliance = appliance;
    }

    @Override
    public String toString() {
        return "BrokenApplianceEvent{" +
                "appliance=" + appliance +
                ", where='" + where + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
