package cz.fel.cvut.omo.report;

import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.house.Floor;
import cz.fel.cvut.omo.house.House;
import cz.fel.cvut.omo.house.Room;

public class ReportVisitorImpl implements ReportVisitor{
    @Override
    public void visit(House house) {
        System.out.println("Report visitor visiting house:\n");
    }

    @Override
    public void visit(Floor floor, int i) {
        System.out.println("Report visitor visiting floor "+ i +":\n");
    }

    @Override
    public void visit(Room room) {
        System.out.println("Report visitor visiting room "+ room.getName() +":\n");
    }

    @Override
    public void visit(Appliance appliance) {
        System.out.println(appliance.getType() + ": " + appliance.getConsumption() + "\n");
    }
}
