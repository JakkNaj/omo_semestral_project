package cz.fel.cvut.omo.activities;

import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.appliances.ApplianceFactory;
import cz.fel.cvut.omo.appliances.states.ApplianceState;
import cz.fel.cvut.omo.appliances.states.BrokenState;
import cz.fel.cvut.omo.creature.Creature;
import lombok.Getter;

import java.util.Random;

public class Fix extends ApplianceActivity{

    private String manual;

    @Getter
    private Boolean fixable;

    private transient Random random = new Random();

    public Fix(Creature creature, Appliance appliance) {
        super(creature, appliance, 200);
    }

    @Override
    public void iterate() {
        if (!(this.appliance.getState() instanceof BrokenState)){
            //finish the activity, since the appliance has been already repaired by someone else
            timeOfUse = timeOfActivity;
        }
        if (timeOfUse == 0){
            manual = appliance.getManual();
            fixable = knowsHowToRepairIt();
        }
        if (timeOfUse == timeOfActivity && fixable){
            appliance.repair();
        }
        this.timeOfUse++;
    }

    public boolean knowsHowToRepairIt(){
        return random.nextDouble() < 0.8;
    }

    public Appliance getNewAppliance(){
        return ApplianceFactory.createAppliance(appliance.getClass().getSimpleName(), appliance.getCurrentConsumption());
    }
}
