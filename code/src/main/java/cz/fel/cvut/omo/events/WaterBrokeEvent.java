package cz.fel.cvut.omo.events;

import cz.fel.cvut.omo.appliances.Appliance;
import lombok.Getter;

@Getter
public class WaterBrokeEvent extends Event{

    private final Appliance appliance;
    public WaterBrokeEvent(String source, String cause, Appliance appliance) {
        super(source, cause);
        this.appliance = appliance;
    }

    @Override
    public String toString() {
        return "WaterBrokeEvent{" +
                "appliance=" + appliance +
                ", where='" + where + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
