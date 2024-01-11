package cz.fel.cvut.omo.json.serializer;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

public class ListSerializer<T> implements JsonSerializer<List<T>> {
    @Override
    public JsonElement serialize(List<T> objects, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray jsonArray = new JsonArray();

        for (T object : objects) {
            JsonObject jsonObject = context.serialize(object).getAsJsonObject();
            jsonObject.addProperty("ObjectType", object.getClass().getSimpleName()); // Set the 'type' field
            jsonArray.add(jsonObject);
        }

        return jsonArray;
    }
}
