package cz.fel.cvut.omo.activities;

import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.appliances.states.ApplianceState;
import cz.fel.cvut.omo.appliances.states.BrokenState;
import cz.fel.cvut.omo.creature.Creature;
import cz.fel.cvut.omo.vehicles.Vehicle;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


/**
 * Class representing an activity of Person/Creature
 */
@Getter
public abstract class Activity {

    protected final Creature creature;

    protected int timeOfUse;

    protected int timeOfActivity;

    public Activity(Creature creature, int timeOfActivity) {
        this.creature = creature;
        this.timeOfUse = 0;
        this.timeOfActivity = timeOfActivity;
    }

    public abstract void iterate();

    protected abstract void updateStatistics();

    public boolean isFinished(){
        return timeOfUse >= timeOfActivity;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "creature=" + creature +
                ", timeOfUse=" + timeOfUse +
                ", timeOfActivity=" + timeOfActivity +
                '}';
    }
}
