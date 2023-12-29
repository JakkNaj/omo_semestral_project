package cz.fel.cvut.omo.activities;

import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.appliances.states.ApplianceState;
import cz.fel.cvut.omo.appliances.states.BrokenState;
import cz.fel.cvut.omo.creature.Creature;
import lombok.Getter;


/**
 * Class representing an interaction between a Creature (Person / Animal) and an Appliance
 */
@Getter
public class Activity {

    protected final Creature creature;

    protected final Appliance appliance;

    protected int timeOfUse;

    protected int timeOfActivity;

    public Activity(Creature creature, Appliance appliance, int timeOfActivity) {
        this.creature = creature;
        this.appliance = appliance;
        this.timeOfUse = 0;
        this.timeOfActivity = timeOfActivity;
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
