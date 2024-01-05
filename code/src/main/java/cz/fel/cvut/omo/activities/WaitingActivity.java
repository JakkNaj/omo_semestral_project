package cz.fel.cvut.omo.activities;

import cz.fel.cvut.omo.creature.Creature;
import cz.fel.cvut.omo.house.House;
import cz.fel.cvut.omo.vehicles.Vehicle;

import java.util.HashMap;
import java.util.Map;

public class WaitingActivity extends Activity {

    protected static final Map<Creature, Integer> statistics = new HashMap<>();

    public WaitingActivity(Creature creature){
        super(creature, 1);
    }

    @Override
    public void iterate() {
        timeOfUse++;
        creature.move(House.getInstance().getCreatureRoom(creature));
    }

    @Override
    protected void updateStatistics() {
        if (!statistics.containsKey(creature)){
            statistics.put(creature, 1);
        } else {
            int applianceStatistics = statistics.get(creature);
            statistics.put(creature, applianceStatistics + 1);
        }
    }

    @Override
    public String toString() {
        return "WaitingActivity{" +
                "timeOfUse=" + timeOfUse +
                ", timeOfActivity=" + timeOfActivity +
                '}';
    }
}
