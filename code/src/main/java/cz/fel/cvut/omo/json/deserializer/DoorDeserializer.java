package cz.fel.cvut.omo.json.deserializer;

import com.google.gson.*;
import cz.fel.cvut.omo.appliances.states.*;
import cz.fel.cvut.omo.house.Door;

import java.lang.reflect.Type;

public class DoorDeserializer implements JsonDeserializer<Door> {
    @Override
    public Door deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject != null) {
            String typeOne = jsonObject.get("firstRoom").getAsString();
            String typeTwo = jsonObject.get("secondRoom").getAsString();
            return context.deserialize(jsonObject, Door.class);

        }

        return null;
    }
}
