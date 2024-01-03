package cz.cvut.fel.omo;

import cz.fel.cvut.omo.appliances.coffeeMachine.CoffeeMachineImpl;
import cz.fel.cvut.omo.appliances.fridge.FridgeImpl;
import cz.fel.cvut.omo.appliances.heater.HeaterImpl;
import cz.fel.cvut.omo.appliances.light.LightingImpl;
import cz.fel.cvut.omo.appliances.tv.TelevisionImpl;
import cz.fel.cvut.omo.appliances.washingMachine.WashingMachineImpl;
import cz.fel.cvut.omo.builders.FloorBuilder;
import cz.fel.cvut.omo.builders.HouseBuilder;
import cz.fel.cvut.omo.builders.RoomBuilder;
import cz.fel.cvut.omo.builders.WindowBuilder;
import cz.fel.cvut.omo.house.House;
import cz.fel.cvut.omo.house.Room;
import cz.fel.cvut.omo.vehicles.Vehicle;
import cz.fel.cvut.omo.vehicles.VehicleFactory;

import java.util.Arrays;

public class HouseBuilderTest {

    public void HouseBuilderTestFirst() {
        House house = new HouseBuilder()
                .addFloor(new FloorBuilder(0)
                        .addRoom(new RoomBuilder("outside",0,100)
                                .build())
                        .addRoom(new RoomBuilder("inside",20,2)
                                .build())
                        .addDoor("outside", "inside")
                        .build()
                )
                .addFloor(new FloorBuilder(1)
                        .addRoom(new RoomBuilder("Bedroom",21,1)
                                .addWindow(new WindowBuilder()
                                        .addBlinds()
                                        .build())
                                .build())
                        .addRoom(new RoomBuilder("Bathroom",19,4)
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

    public void secondHouseFromBuilder() {
        HouseBuilder houseBuilder = new HouseBuilder();
        FloorBuilder floorBuilder = new FloorBuilder(0);
        FloorBuilder floorBuilder1 = new FloorBuilder(1);
        RoomBuilder roomBuilderLivingR = new RoomBuilder("Living room",22,2);
        RoomBuilder roomBuilderKitchen = new RoomBuilder("Kitchen",21,3);

        House house = houseBuilder.addFloor(
                        floorBuilder.addRoom(
                                        roomBuilderLivingR.addAppliance(new TelevisionImpl(Arrays.asList(100.0, 0.0, 0.0)))
                                                .addAppliance(new LightingImpl(Arrays.asList(50.0, 0.0, 0.0)))
                                                .addAppliance(new HeaterImpl(Arrays.asList(50.0, 0.0, 50.0)))
                                                .build())
                                .addRoom(
                                        new RoomBuilder("Garage",15,5)
                                                .addWindow(new WindowBuilder().addBlinds().build())
                                                .addVehicle(VehicleFactory.getInstance().createCar("Skoda"))
                                                .addVehicle(VehicleFactory.getInstance().createBike("Detske"))
                                                .addVehicle(VehicleFactory.getInstance().createBike("Damske"))
                                                .addVehicle(VehicleFactory.getInstance().createSki("Lyze"))
                                                .build())
                                .addDoor("Garage", "Living room")
                                .addRoom(new Room("Outside",0,100))
                                .addDoor("Garage","Outside")
                                .build())
                .addFloor(
                        floorBuilder1.addRoom(
                                        roomBuilderKitchen.addAppliance(new LightingImpl(Arrays.asList(50.0, 0.0, 0.0)))
                                                .addAppliance(new FridgeImpl(Arrays.asList(100.0, 0.0, 0.0)))
                                                .addAppliance(new WashingMachineImpl(Arrays.asList(100.0, 100.0, 0.0)))
                                                .addAppliance(new CoffeeMachineImpl(Arrays.asList(20.0, 20.0, 0.0)))
                                                .addAppliance(new HeaterImpl(Arrays.asList(50.0, 0.0, 50.0)))
                                                .build())
                                .build()
                )
                .build();
    }
}
