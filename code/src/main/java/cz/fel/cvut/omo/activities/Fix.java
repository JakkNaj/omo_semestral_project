package cz.fel.cvut.omo.activities;

import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.appliances.ApplianceFactory;
import cz.fel.cvut.omo.creature.Creature;
import lombok.Getter;

import java.util.Random;

public class Fix extends Activity{

    private String manual;

    @Getter
    private Boolean fixable;

    private Random random;

    public Fix(Creature creature, Appliance appliance) {
        super(creature, appliance, 200);
    }

    @Override
    public void iterate() {
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
