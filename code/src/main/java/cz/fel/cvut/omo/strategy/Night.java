package cz.fel.cvut.omo.strategy;

import cz.fel.cvut.omo.appliances.states.blinds.BlindsState;


public class Night extends Strategy{

    public Night() {
        house.getFloors()
                .forEach(floor -> floor.getRooms()
                        .forEach(room -> {
                            room.getCreatures()
                                    .forEach(creature -> creature.setSleeping(false));
                            room.getWindows()
                                    .forEach(window -> window.getBlinds()
                                            .setState(BlindsState.opened));
                        }));
    }
}
