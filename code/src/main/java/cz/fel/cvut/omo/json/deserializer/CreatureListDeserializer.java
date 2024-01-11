package cz.fel.cvut.omo.json.deserializer;

import com.google.gson.*;
import cz.fel.cvut.omo.creature.Creature;
import cz.fel.cvut.omo.creature.animal.Animal;
import cz.fel.cvut.omo.creature.person.Person;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CreatureListDeserializer implements JsonDeserializer<List<Creature>> {
    @Override
    public List<Creature> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        List<Creature> creatures = new ArrayList<>();

        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            JsonElement typeElement = jsonObject.get("ObjectType");

            if (typeElement != null) {
                String type = typeElement.getAsString();

                if (type.equals("Person")) {
                    creatures.add(context.deserialize(jsonObject, Person.class));
                } else if (type.equals("Animal")) {
                    creatures.add(context.deserialize(jsonObject, Animal.class));
                }
            }
        }

        return creatures;
    }
}
