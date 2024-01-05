package cz.fel.cvut.omo.creature.person;

import cz.fel.cvut.omo.activities.Activity;
import cz.fel.cvut.omo.activities.Fix;
import cz.fel.cvut.omo.appliances.heater.Heater;
import cz.fel.cvut.omo.appliances.heater.HeaterImpl;
import cz.fel.cvut.omo.creature.Creature;
import cz.fel.cvut.omo.events.BrokenApplianceEvent;
import cz.fel.cvut.omo.events.Event;
import cz.fel.cvut.omo.events.WaterBrokeEvent;
import cz.fel.cvut.omo.observer.Observer;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a Human being inside the simulation
 */
@Getter
@Setter
public class Person extends Creature implements Observer {

     private String firstName;

     private String lastName;

     private PersonType type;

     public Person(PersonType type) {
          this.type = type;
     }

     //represents fifo queue of activities which should person do on free time
     //as reaction to events generated by Room
     private List<Activity> todoActivities = new ArrayList<>();

     public void addTodoActivity(Activity activity) {
          todoActivities.add(activity);
     }

     public void addImportantActivity(Activity activity) {
          todoActivities.add(0, activity);
     }

     public Activity getFirstTodoActivity() {
          if (todoActivities.isEmpty())
               return null;
          return todoActivities.remove(0);
     }

     @Override
     public void update(Event event) throws IOException {
          reactToEvent(event);
          logEvent(event);
     }

     @Override
     public void reactToEvent(Event event) {
          if (type == PersonType.ADULT || type == PersonType.ELDER) {
               if (event instanceof WaterBrokeEvent){
                    addImportantActivity(new Fix(this,
                            ((WaterBrokeEvent) event).getAppliance()));

               } else if (event instanceof BrokenApplianceEvent){
                    addTodoActivity(new Fix(this,
                            ((BrokenApplianceEvent) event).getAppliance()));
               }
          }
     }

     @Override
     public void logEvent(Event event) throws IOException {
          //todo
     }

     @Override
     public String getName() {
          return firstName + " " + lastName;
     }
}
