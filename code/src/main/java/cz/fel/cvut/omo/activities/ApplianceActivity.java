package cz.fel.cvut.omo.activities;

import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.appliances.states.BrokenState;
import cz.fel.cvut.omo.creature.Creature;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class ApplianceActivity extends Activity {

    @Getter
    protected final Appliance appliance;

    protected static final Map<Appliance,Map<Creature, Integer>> statistics = new HashMap<>();

    public ApplianceActivity(Creature creature, Appliance appliance, int timeOfActivity) {
        super(creature, timeOfActivity);
        this.appliance = appliance;
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

    protected void updateStatistics(){
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

    @Override
    public String toString() {
        return "ApplianceActivity{" +
                "appliance=" + appliance +
                ", creature=" + creature +
                ", timeOfUse=" + timeOfUse +
                ", timeOfActivity=" + timeOfActivity +
                '}';
    }
}
