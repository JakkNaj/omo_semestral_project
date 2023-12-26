package cz.cvut.fel.omo;

import cz.fel.cvut.omo.builders.FloorBuilder;
import cz.fel.cvut.omo.builders.HouseBuilder;
import cz.fel.cvut.omo.builders.RoomBuilder;
import cz.fel.cvut.omo.builders.WindowBuilder;
import cz.fel.cvut.omo.house.House;

public class HouseBuilderTest {

    public void HouseBuilderTestFirst(){
        House house  = new HouseBuilder()
                .addFloor(new FloorBuilder(0)
                        .addRoom(new RoomBuilder("outside")
                                .build())
                        .addRoom(new RoomBuilder("inside")
                                .build())
                        .addDoor("outside", "inside")
                        .build()
                )
                .addFloor(new FloorBuilder(1)
                        .addRoom(new RoomBuilder("Bedroom")
                                .addWindow(new WindowBuilder()
                                        .addBlinds()
                                        .build())
                                .build())
                        .addRoom(new RoomBuilder("Bathroom")
                                .addWindow(new WindowBuilder()
                                        .addBlinds()
                                        .build())
                                .addWindow(new WindowBuilder()
                                        .build())
                                .build())
                        .addDoor("Bedroom", "Bathroom")
                        .build())
                .build();
    }
}
