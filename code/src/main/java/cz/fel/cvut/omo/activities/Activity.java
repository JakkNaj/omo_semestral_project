package cz.fel.cvut.omo.activities;

import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.appliances.states.ApplianceState;
import cz.fel.cvut.omo.appliances.states.BrokenState;
import cz.fel.cvut.omo.creature.Creature;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


/**
 * Class representing an interaction between a Creature (Person / Animal) and an Appliance
 */
@Getter
public class Activity {

    protected final Creature creature;

    protected final Appliance appliance;

    protected int timeOfUse;

    protected int timeOfActivity;

    private static final Map<Appliance,Map<Creature, Integer>> statistics = new HashMap<>();

    public Activity(Creature creature, Appliance appliance, int timeOfActivity) {
        this.creature = creature;
        this.appliance = appliance;
        this.timeOfUse = 0;
        this.timeOfActivity = timeOfActivity;
        updateStatistics();
    }

    public void iterate(){
        if (timeOfUse == 0){
            appliance.turnOn();
            appliance.use();
        } else if (timeOfUse == timeOfActivity) {
            appliance.turnOff();
        }
        timeOfUse++;
        if (appliance.getState() instanceof BrokenState){
            timeOfUse = timeOfActivity;
        }
        System.out.println(this);
    }

    private void updateStatistics(){
        if (!statistics.containsKey(appliance)){
            statistics.put(appliance, new HashMap<>());
        }
        Map<Creature, Integer> applianceStatistics = statistics.get(appliance);
        if (!applianceStatistics.containsKey(creature)){
            applianceStatistics.put(creature, 1);
        } else {
            applianceStatistics.put(creature, applianceStatistics.get(creature) + 1);
        }
    }

    public boolean isFinished(){
        return timeOfUse >= timeOfActivity;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "creature=" + creature +
                ", appliance=" + appliance +
                ", timeOfUse=" + timeOfUse +
                ", timeOfActivity=" + timeOfActivity +
                '}';
    }
}
