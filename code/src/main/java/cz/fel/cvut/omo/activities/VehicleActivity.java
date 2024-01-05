package cz.fel.cvut.omo.activities;

import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.appliances.states.BrokenState;
import cz.fel.cvut.omo.creature.Creature;
import cz.fel.cvut.omo.house.House;
import cz.fel.cvut.omo.vehicles.Vehicle;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class VehicleActivity extends Activity {
    @Getter
    protected final Vehicle vehicle;

    protected static final Map<Vehicle,Map<Creature, Integer>> statistics = new HashMap<>();

    public VehicleActivity(Creature creature, Vehicle vehicle, int timeOfActivity) {
        super(creature, timeOfActivity);
        this.vehicle = vehicle;
    }

    public void iterate(){
        if (timeOfUse == 0){
            House house = House.getInstance();
            vehicle.take();
            vehicle.setInUse(true);
        }
        timeOfUse++;
        if (timeOfUse == timeOfActivity) {
            vehicle.store();
            vehicle.setInUse(false);
        }
        if (vehicle.isBroken()){
            timeOfUse = timeOfActivity;
        }
        System.out.println(this);
    }

    protected void updateStatistics(){
        if (!statistics.containsKey(vehicle)){
            statistics.put(vehicle, new HashMap<>());
        }
        Map<Creature, Integer> applianceStatistics = statistics.get(vehicle);
        if (!applianceStatistics.containsKey(creature)){
            applianceStatistics.put(creature, 1);
        } else {
            applianceStatistics.put(creature, applianceStatistics.get(creature) + 1);
        }
    }
}
