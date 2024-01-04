package cz.fel.cvut.omo.events;

import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.vehicles.Vehicle;
import lombok.Getter;

@Getter
public class BrokenVehicleEvent extends Event {

    private final Vehicle vehicle;

    public BrokenVehicleEvent(String where, String reason, Vehicle vehicle) {
        super(where, reason);
        this.vehicle = vehicle;
    }
}
