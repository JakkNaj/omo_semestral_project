package cz.fel.cvut.omo.house;

import cz.fel.cvut.omo.appliances.states.blinds.BlindsState;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Blinds {

    private BlindsState state = BlindsState.closed;

}
