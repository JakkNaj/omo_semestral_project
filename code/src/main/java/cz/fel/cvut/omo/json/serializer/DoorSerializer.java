package cz.fel.cvut.omo.json.serializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import cz.fel.cvut.omo.house.Door;

import java.lang.reflect.Type;

public class DoorSerializer implements JsonSerializer<Door> {
    @Override
    public JsonElement serialize(Door door, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        // Serialize other fields
        jsonObject.addProperty("closed", door.isClosed());
        jsonObject.addProperty("secondRoom", door.getRoom2().getName());
        jsonObject.addProperty("firstRoom", door.getRoom1().getName());
        jsonObject.addProperty("doorId", door.getId());
        return jsonObject;
    }
}
