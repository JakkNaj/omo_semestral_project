package cz.fel.cvut.omo.activities;

import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.appliances.states.BrokenState;
import cz.fel.cvut.omo.creature.Creature;
import cz.fel.cvut.omo.house.House;
import cz.fel.cvut.omo.vehicles.Vehicle;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing an interaction of Person/Creature with a Vehicle
 */
public class    VehicleActivity extends Activity {

    @Getter
    @Setter
    protected transient final Vehicle vehicle;

    protected static final Map<Vehicle,Map<Creature, Integer>> statistics = new HashMap<>();

    public VehicleActivity(Creature creature, Vehicle vehicle, int timeOfActivity) {
        super(creature, timeOfActivity);
        this.vehicle = vehicle;
        updateStatistics();
    }

    public static void printStatistics() {
        System.out.println("Vehicle statistics:");
        for (Vehicle vehicle : statistics.keySet()){
            System.out.println(vehicle + ":");
            for (Creature creature : statistics.get(vehicle).keySet()){
                System.out.println("\t" + creature + ": " + statistics.get(vehicle).get(creature));
            }
        }
    }

    /**
     * Iterating the activity from simulation (1 iteration = 1 hour)
     */
    public void iterate(){
        if (timeOfUse == 0){
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
        //System.out.println(this);
    }

    /**
     * Updating statistics about appliance usage
     */
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
