package cz.fel.cvut.omo.appliances;



import cz.fel.cvut.omo.appliances.states.*;
import cz.fel.cvut.omo.events.BrokenApplianceEvent;
import cz.fel.cvut.omo.house.House;
import cz.fel.cvut.omo.house.Room;
import cz.fel.cvut.omo.report.ReportVisitor;
import lombok.Getter;

import java.util.List;
import java.util.Random;

public abstract class Appliance implements ApplianceContext {

    @Getter
    private int wearTear;
    private ApplianceState state;
    private final List<Double> consumption;

    private List<Double> sumConsumption = List.of(0.0, 0.0, 0.0);

    protected List<Runnable> actions;

    @Getter
    private final String manual = "repair manual for appliance";

    public Appliance(List<Double> consumption, int wearTear) {
        this.consumption = consumption;
        this.wearTear = wearTear;
        this.state = new OffState();
    }

    @Override
    public void setState(ApplianceState state) {
        this.state = state;
    }

    @Override
    public ApplianceState getState() {
        return state;
    }

    @Override
    public List<Double> getCurrentConsumption() {
        return state.getCurrentConsumption(consumption);
    }

    @Override
    public void turnOn() {
        //don't turn on when appliance broken
        if (this.state instanceof BrokenState)
            return;
        this.setState(new OnState());
    }

    @Override
    public void turnOff() {
        //don't change state when broken
        if (this.state instanceof BrokenState)
            return;
        this.setState(new OffState());
    }

    //todo after appliance broken, some person has to repair it
    @Override
    public void turnBroken() {
        this.setState(new BrokenState());
    }

    @Override
    public void repair() {
        if (this.state instanceof BrokenState)
            this.setState(new IdleState());
    }

    @Override
    public void turnIdle() {
        //don't change state when broken
        if (this.state instanceof BrokenState)
            return;
        this.setState(new IdleState());
    }


    //use each day (every 24th tick) on appliance tick
    public void wearOff(){
        wearTear--;
        if (wearTear == 0) {
            this.setState(new BrokenState());
            Room room = House.getInstance().getApplianceRoom(this);
            room.acceptEvent(new BrokenApplianceEvent(room.getName(), "age",this));
        }
    }

    //use each hour on appliance tick
    public void saveConsumption(){
        List<Double> currentConsumption = getCurrentConsumption();
        for (int i = 0; i < consumption.size(); i++){
            sumConsumption.add(currentConsumption.get(i));
        }
    }

    public List<Double> getSumConsumption(){
        List<Double> sum = sumConsumption;
        sumConsumption = List.of(0.0, 0.0, 0.0);
        return sum;
    }

    public void accept(ReportVisitor reportVisitor){
        reportVisitor.visit(this);
    }

    public String getType(){
        return "generic Appliance";
    }

    @Override
    public void use() {
        Runnable action = getRandomAction();
        if (action != null)
            action.run();
    }

    @Override
    public Runnable getRandomAction() {
        Random random = new Random();
        return actions.isEmpty() ? null : actions.get(random.nextInt(actions.size()));
    }
}
