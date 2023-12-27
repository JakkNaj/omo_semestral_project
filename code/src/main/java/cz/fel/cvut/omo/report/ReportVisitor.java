package cz.fel.cvut.omo.report;

import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.house.Floor;
import cz.fel.cvut.omo.house.House;
import cz.fel.cvut.omo.house.Room;

public interface ReportVisitor {
    void visit(House house);

    void visit(Floor floor, int i);

    void visit(Room room);

    void visit(Appliance appliance);
}
